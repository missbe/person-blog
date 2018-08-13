package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.PermissionDao;
import cn.missbe.missbe_www.dao.RoleDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.PermissionService;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Missbe
 * @date 16/8/5 18:46
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm) {
        return permissionDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult save(String id, String name, String content) {
        if(name==null || content==null){
            return new JsonBaseResult("权限名称和路径必须填写！",false);
        }
        if (StringUtils.isNotBlank(id)) {
            Permission permission=permissionDao.findById(id);
            if(permission!=null){
                permission.setName(name);
                permission.setPermission(content);
                return permissionDao.update(permission);
            }else{
                return new JsonBaseResult("你要修改的权限已经不在了！",false);
            }
        }else{
            Permission permission=new Permission();
            permission.setName(name);
            permission.setPermission(content);
            JsonBaseResult res=permissionDao.save(permission);
            if (res.isSuccess()) {
                PrintUtil.print("添加新的权限:"+name+"->"+content, SystemLog.Level.info);
            }
            return res;
        }///end
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public Permission findById(String id) {
        return permissionDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        Permission permission = permissionDao.findById(id);
        for (Role role : permission.getRoles()) {
            role.getPmss().remove(permission);
            roleDao.update(role);
        }
        permissionDao.delete(permission);
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        for (int i=ids.size()-1;i>=0;i--){
            deleteById(ids.get(i));
        }///end
        return new JsonBaseResult("删除完成",true);
    }


    @Override
    public void update(Permission per) {
        permissionDao.update(per);
    }

    @Override
    public void save(Permission per) {
        permissionDao.save(per);
    }

}