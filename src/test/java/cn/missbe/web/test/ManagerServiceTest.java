package cn.missbe.web.test;

import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/31 0031.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"classpath:spring-beans.xml"})
public class ManagerServiceTest {
	@Resource
	private UserService userService;

	@Test
	public void loginTest() {
		try {

			User user=userService.login("admin", MD5Util.string2MD5("123456"));


			if(user==null){
				System.out.println("========未找到===========");
			}else{
				user.setLastLoginTime(new Date());///更新登录时间
				System.out.println("========找到并更新登录时间===========");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
