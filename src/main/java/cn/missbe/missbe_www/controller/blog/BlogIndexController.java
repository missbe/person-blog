package cn.missbe.missbe_www.controller.blog;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.blog.BlogService;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.service.blog.SectionService;
import cn.missbe.missbe_www.service.comment.CommentService;
import cn.missbe.missbe_www.service.comment.ThemeService;
import cn.missbe.missbe_www.util.HttpServletUtil;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * @author lyg
 * @date 16/9/11 16:24
 */
@Controller
@RequestMapping("/blog")
public class BlogIndexController extends BaseController {

    @Resource
    private JsonKeyValueService jsonKeyValueService;
    @Resource
    private SectionService sectionService;
    @Resource
    private BlogService blogService;
    @Resource
    private MenuService menuService;
    @Resource
    private CommentService commentService;
    @Resource
    private ThemeService themeService;

    private static final int COMMENT_NUMBER=3;

    private static final int PAGE_SIZE=6;

    /**
     * 得到帐户对应的博客信息
     * @param account
     * @param map
     * @return
     */
    @RequestMapping("{account}")
    public String index(@PathVariable String account, ModelMap map) {
        map.putAll(commonInfoMap(account));
        Section indexSection = sectionService.findIndexShowSection(account);
        if (indexSection != null) {
            map.put("indexSection", sectionService.findIndexShowSection(account));
            List<Blog> sectionBlogs = blogService.findSectionBlogs(1, isMobile() ? 4 : 5, indexSection.getId() + "", false);
            map.put("sectionBlogs", sectionBlogs);
        }
        List<Blog> recommendBlogs = blogService.findUserRecommendBlogs(account);
        map.put("recommendBlogs", recommendBlogs);
        List<Blog> newBlogs = blogService.findUserNewBlogs(account);
        map.put("newBlogs", newBlogs);
        List<Blog> hotBlogs = blogService.findUserHotBlogs(account);
        map.put("hotBlogs", hotBlogs);
        return "blog/" + (isMobile() ? "mobile/" : "") + "index";
    }

    @RequestMapping("{account}/about")
    public String about(@PathVariable String account, ModelMap map) {
        map.putAll(commonInfoMap(account));
        map.put("aboutBlog", blogService.findUserAbout(account));
        return "blog/" + (isMobile() ? "mobile/" : "") + "about";
    }

    @RequestMapping("{account}/menu/{menuId}")
    public String menu(@PathVariable String account, @PathVariable String menuId, ModelMap map) {
        map.putAll(commonInfoMap(account));
        int page = 1;
        if (StringUtils.isNotBlank(menuId) && menuId.contains("_")) {
            try {
                page = Integer.valueOf(menuId.split("_")[1]);
            } catch (Exception e) {
                page = 1;
            }
            menuId = menuId.split("_")[0];
        }
        Menu menu = menuService.findById(menuId);
        map.put("menu", menu);
        if (menu != null) {
            List<Blog> menuBlogs = blogService.findUserMenuBlogs(account, menuId, page);
            long count = blogService.countUserMenuBlogs(account, menuId);
            long pageAll = (count / 6) + 1;
            if (pageAll == 0) {
                pageAll = 1;
            }
            map.put("pageAll", pageAll);
            map.put("count", count);
            map.put("pageNow", page);
            map.put("menuBlogs", menuBlogs);
            List<Blog> newBlogs = blogService.findUserMenuNewBlogs(account, menuId);
            map.put("newBlogs", newBlogs);
            List<Blog> hotBlogs = blogService.findUserMenuHotBlogs(account, menuId);
            map.put("hotBlogs", hotBlogs);
            List<Section> menuSections = sectionService.findByMenu(menuId);
            map.put("menuSections", menuSections);
        }
        return "blog/" + (isMobile() ? "mobile/" : "") + "menu";
    }

