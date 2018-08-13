package cn.missbe.web.test;

import cn.missbe.missbe_www.dao.UserDao;
import cn.missbe.missbe_www.dao.comment.CommentDao;
import cn.missbe.missbe_www.dao.comment.ThemeDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.service.blog.BlogService;
import cn.missbe.missbe_www.service.comment.CommentService;
import cn.missbe.missbe_www.service.comment.ThemeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"classpath:spring-beans.xml"})
public class ThemesDaoTest {
	@Resource
	private ThemeDao themeDao;
	@Resource
	private ThemeService themeService;
	@Resource
	private CommentDao commentDao;
	@Resource
	private CommentService commentService;
	@Resource
	private UserService userService;
	@Resource
	private BlogService blogService;
	@Resource
	private UserDao userDao;

	@Test
	public void commentSaveTest(){
		commentService.save(null,"love",null,"love_love-love","6","admin");
	}
	@Test
	public void themeSaveTest(){

		String id="1";
		Themes themes1 =new Themes();
		themes1.setId(1);
		themes1.setStatus(false);
		themes1.setContent("要疯了要疯了要疯了要疯了要疯了要疯了要疯了要疯了要疯了");
		themes1.setUser(userDao.findById("1"));
		themes1.setBlog(blogService.findById("44"));
		themeDao.save(themes1);

//		Themes theme=themeDao.findById(id);
//		if(theme!=null){
//			System.out.println("----->"+theme.isStatus());
//			theme.setStatus(!theme.isStatus());
//			JsonBaseResult res=themeDao.update(theme);
//			if(res!=null ){
//				System.out.println(res.isSuccess()+"--->"+res.getResult().toString());
//			}
//			System.out.println("----->"+theme.isStatus());
//		}
	}
	@Test
	public void themeDeleteTest(){
		String[] ids={"4"};
		List<String> list=new ArrayList<>();
		for (int i = 0; i <ids.length ; i++) {
			list.add(ids[i]);
		}
		themeService.deleteByIds(list);
	}
	@Test
	public void commentFind(){

		List<Comment> comments=commentDao.findByTopicId(themeDao.findById("6"));
		for (int i = 0; i < comments.size(); i++) {
			Comment o=comments.get(i);
			if(o!=null){
				System.out.println(o.getId()+"->"+o.getTime());
			}
		}
	}
	@Test
	public void commentDeleteTest(){
		String[] ids={"2"};
		commentDao.deleteByIds(ids);
	}
	@Test
	public void changeCommentTest(){
		Comment comment1=new Comment();
		comment1.setId(1);
		comment1.setStatus(true);
		comment1.setContent("要疯了要疯了要疯了要疯了要疯了要疯了要疯了要疯了要疯了");
		List list=new ArrayList<Themes>();
		list.add(themeDao.findById("1"));
		comment1.setTheme(list);
		commentDao.save(comment1);
//		String id="1";
//		Comment comment=commentDao.findById(id);
//		if(comment!=null){
//			System.out.println("----->"+comment.isStatus());
//			comment.setStatus(!comment.isStatus());
//			JsonBaseResult res=commentDao.update(comment);
//			if(res!=null ){
//				System.out.println(res.isSuccess()+"--->"+res.getResult().toString());
//			}
//			System.out.println("----->"+comment.isStatus());
//		}
	}
}
