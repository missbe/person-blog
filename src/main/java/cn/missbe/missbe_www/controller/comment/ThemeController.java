package cn.missbe.missbe_www.controller.comment;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.comment.CommentService;
import cn.missbe.missbe_www.service.comment.ThemeService;
import cn.missbe.missbe_www.util.CaptchaUtils;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author missbe
 * @date 16/9/10 01:48
 */
@Controller
@RequestMapping("/user/themes/themes")
public class ThemeController extends BaseController {
    @Resource
    private ThemeService themeService;
    @Resource
    private CommentService commentService;

    private static final int COMMENT_NUMBER=3;

    private static final int PAGE_SIZE=6;
    private static String SEPARATOR="_";
    private static String captcha;
    private static int RANDAM_NUMBER=23;



    @RequestMapping(value = "captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");

            byte[] captchaChallengeAsJpeg = CaptchaUtils.genrateCaptcha().toByteArray();
            this.captcha=CaptchaUtils.getCaptcha();///得到生成的验证码

            ServletOutputStream respOs = response.getOutputStream();
            respOs.write(captchaChallengeAsJpeg);
            respOs.flush();
            respOs.close();
        } catch (IOException e) {
            PrintUtil.print("generate captcha image error:" + e.getMessage(), SystemLog.Level.error);
        }
    }

    @RequestMapping(value = "verify")
    @ResponseBody
    public JsonBaseResult verify(String verify){
        if(verify.trim().equals(captcha.trim())){
            return new JsonBaseResult("验证成功",true);
        }
        return new JsonBaseResult("验证失败",false);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public JsonBaseResult add(String id,  String content,String user,String blog) {
        String name=request.getLocalName()+SEPARATOR+request.getLocalAddr();
        String avator="static/web/avatar/"+ StringUtils.random(RANDAM_NUMBER)+".jpg";
        return themeService.save(id, name,avator, content,user,blog);
    }

    ////将评论与主题对应
    public Map<String,List<Comment>> findCommentsByThems(List<Themes> themes){
        Map<String,List<Comment>> comments=new HashMap<String,List<Comment>>();
        for (int i = 0,len=themes.size(); i <len; i++) {
            String idStr=String.valueOf(themes.get(i).getId());
            ///将主题对应的前三条评论加载到首页
            comments.put(idStr,findDefault(idStr));
        }
        return comments;
    }
    ///根据主题id提取主题的前三条评论
    public List<Comment> findDefault(String id){
        List<Comment> list=commentService.findByTopicId(id);
        if(list==null){
            PrintUtil.print("根据主题ID获取评论出错，请检查..", SystemLog.Level.error);
        }
        if(list!=null && list.size()>COMMENT_NUMBER){
            List<Comment> backup=new ArrayList<Comment>();
            backup.add(list.get(COMMENT_NUMBER-COMMENT_NUMBER));
            backup.add(list.get(COMMENT_NUMBER-COMMENT_NUMBER+1));
            backup.add(list.get(COMMENT_NUMBER-COMMENT_NUMBER+2));
            return backup;
        }
        return list;
    }
    @RequestMapping(value = "reload")
    @ResponseBody
    private JsonBaseResult reload(String page,String blog_id){
        int number=0;
        try{
            number=Integer.parseInt(page);
        }catch (NumberFormatException e){
            PrintUtil.print("字符串转整型出错，默认值", SystemLog.Level.error);
            number=1;
        }
        List<Themes> themes=themeService.findByPage(number,PAGE_SIZE,blog_id);
        ////加载信息
        Map<String,List<Comment>> comments=findCommentsByThems(themes);
        Map<String,Object> o=new HashMap<String,Object>();
        o.put("themes",themes);
        o.put("comments",comments);
        return new JsonBaseResult(o,true);
    }

    @RequestMapping(value = "changeRecommend")
    @ResponseBody
    public JsonBaseResult changeRecommend(@RequestParam("ids[]") List<String> ids){
       return  themeService.changeRecommendByIds(ids);
    }

    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Themes().dataTableTrs());
        map.put("className", "themes");
        return "admin/comment/themes/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PaginationResult list() {
        return themeService.paginateSearch(new DataTableForm(request),getUser());
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return themeService.deleteByIds(ids);
    }
}