    @RequestMapping("{account}/section/{sectionId}")
    public String section(@PathVariable String account, @PathVariable String sectionId, ModelMap map) {
        map.putAll(commonInfoMap(account));
        int page = 1;
        if (StringUtils.isNotBlank(sectionId) && sectionId.contains("_")) {
            try {
                page = Integer.valueOf(sectionId.split("_")[1]);
            } catch (Exception e) {
                page = 1;
            }
            sectionId = sectionId.split("_")[0];
        }
        Section section = sectionService.findById(sectionId);
        map.put("section", section);
        if (section != null) {
            Menu menu = menuService.findById(section.getMenu());
            map.put("menu", menu);
            List<Blog> sectionBlogs = blogService.findUserSectionBlogs(account, sectionId, page);
            long count = blogService.countUserSectionBlogs(account, sectionId);
            long pageAll = (count / 6) + 1;
            if (pageAll == 0) {
                pageAll = 1;
            }
            map.put("pageAll", pageAll);
            map.put("count", count);
            map.put("pageNow", page);
            map.put("sectionBlogs", sectionBlogs);
            List<Blog> newBlogs = blogService.findUserSectionNewBlogs(account, sectionId);
            map.put("newBlogs", newBlogs);
            List<Blog> hotBlogs = blogService.findUserSectionHotBlogs(account, sectionId);
            map.put("hotBlogs", hotBlogs);
            List<Section> menuSections = sectionService.findByMenu(section.getMenu());
            map.put("menuSections", menuSections);
        }
        return "blog/" + (isMobile() ? "mobile/" : "") + "section";
    }

    @RequestMapping("{account}/detail/{blogId}")
    public String detail(@PathVariable String account, @PathVariable String blogId, ModelMap map) {
        map.putAll(commonInfoMap(account));
        Blog blog = blogService.findById(blogId);
        map.put("nowBlog", blog);
        if (blog != null) {
            blog.setClick(blog.getClick() + 1);
            blogService.update(blog);
            Section section = sectionService.findById(blog.getSection());
            if (section != null) {
                map.put("section", section);
                Menu menu = menuService.findById(section.getMenu());
                map.put("menu", menu);
                List<Blog> newBlogs = blogService.findUserSectionNewBlogs(account, blog.getSection());
                map.put("newBlogs", newBlogs);
                List<Blog> hotBlogs = blogService.findUserSectionHotBlogs(account, blog.getSection());
                map.put("hotBlogs", hotBlogs);
                List<Section> menuSections = sectionService.findByMenu(section.getMenu());
                map.put("menuSections", menuSections);
                Blog lastBlog = blogService.findSectionLastBlog(section.getId(), blog.getId());
                Blog nextBlog = blogService.findSectionNextBlog(section.getId(), blog.getId());
                map.put("lastBlog", lastBlog);
                map.put("nextBlog", nextBlog);
            }
        }
        long countBacukp=themeService.count(blogId);
        countBacukp= (countBacukp%PAGE_SIZE==0) ? countBacukp/PAGE_SIZE :countBacukp/PAGE_SIZE+1;
        List<Themes> themes=themeService.findByPage(1,PAGE_SIZE,blogId);
        Map<String,List<Comment>> comments=findCommentsByThems(themes);

        map.put("themes",themes);
        map.put("count",countBacukp);
        map.put("comments",comments);
		map.put("account",account);
		map.put("blog_id",blogId);

        return "blog/" + (isMobile() ? "mobile/" : "") + "detail";
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

    /**
     * 将前台页面需要的共有信息放入map中
     * @param account
     * @return
     */
    private Map<String, Object> commonInfoMap(String account) {
        Map<String, Object> map = new HashMap<>();
        JSONObject blogInfo = jsonKeyValueService.findById(App.BLOGJSON_PREFIX + account);
        if (blogInfo != null) {
            String text = blogInfo.getString("bannerText");
            if (StringUtils.isNotBlank(text)) {
                String bannerText[] = text.split(" ");
                blogInfo.put("bannerText", bannerText);
            }
            map.put("blogInfo", blogInfo);
        }
        List<Menu> menus = menuService.findAllByAccount(account);
        map.put("menus", menus);
        return map;
    }

    private boolean isMobile() {
        return HttpServletUtil.isMobileDevice(request);
    }
}
