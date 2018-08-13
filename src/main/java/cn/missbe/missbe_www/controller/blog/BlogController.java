package cn.missbe.missbe_www.controller.blog;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.blog.BlogService;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.service.blog.SectionService;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyg
 * @date 16/9/11 01:34
 */
@Controller
@RequestMapping("/user/blog/blog")
public class BlogController extends BaseController {

    @Resource
    private BlogService blogService;
    @Resource
    private MenuService menuService;
    @Resource
    private SectionService sectionService;
    @Resource
    private JsonKeyValueService jsonKeyValueService;

    @RequestMapping("addUI")
    public String addUI(ModelMap map, String id) {
        putUrlToSession();
        List<Menu> menuList = new ArrayList<>();
        List<Section> sectionList = new ArrayList<>();
        if (StringUtils.isNotBlank(id)) {
            Blog blog = blogService.findById(id);
            Menu menu = menuService.findById(blog.getMenu());
            if (menu != null) {
                menuList.add(menu);
                List<Menu> otherMenus = menuService.findAllByAccount(getUser().getAccount());
                otherMenus.remove(menu);
                menuList.addAll(otherMenus);
                Section section = sectionService.findById(blog.getSection());
                if (section != null) {
                    sectionList.add(section);
                    List<Section> otherSections = sectionService.findByMenu(menu.getId() + "");
                    otherSections.remove(section);
                    sectionList.addAll(otherSections);
                } else {
                    sectionList = sectionService.findByMenu(menu.getId() + "");
                }
            } else {
                menuList = menuService.findAllByAccount(getUser().getAccount());
            }
            map.put("blog", blog);
        } else {
            menuList = menuService.findAllByAccount(getUser().getAccount());
            if (!menuList.isEmpty())
                sectionList = sectionService.findByMenu(menuList.get(0).getId() + "");
        }
        map.put("menus", menuList);
        map.put("sections", sectionList);
        return "user/blog/blog/addUI";
    }

    @RequestMapping("add")
    @ResponseBody
    public JsonBaseResult add(String menu, String section, String title, String content, String id) {
        JSONObject blogUserInfo = jsonKeyValueService.findById(App.BLOGJSON_PREFIX + getUser().getAccount());
        return blogService.save(menu, section, title, content, id, getUser().getAccount(), blogUserInfo == null ? "" : blogUserInfo.getString("name"));
    }


    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Blog().dataTableTrs());
        map.put("className", "blog");
        return "user/blog/blog/list";
    }

    @RequestMapping("list")
    @ResponseBody
    public PaginationResult list() {
        return blogService.paginateSearch(new DataTableForm(request), getUser().getAccount());
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return blogService.deleteByIds(ids);
    }

    @RequestMapping("changeRecommend")
    @ResponseBody
    public JsonBaseResult changeRecommend(@RequestParam("ids[]") List<String> ids) {
        return blogService.changeRecommendByIds(ids);
    }

}
