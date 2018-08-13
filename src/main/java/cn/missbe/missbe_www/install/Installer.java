package cn.missbe.missbe_www.install;

import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.listener.InitServletContextListener;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.listener.AppConfig;

import java.util.Date;

/**
 * 初始化数据
 *
 * @author lyg
 * @date 16/8/5 19:17
 */
public class Installer {
    public static void install() {
        initAdmin();
    }

    private static void initAdmin() {
        PrintUtil.print("初始化admin用户.", SystemLog.Level.warning);
        User user = new User();
        user.setAccount("admin");
        user.setPassword(AppConfig.getConfigAsString("system.user.initPassword"));
        user.setNickname("超级管理员");
        user.setLastLoginTime(new Date());
        InitServletContextListener.getApplicationContext().getBean(UserService.class).save(user);
    }
}
