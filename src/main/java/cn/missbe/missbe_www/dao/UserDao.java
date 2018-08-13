package cn.missbe.missbe_www.dao;

import cn.missbe.missbe_www.entity.User;

public interface UserDao extends BaseDao<User> {
	User findByUserAccount(String account);
}
