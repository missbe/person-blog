package cn.missbe.missbe_www.controller.blog;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyg
 * @date 16/9/10 01:48
 */
@Controller
@RequestMapping("/user/blog/menu")
public class MenuController extends BaseController {
    @Resource
    private MenuService menuService;

    @RequestMapping("addUI")
    public String addUI(ModelMap map, String id) {
        putUrlToSession();
        if (StringUtils.isNotBlank(id)) {
            map.put("menu", menuService.findById(id));
        }
        return "user/blog/menu/addUI";
    }

    @RequestMapping("add")
    @ResponseBody
    public JsonBaseResult add(String id, String name, String content, String englishName) {
        return menuService.save(id, name, englishName, content, getUser().getAccount());
    }

    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Menu().dataTableTrs());
        map.put("className", "menu");
        return "user/blog/list";
    }

    @RequestMapping("list")
    @ResponseBody
    public PaginationResult list() {
        return menuService.paginateSearch(new DataTableForm(request), getUser().getAccount());
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return menuService.deleteByIds(ids);
    }
}
