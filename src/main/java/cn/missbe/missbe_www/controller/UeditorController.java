package cn.missbe.missbe_www.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ueditor的controller,一般情况不用更改
 * @author lyg
 * @date 16/8/6 03:53
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController {
    @RequestMapping(value = "test", name = "Ueditor的测试页面")
    public String ueditorTest() {
        return "ueditorTest";
    }

    @RequestMapping(value = "config", name = "Ueditor获取后端配置")
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        String result = new ActionEnter(request, request.getServletContext().getRealPath("/")).exec();
        JSONObject jsonObj = JSON.parseObject(result);
        response.getWriter().write(jsonObj.toJSONString());
    }
}
