package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.JsonKeyValue;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JsonKeyValueService jsonKeyValueService;
    /**
     * 后台登录主页面
     * @param map
     * @return
     */
    @RequestMapping(value = "")
    public String admin(ModelMap map) {
        putUrlToSession();
        map.put("msg", new JsonBaseResult(getUser().getNickname() + ",欢迎使用官网后台系统", true));
        return "admin/admin";
    }
    @RequestMapping(value = "permission",name = "权限管理")
    public String permission(ModelMap map) {
        return "admin/admin";
    }

    @RequestMapping(value = "index", name = "首页")
    public String index(ModelMap map, String title_left, String title_right, String content) {
        putUrlToSession();
        if (StringUtils.isBlank(title_left)) {
            map.put(App.INDEX_JSON, jsonKeyValueService.findByIdForce(App.INDEX_JSON));
        } else {
            JsonKeyValue jk = new JsonKeyValue();
            JSONObject obj = new JSONObject();
            obj.put("title_left", title_left);
            obj.put("title_right", title_right);
            obj.put("content", content);
            jk.setKey(App.INDEX_JSON);
            jk.setValue(obj.toJSONString());
            map.put(App.INDEX_JSON, obj);
            map.put("msg", jsonKeyValueService.save(jk));
        }
        return "admin/index";
    }

    @RequestMapping(value = "about", name = "关于我们")
    public String about(ModelMap map, String title_left, String title_right, String title_second, String content_one, String content_two, String list_one, String list_two, String list_three) {
        putUrlToSession();
        if (StringUtils.isBlank(title_left)) {
            map.put(App.ABOUT_JSON, jsonKeyValueService.findByIdForce(App.ABOUT_JSON));
        } else {
            JsonKeyValue jk = new JsonKeyValue();
            JSONObject obj = new JSONObject();
            obj.put("title_left", title_left);
            obj.put("title_right", title_right);
            obj.put("title_second", title_second);
            obj.put("content_one", content_one);
            obj.put("content_two", content_two);
            obj.put("list_one", list_one);
            obj.put("list_two", list_two);
            obj.put("list_three", list_three);
            jk.setKey(App.ABOUT_JSON);
            jk.setValue(obj.toJSONString());
            map.put(App.ABOUT_JSON, obj);
            map.put("msg", jsonKeyValueService.save(jk));
        }
        return "admin/about";
    }

    @RequestMapping(value = "services", name = "产品服务")
    public String services(ModelMap map, String title_one, String content_one, String title_two, String content_two, String title_three, String content_three, String title_four, String content_four, String title_five, String content_five, String title_six, String content_six, String title_seven, String content_seven, String title_eight, String content_eight) {
        putUrlToSession();
        if (StringUtils.isBlank(title_one)) {
            map.put(App.SERVICES_JSON, jsonKeyValueService.findByIdForce(App.SERVICES_JSON));
        } else {
            JsonKeyValue jk = new JsonKeyValue();
            JSONObject obj = new JSONObject();
            obj.put("title_one", title_one);
            obj.put("content_one", content_one);
            obj.put("title_two", title_two);
            obj.put("content_two", content_two);
            obj.put("title_three", title_three);
            obj.put("content_three", content_three);
            obj.put("title_four", title_four);
            obj.put("content_four", content_four);
            obj.put("title_five", title_five);
            obj.put("content_five", content_five);
            obj.put("title_six", title_six);
            obj.put("content_six", content_six);
            obj.put("title_seven", title_seven);
            obj.put("content_seven", content_seven);
            obj.put("title_eight", title_eight);
            obj.put("content_eight", content_eight);
            jk.setKey(App.SERVICES_JSON);
            jk.setValue(obj.toJSONString());
            map.put(App.SERVICES_JSON, obj);
            map.put("msg", jsonKeyValueService.save(jk));
        }
        return "admin/services";
    }

    @RequestMapping(value = "customs", name = "合作客户")
    public String customs(ModelMap map, String title, String title_one, String content_one, String title_two, String content_two, String title_three, String content_three, String title_four, String content_four, String title_five, String content_five, String title_six, String content_six, String custom_one, String custom_two, String custom_three, String custom_four, String custom_five, String custom_six) {
        putUrlToSession();
        if (StringUtils.isBlank(title_one)) {
            map.put(App.CUSTOMS_JSON, jsonKeyValueService.findByIdForce(App.CUSTOMS_JSON));
        } else {
            JsonKeyValue jk = new JsonKeyValue();
            JSONObject obj = new JSONObject();
            obj.put("title_one", title_one);
            obj.put("title", title);
            obj.put("content_one", content_one);
            obj.put("title_two", title_two);
            obj.put("content_two", content_two);
            obj.put("title_three", title_three);
            obj.put("content_three", content_three);
            obj.put("title_four", title_four);
            obj.put("content_four", content_four);
            obj.put("title_five", title_five);
            obj.put("content_five", content_five);
            obj.put("title_six", title_six);
            obj.put("content_six", content_six);
            obj.put("custom_one", custom_one);
            obj.put("custom_two", custom_two);
            obj.put("custom_three", custom_three);
            obj.put("custom_four", custom_four);
            obj.put("custom_five", custom_five);
            obj.put("custom_six", custom_six);
            jk.setKey(App.CUSTOMS_JSON);
            jk.setValue(obj.toJSONString());
            map.put(App.CUSTOMS_JSON, obj);
            map.put("msg", jsonKeyValueService.save(jk));
        }
        return "admin/customs";
    }

    @RequestMapping(value = "contact", name = "联系我们")
    public String contact() {
        putUrlToSession();
        return "admin/contact";
    }

}
