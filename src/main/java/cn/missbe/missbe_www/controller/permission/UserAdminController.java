package cn.missbe.missbe_www.controller.permission;

/**
 * Created by missbe_www on 2017/7/3 0003.
 */

import cn.missbe.missbe_www.controller.BaseController;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.service.RoleService;
import cn.missbe.missbe_www.service.UserService;
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
@RequestMapping("/user/users/users")
public class UserAdminController extends BaseController{
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	@RequestMapping(value = "addUI",name = "角色添加页面")
	public String addUI(ModelMap map, String id) {
		putUrlToSession();
		if (StringUtils.isNotBlank(id)) {
			User user=userService.findById(id);
			map.put("user",user);
		}
		map.put("roles",roleService.findAll());
		return "user/permission/user/addUI";
	}

	@RequestMapping(value = "add",name = "用户添加方法")
	@ResponseBody
	public JsonBaseResult add(String id, String name, String password, @RequestParam(value = "roles[]") String[] roles) {
		return userService.save(id, name, password,roles);
	}

	@RequestMapping("listUI")
	public String listUI(ModelMap map) {
		putUrlToSession();
		map.put("columns", new User().dataTableTrs());
		map.put("className", "users");
		return "user/permission/user/list";
	}

	@RequestMapping(value = "changeStatus")
	@ResponseBody
	public JsonBaseResult changeStatus(@RequestParam("ids[]") List<String> ids){
		return userService.changeStatus(ids);
	}
	@RequestMapping(value = "list",name = "用户管理列表")
	@ResponseBody
	public PaginationResult list() {
		return userService.paginateSearch(new DataTableForm(request));
	}

	@RequestMapping("delete")
	@ResponseBody
	public JsonBaseResult delete(@RequestParam("ids[]") List<String> ids) {
		return userService.deleteByIds(ids);
	}
}
