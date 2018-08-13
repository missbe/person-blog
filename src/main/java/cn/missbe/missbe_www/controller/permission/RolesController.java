package cn.missbe.missbe_www.controller.permission;

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.service.RoleService;
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
@RequestMapping("/user/roles/roles")
public class RolesController extends BaseController {
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @RequestMapping(value = "addUI",name = "角色添加页面")
    public String addUI(ModelMap map, String id) {
        putUrlToSession();
        if (StringUtils.isNotBlank(id)) {
            Role role=roleService.findById(id);
            map.put("roles",role);
        }
        map.put("permissions",permissionService.findAll());
        return "user/permission/role/addUI";
    }

    @RequestMapping(value = "add",name = "角色添加方法")
    @ResponseBody
    public JsonBaseResult add(String id, String name, String content,@RequestParam(value = "permissions[]") String[] permissions) {
        return roleService.save(id, name, content,permissions);
    }

    @RequestMapping("listUI")
    public String listUI(ModelMap map) {
        putUrlToSession();
        map.put("columns", new Role().dataTableTrs());
        map.put("className", "roles");
        return "user/permission/role/list";
    }

    @RequestMapping(value = "list",name = "角色管理列表")
    @ResponseBody
    public PaginationResult list() {
        return roleService.paginateSearch(new DataTableForm(request));
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
        return roleService.deleteByIds(ids);
    }
}
