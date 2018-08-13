package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.JsonKeyValue;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.shiro.token.manager.TokenManager;
import cn.missbe.missbe_www.util.MD5Util;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

@Controller
public class UserController extends BaseController {

    /**
     * 验证码
     */
    @Resource
    private ImageCaptchaService imageCaptchaService;
    @Resource
    private UserService userService;
    @Resource
    private JsonKeyValueService jsonKeyValueService;

    /**
     * 验证用户登录方法
     * @param username
     * @param password
     * @param verCode
     * @param rememberMe
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(String username, String password, String verCode, boolean rememberMe,
						HttpServletRequest request, RedirectAttributes redirectAttributes) {
        boolean isResponseCorrect;
        try {
            isResponseCorrect = imageCaptchaService.validateResponseForID(request.getSession().getId(), verCode);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "验证码失效.");
            return "redirect:/loginUI";
        }
        if (!isResponseCorrect) {
            redirectAttributes.addFlashAttribute("msg", "验证码错误.");
            return "redirect:/loginUI";
        }
        // 获取用户未登录之前的地址
        String url = WebUtils.getSavedRequest(request) == null ? "" : WebUtils.getSavedRequest(request).getRequestUrl();
        PrintUtil.print("未登陆前的url：" + url, Level.debug);
        if (StringUtils.isBlank(url)) {
            url = "/admin";
        }
        try {
            TokenManager.login(username, MD5Util.string2MD5(password), rememberMe);
        } catch (DisabledAccountException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/loginUI";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/loginUI";
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = "/loginUI")
    public String loginUI() {
        return "login";
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        TokenManager.logout();
        return "redirect:/";
    }

    /**
     * captcha生成验证码图片io流
     */
    @RequestMapping(value = "/captcha")
    public void ImageCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            String captchaId = request.getSession().getId();
            BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, request.getLocale());
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");

            ImageIO.write(challenge, "jpeg", jpegOutputStream);
            byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

            ServletOutputStream respOs = response.getOutputStream();
            respOs.write(captchaChallengeAsJpeg);
            respOs.flush();
            respOs.close();
        } catch (IOException e) {
            PrintUtil.print("generate captcha image error:" + e.getMessage(), Level.error);
        }

    }

    /**
     * 用户更改信息UI界面
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/profileUI")
    public String profile(ModelMap map) {
        map.put("blog", jsonKeyValueService.findByIdForce(App.BLOGJSON_PREFIX + getUser().getAccount()));
        map.put("user", userService.findByAccount(getUser().getAccount()));
        return "user/profile";
    }

    /**
     * 更改用户帐户信息
     * @param passwordrepeat 确认密码
     * @param oldpassword 旧密码
     * @param nickname 昵称
     * @param password 新密码
     * @param avator 头像
     * @return
     */
    @RequestMapping(value = "/user/profile")
    @ResponseBody
    public JsonBaseResult profile(String passwordrepeat, String oldpassword, String nickname, String password, String avator) {
        return userService.changeUserInfo(getUser().getAccount(), password, oldpassword, passwordrepeat, nickname, avator);
    }

    /**
     * 更改用户博客信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/blogProfile")
    @ResponseBody
    public JsonBaseResult blogProfile(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        JsonKeyValue jk = new JsonKeyValue();
        JSONObject obj = new JSONObject();
        for (Entry<String, String[]> kv : params.entrySet()) {
            obj.put(kv.getKey(), kv.getValue()[0]);
        }
        jk.setKey(App.BLOGJSON_PREFIX + getUser().getAccount());
        jk.setValue(obj.toJSONString());
        return jsonKeyValueService.save(jk);
    }
}
