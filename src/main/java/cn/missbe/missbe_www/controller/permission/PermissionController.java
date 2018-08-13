package cn.missbe.missbe_www.controller.permission;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.PermissionService;
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
@RequestMapping("/user/permission/permission")
public class PermissionController extends BaseController {
    @Resource
    private PermissionService permissionService;

    @RequestMapping(value = "addUI",name = "权限添加页面")
    public String addUI(ModelMap map, String id) {
        putUrlToSession();
        if (StringUtils.isNotBlank(id)) {
            Permission permission=permissionService.findById(id);
            map.put("permission",permission);
        }
        return "user/permission/permission/addUI";
    }

    @RequestMapping(value = "add",name = "权限添加方法")
    @ResponseBody
    public JsonBaseResult add(String id, String name, String content) {
        return permissionService.save(id, name, content);
    }

    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Permission().dataTableTrs());
        map.put("className", "permission");
        return "user/permission/permission/list";
    }

    @RequestMapping(value = "list",name = "权限管理列表")
    @ResponseBody
    public PaginationResult list() {
        return permissionService.paginateSearch(new DataTableForm(request));
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return permissionService.deleteByIds(ids);
    }
}
