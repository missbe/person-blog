package cn.missbe.missbe_www.dao.impl;

import cn.missbe.missbe_www.dao.UserDao;
import cn.missbe.missbe_www.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findByUserAccount(String account) {
		String hql=" from User o where o.account=:account order by o.id desc ";
		List<User> userList=getSession().createQuery(hql).setParameter("account",account).list();
		return userList!=null && userList.size()>0 ? userList.get(0) : null;
	}
}
