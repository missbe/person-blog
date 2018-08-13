package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.Access;
import cn.missbe.missbe_www.entity.Message;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.service.AccessService;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.MessageService;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.util.HttpServletUtil;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends BaseController{

    @Autowired
    private JsonKeyValueService jsonKeyValueService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccessService accessService;
    @Resource
    private UserService userService;
    /**
     * 首页页面
     *
     * @return
     */
    @RequestMapping("/")
    public String blogs(ModelMap map,HttpServletRequest request) {
        String ip = HttpServletUtil.getIpAddr(request);
        PrintUtil.print("IP:"+ip+"访问首页.", SystemLog.Level.info);
        List<JSONObject> users = new ArrayList<>();
        for (User user : userService.findAll()) {
            JSONObject u = jsonKeyValueService.findById(App.BLOGJSON_PREFIX + user.getAccount());
            if (u != null) {
                users.add(u);
                u.put("account", user.getAccount());
                u.put("avator", user.getAvator());
            }
        }
        map.put("users", users);
        return "index";
    }

}