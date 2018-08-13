package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.util.PrintUtil;
import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.BaseState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author lyg
 * @date 16/9/9 23:23
 */
@Controller
public class FileUploadController {

    /**
     * 图片上传接口
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/imageUpload")
    public JsonBaseResult imageUpload(HttpServletRequest request)
            throws UnsupportedEncodingException {
        String rootPath = request.getServletContext().getRealPath("/");
        BaseState baseState = new ActionEnter(request, rootPath, "uploadimage").exec_app();
        if (baseState.isSuccess()) {
            PrintUtil.print("文件上传:" + baseState.getInfo("url"), Level.info);
            String url = baseState.getInfo("url");
            return new JsonBaseResult(url, true);
        } else {
            PrintUtil.print(baseState.toJSONString());
            return new JsonBaseResult(baseState.getInfo(), false);
        }
    }

}
