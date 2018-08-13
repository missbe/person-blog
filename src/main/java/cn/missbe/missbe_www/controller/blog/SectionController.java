package cn.missbe.missbe_www.controller.blog;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.service.blog.SectionService;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/11 01:34
 */
@Controller
@RequestMapping("/user/blog/section")
public class SectionController extends BaseController {

    @Resource
    private SectionService sectionService;
    @Resource
    private MenuService menuService;

    @RequestMapping("addUI")
    public String addUI(ModelMap map, String id) {
        putUrlToSession();
        List<Menu> menuList = new ArrayList<>();
        if (StringUtils.isNotBlank(id)) {
            Section section = sectionService.findById(id);
            Menu menu = menuService.findById(section.getMenu());
            if (menu != null) {
                menuList.add(menu);
                List<Menu> otherMenus = menuService.findAllByAccount(getUser().getAccount());
                otherMenus.remove(menu);
                menuList.addAll(otherMenus);
            } else {
                menuList = menuService.findAllByAccount(getUser().getAccount());
            }
            map.put("section", section);
        } else {
            menuList = menuService.findAllByAccount(getUser().getAccount());
        }
        map.put("menus", menuList);
        return "user/blog/section/addUI";
    }

    @RequestMapping("add")
    @ResponseBody
    public JsonBaseResult add(String menu, String description, String content, String id) {
        return sectionService.save(menu, description, content, getUser().getAccount(), id);
    }


    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Section().dataTableTrs());
        map.put("className", "section");
        return "user/blog/section/list";
    }

    @RequestMapping("list")
    @ResponseBody
    public PaginationResult list() {
        return sectionService.paginateSearch(new DataTableForm(request), getUser().getAccount());
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return sectionService.deleteByIds(ids);
    }

    @RequestMapping("listByMenu")
    @ResponseBody
    public JsonBaseResult listByMenu(String id) {
        return sectionService.listByMenu(id);
    }

    @RequestMapping("indexShow")
    @ResponseBody
    public JsonBaseResult indexShow(String id) {
        return sectionService.indexShowById(id, getUser().getAccount());
    }

}
