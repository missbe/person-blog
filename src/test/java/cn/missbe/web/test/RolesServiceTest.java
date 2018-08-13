package cn.missbe.web.test;

import cn.missbe.missbe_www.dao.RoleDao;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.service.RoleService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by missbe_www on 2017/7/3 0003.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"classpath:spring-beans.xml"})
public class RolesServiceTest {
	@Resource
	private RoleService roleService;
	@Resource
	private RoleDao roleDao;

	@Ignore
	public  void userTest(){
		List<Role> roles=roleService.findAll();
		for (int i=roles.size()-1;i>=0;i--){
			Role role=roles.get(i);
			System.out.println(role.getId()+"->"+role.getName()+"->");
			Permission[] roleArray=role.getPmss().toArray(new Permission[0]);
			for (int j=0;j<roleArray.length;j++){
				System.out.println(roleArray[j].getPermission());
			}///end for
		}///end for
	}

	@Test
	public void deleteTest(){
		roleDao.deleteById("13");
	}
}
