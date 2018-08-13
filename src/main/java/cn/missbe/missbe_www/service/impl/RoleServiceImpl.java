package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.PermissionDao;
import cn.missbe.missbe_www.dao.RoleDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.RoleService;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm) {
        return roleDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult save(String id, String name, String content,String[] permissions) {
        if(name==null  || content==null && !(name.isEmpty() || content.isEmpty())){
            return new JsonBaseResult("角色名称和描述必须填写！",false);
        }
        if (StringUtils.isNotBlank(id)) {
            Role role=roleDao.findById(id);
            if(role!=null){
                role.setName(name);
                role.setDescription(content);
                if(permissions!=null && permissions.length>0){
                    Collection<Permission> permissionsCollect=new ArrayList<>();
                    for (int i=0;i<permissions.length;i++){
                        permissionsCollect.add(permissionDao.findById(permissions[i]));
                    }
                    role.setPmss(permissionsCollect);
                }///end if
                return roleDao.update(role);
            }else{
                return  new JsonBaseResult("你要修改的角色已经不存在了！",false);
            }
        }else{
            Role role=new Role();
            role.setName(name);
            role.setDescription(content);
            if(permissions!=null && permissions.length>0){
                Collection<Permission> permissionsCollect=new ArrayList<>();
                for (int i=0;i<permissions.length;i++){
                    permissionsCollect.add(permissionDao.findById(permissions[i]));
                }
                role.setPmss(permissionsCollect);

            }///end if
            JsonBaseResult res=roleDao.save(role);
            if(res.isSuccess()){
                PrintUtil.print("添加新的角色成功:"+name+"->"+content, SystemLog.Level.info);
            }
            return res;
        }
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        roleDao.deleteById(id);
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        for (int i=ids.size()-1;i>=0;i--){
            deleteById(ids.get(i));
        }///end
        return new JsonBaseResult("删除完成",true);
    }
}
