package cn.missbe.missbe_www.controller.comment;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.service.comment.CommentService;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author missbe
 * @date 16/9/10 01:48
 */
@Controller
@RequestMapping("/user/comments/comments")
public class CommentController extends BaseController {
    @Resource
    private CommentService commentService;
    private static String SEPARATOR="_";
    private static int RANDAM_NUMBER=23;


    @RequestMapping(value = "add")
    @ResponseBody
    public JsonBaseResult add(String id,  String content,String theme,String user) {
        String name=request.getLocalName()+SEPARATOR+request.getLocalAddr();
        String avator="static/web/avatar/"+StringUtils.random(RANDAM_NUMBER)+".jpg";
        return commentService.save(id, name,avator, content,theme,user);
    }
    @RequestMapping(value = "commentAll")
    @ResponseBody
    public JsonBaseResult commentAll(String theme_id){
        List<Comment> comments=commentService.findByTopicId(theme_id);
        if(comments==null){
            return new JsonBaseResult("--根据主题ID获取评论失败--",false);
        }
        return new JsonBaseResult(comments,true);
    }
    @RequestMapping(value = "changeRecommend")
    @ResponseBody
    public JsonBaseResult changeRecommend(@RequestParam("ids[]") List<String> ids){
        return  commentService.changeRecommendByIds(ids);
    }
    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Comment().dataTableTrs());
        map.put("className", "comments");
        return "admin/comment/comment/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PaginationResult list() {
        return commentService.paginateSearch(new DataTableForm(request),getUser());
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return commentService.deleteByIds(ids);
    }
}
