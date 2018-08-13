package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dao.RoleDao;
import cn.missbe.missbe_www.dao.UserDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.JsonKeyValue;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.util.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private JsonKeyValueService jsonKeyValueService;

    @Override
    public User findByAccount(String account) {
        return userDao.uniqueResultByHql("o.account = :account", HqlMapUtil.paramsToMap("account", account));
    }

    @Override
    public User login(String username, String pswd) {
        User user = userDao.uniqueResultByHql("o.account=:username AND o.password=:password",
                HqlMapUtil.paramsToMap("username,password", username, pswd));
        if (user != null) {
            Date date = user.getLastLoginTime();
            user.setLastLoginTime(new Date());
            userDao.update(user);
           // PrintUtil.print("用户登录后台"+user.getNickname()+"登录操作"+"距离上次登录:" + DateUtil.formatDuring(user.getLastLoginTime().getTime() - date.getTime()), Level.error);
        } else {
            PrintUtil.print("Login fail :" + username, Level.info);
        }
        return user;
    }

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm) {
        return userDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult save(String id, String name, String password, String[] roles) {
        if(name==null  || password==null && !(name.isEmpty() || password.isEmpty())){
            return new JsonBaseResult("角色名称和描述必须填写！",false);
        }
        if(StringUtils.isNotBlank(id)){
            User user=userDao.findById(id);
            if(user!=null){
                user.setAccount(name);
                user.setLastLoginTime(new Date());
                user.setPassword(password);
                user.setStatus(0);
                user.setNickname(name);
                if(roles!=null && roles.length>0){
                    Collection<Role> roleCollection=new ArrayList<Role>();
                    for (int i = 0; i <roles.length ; i++) {
                        roleCollection.add(roleDao.findById(roles[i]));
                    }///end for
                    user.setRoles(roleCollection);
                }
                userDao.update(user);
            }else{
                return  new JsonBaseResult("你要修改的用户已经不存在了！",false);
            }
        }else{
            User user=new User();
            user.setAccount(name);
            user.setLastLoginTime(new Date());
            user.setPassword(password);
            user.setStatus(0);
            user.setNickname(name);
            if(roles!=null && roles.length>0){
                Collection<Role> roleCollection=new ArrayList<Role>();
                for (int i = 0; i <roles.length ; i++) {
                    roleCollection.add(roleDao.findById(roles[i]));
                }///end for
                user.setRoles(roleCollection);
            }
            JsonBaseResult res=userDao.save(user);
            if(res.isSuccess()){
                PrintUtil.print("添加新的用户成功:"+name+"->"+password, SystemLog.Level.info);
            }
            return res;
        }
        return null;
    }

    @Override
    public User findById(Object userId) {
        return userDao.findById(userId.toString());
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        String msg="删除完成";
        for (int i=ids.size()-1;i>=0;i--){
            User user=userDao.findById(ids.get(i));
            if(user.getAccount().equals("admin")){
                msg="删除完成，超级管理员不能进行删除！";;
            }else{
                userDao.delete(user);
            }///end
        }
        return new JsonBaseResult(msg,true);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public JsonBaseResult changeUserInfo(String account, String password, String oldpassword, String passwordrepeat, String nickname, String avator) {
        User user = this.findByAccount(account);
        if (user != null) {
            if (StringUtils.isNotAllBlank(password, oldpassword, passwordrepeat)) {
                if (StringUtils.isOneBlank(password, oldpassword, passwordrepeat)) {
                    return new JsonBaseResult("修改密码,三个密码相关参数必填！", false);
                } else if (!MD5Util.string2MD5(oldpassword).equals(user.getPassword())) {
                    return new JsonBaseResult("旧密码填写错误！", false);
                } else if (!password.equals(passwordrepeat)) {
                    return new JsonBaseResult("两次新密码不一致！", false);
                } else {
                    if (password.length() < 6 || password.length() > 16) {
                        String[] err = {"password", "新密码长度必须在6-16个字符之间!", "passwordrepeat", "新密码长度必须在6-16个字符之间!"};
                        return new JsonBaseResult(err, false);
                    }
                    user.setPassword(password);
                }
            }
            user.setNickname(nickname);
            user.setAvator(avator);
            JsonBaseResult res = userDao.update(user);
            if (res.isSuccess()) {
                JSONObject jk = jsonKeyValueService.findById(App.BLOGJSON_PREFIX + account);
                JsonKeyValue jkv = new JsonKeyValue();
                if (jk != null) {
                    jk.put("avator", avator);
                    jkv.setKey(App.BLOGJSON_PREFIX + account);
                    jkv.setValue(jk.toJSONString());
                    jsonKeyValueService.save(jkv);
                }
            }
            return res;
        } else {
            return new JsonBaseResult("用户不存在！", false);
        }
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public JsonBaseResult changeStatus(List<String> ids) {
        long goSize = 0;
        long cancleSize = 0;
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                User user = this.findById(id);
                if (user != null) {
                    user.setStatus(user.isRecommend() ? 1 : 0);
                    userDao.update(user);
                    if (user.isRecommend()) {
                        goSize++;
                    } else {
                        cancleSize++;
                    }
                }
            }
        }
        return new JsonBaseResult("成功锁定" + (goSize) + "项.取消锁定用户" + cancleSize + "项.", true);
    }

}
