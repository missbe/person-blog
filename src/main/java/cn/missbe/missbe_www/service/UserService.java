package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

public interface UserService {

    /**
     * 根据账号查找用户
     *
     * @param account
     * @return
     */

    User findByAccount(String account);

    /**
     * 判断登录
     *
     * @param username
     * @param pswd
     * @return
     */
    User login(String username, String pswd);


    PaginationResult paginateSearch(DataTableForm dataTableForm);

    JsonBaseResult save(String id, String name, String password,String[] roles);

    User findById(Object userId);

    JsonBaseResult deleteByIds(List<String> ids);

    void save(User user);

    JsonBaseResult changeUserInfo(String account, String password, String oldpassword, String passwordrepeat, String nickname, String avator);

    List<User> findAll();

	JsonBaseResult changeStatus(List<String> ids);
}
