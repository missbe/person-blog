package cn.missbe.missbe_www.listener;

import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.SystemConfig;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.service.SystemConfigService;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.install.Installer;
import cn.missbe.missbe_www.service.SystemLogService;
import cn.missbe.missbe_www.util.DateUtil;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import cn.missbe.missbe_www.util.WildcardMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;
/**
 * init
 * @author lyg
 * @date 16/8/5 19:17
 */
public class InitServletContextListener implements ServletContextListener {
    /**
     * 保存的Spring applicationContext实例.
     */
    private static ApplicationContext applicationContext;
    /**
     * Receives notification that the web application initialization
     * process is starting.
     * <p>
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PrintUtil.print("========contextInitialized Start=========", Level.warning);

        ServletContext application = sce.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
        setApplicationContext(ac);
        // 保存service实例到实体类的静态变量service方便直接调用
        SystemLog.service = ac.getBean(SystemLogService.class);
        SystemConfig.service = ac.getBean(SystemConfigService.class);

        Date date = new Date();
        date.setTime(ac.getStartupDate());
        PrintUtil.print("spring 初始化时间：" + DateUtil.formateDateyyyy_MM_ddHHmmss(new Date(ac.getStartupDate())),
                Level.warning);
        PrintUtil.print("========contextInitialized start=========", Level.warning);
        PrintUtil.print("系统运行模式："+(AppConfig.getConfigAsBoolean("system.mode.online")?"线上模式":"开发模式"), Level.info);
        PrintUtil.print("同步配置...");
        ac.getBean(AppConfig.class).reloadConfig();
        PrintUtil.print("同步配置 Done.");
        PrintUtil.print("同步Url权限到permission表...");
        syncPermissions();
        PrintUtil.print("同步Url权限到permission表 Done.");
        PrintUtil.print("检查是否首次运行");
        UserService userService = ac.getBean(UserService.class);
        if (userService.findByAccount(App.ON_FIRE_ACCOUNT) == null) {
            Installer.install();
        }
        PrintUtil.print("检查是否首次运行 Done.");
        printDeployPath();
        PrintUtil.print("========contextInitialized end=========", Level.warning);
    }
    /**
     * 同步Url权限到permission<br>
     * controller的spring requestMapping获得url,并检测在shiro中是否受到permissionfilter限制
     */
    private void syncPermissions() {
        //获取所有的RequestMapping
        Map<String, String> controllerUrls = new HashMap<>();
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                HandlerMapping.class, true, false);
        for (HandlerMapping handlerMapping : allRequestMappings.values()) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String url = patternsCondition.getPatterns().toArray()[0].toString();
                    if (url.endsWith("/")) {
                        url = url.substring(0, url.length() - 1);
                    }
                    while (url.contains("//")) {
                        url = url.replaceAll("//", "/");
                    }
                    if (url.endsWith("UI")) {
                        continue;
                    }
                    controllerUrls.put(url, requestMappingInfo.getName());
                }////end for
            }///end if
        }//////end for
        //判断Url是否有permission控制
        Set<String> permissionWildcards = new HashSet<>();
        ShiroFilterFactoryBean shiroFilterFactoryBean = applicationContext.getBean(ShiroFilterFactoryBean.class);
        Map<String, String> chainMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        for (Map.Entry<String, String> kv : chainMap.entrySet()) {
            if (kv.getValue().contains("permission")) {
                permissionWildcards.add(kv.getKey());
            }
        }
        //受到url权限控制的urls
        Map<String, String> permedUrls = new HashMap<>();
        for (String url : controllerUrls.keySet()) {
            boolean permed = false;
            for (String wildcard : permissionWildcards) {
                if (WildcardMatcher.match(url, wildcard)) {
                    permed = true;
                    break;
                }
            }
            if (permed) {
                String name = controllerUrls.get(url);
                if (StringUtils.isBlank(name)) {
                    name = url;
                    PrintUtil.print("该url的requestMapping没有name属性:" + url, Level.warning);
                }
                permedUrls.put(url, name);
            } else {
                PrintUtil.print("无URL级别的限制:" + url);
            }
        }

        //有permission控制的进行同步,数据库多余的删除掉,角色更新
        PermissionService permissionService = applicationContext.getBean(PermissionService.class);
        List<Permission> permss = permissionService.findAll();
        for (Permission per : permss) {
            if (!permedUrls.containsKey(per.getPermission())) {
                //多余的删除
                PrintUtil.print("删除多余的permission:" + per.getName(), Level.warning);
                permissionService.deleteById(per.getId() + "");
            } else {
                //已有的做更新
                if (!permedUrls.get(per.getPermission()).equals(per.getName())) {
                    per.setName(permedUrls.get(per.getPermission()));
                    permissionService.update(per);
                }
            }
            permedUrls.remove(per.getPermission());
        }
        for (Map.Entry<String, String> kv : permedUrls.entrySet()) {
            //新增permission
            PrintUtil.print("新增了一个permission:" + kv.getValue(), Level.info);
            Permission per = new Permission();
            per.setName(kv.getValue());
            per.setPermission(kv.getKey());
            permissionService.save(per);
        }
    }
    private void printDeployPath() {
        String fullClassPath = getClass().getResource("").getPath();
        String spPath;
        if (fullClassPath.contains("WEB-INF")) {
            spPath = "WEB-INF";
        } else if (fullClassPath.contains("target")) {
            spPath = "target";
        } else if (fullClassPath.contains("classes")) {
            spPath = "classes";
        } else {
            return;
        }
        PrintUtil.print("项目部署路径:" + fullClassPath.substring(0, fullClassPath.indexOf(spPath)), Level.warning);
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p>
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        PrintUtil.print("========contextDestroyed start=========", Level.warning);
        PrintUtil.print("========contextDestroyed end=========", Level.warning);
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        InitServletContextListener.applicationContext = applicationContext;
    }

}
