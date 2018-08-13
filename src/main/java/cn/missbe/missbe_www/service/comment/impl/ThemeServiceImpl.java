package cn.missbe.missbe_www.service.comment.impl;

import cn.missbe.missbe_www.dao.UserDao;
import cn.missbe.missbe_www.dao.blog.BlogDao;
import cn.missbe.missbe_www.dao.comment.CommentDao;
import cn.missbe.missbe_www.dao.comment.ThemeDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.comment.ThemeService;
import cn.missbe.missbe_www.util.DateUtil;
import cn.missbe.missbe_www.util.HqlMapUtil;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
@Service
public class ThemeServiceImpl implements ThemeService {
	@Resource
	private ThemeDao themeDao;
	@Resource
	private BlogDao blogDao;
	@Resource
	private UserDao userDao;
	@Resource
	private CommentDao commentDao;

	@Override
	public void update(Themes themes) {
		themeDao.update(themes);
	}

	@Override
	public PaginationResult paginateSearch(DataTableForm dataTableForm,User user) {
		dataTableForm.putParam("user",user);
		return themeDao.paginateSearch(dataTableForm);
	}

	@Override
	public JsonBaseResult save(String id, String name, String avator,String content,String user_id,String blog_id) {
		if(content==null || content.isEmpty()){
			return new JsonBaseResult("评论内容不能为空，请重新输入...",false);
		}
		if(StringUtils.isNotBlank(id)){
			Themes themes =themeDao.findById(id);
			if(themes !=null){
				themes.setAvator(avator);
				themes.setContent(content);
				themes.setPublisher(name);
				themes.setStatus(false);
				if(StringUtils.isNotBlank(user_id)){
					User user=userDao.findByUserAccount(user_id);
					themes.setUser(user);
				}
				if(StringUtils.isNotBlank(blog_id)){
					Blog blog=blogDao.findById(blog_id);
					themes.setBlog(blog);
				}

				themes.setTime(DateUtil.formateDateyyyy_MM_ddHHmmss(new Date()));
				return themeDao.update(themes);
			}else{
				return  new JsonBaseResult("你要修改的对象已不存在了!",false);
			}
		}else{
			Themes themes =new Themes();
			themes.setAvator(avator);
			themes.setContent(content);
			themes.setPublisher(name);
			themes.setStatus(false);
			if(StringUtils.isNotBlank(user_id)){
				User user=userDao.findByUserAccount(user_id);
				themes.setUser(user);
			}
			if(StringUtils.isNotBlank(blog_id)){
				Blog blog=blogDao.findById(blog_id);
				themes.setBlog(blog);
			}

			themes.setTime(DateUtil.formateDateyyyy_MM_ddHHmmss(new Date()));
			JsonBaseResult res=themeDao.save(themes);
			if(res.isSuccess()){
				PrintUtil.print("新的主题发表成功!");
				res.setResult("评论提交成功，等待审核..");
			}else{
				res.setResult("评论发表出现错误！字数在0-800字哦！.");
			}
			return res;
		}
	}

	@Override
	public List<Themes> findAll() {
		return themeDao.findAll();
	}

	@Override
	public Themes findById(String id) {
		return themeDao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		themeDao.deleteById(id);
	}

	@Override
	public JsonBaseResult changeRecommendByIds(List<String> ids) {
		long goSize = 0;
		long cancleSize = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				Themes themes = themeDao.findById(id);
				if (themes != null) {
					themes.setStatus(!themes.isStatus());
					themeDao.update(themes);
					if (themes.isStatus()) {
						goSize++;
					} else {
						cancleSize++;
					}
				}
			}
		}
		return new JsonBaseResult("审核主题通过" + (goSize) + "项内容.未通过" + cancleSize + "项.", true);
	}

	@Override
	public long count(String blog_id) {
		Blog blog=blogDao.findById(blog_id);
		return themeDao.count("o.blog=:blog and o.status=:status", HqlMapUtil.paramsToMap("blog,status", blog,true));

	}

	@Override
	public List<Themes> findByPage(int i, int pageSize, String id) {
		Blog blog=blogDao.findById(id);
		return themeDao.findList(i, pageSize, "o.blog=:blog and o.status=:status order by id desc", HqlMapUtil.paramsToMap("blog,status", blog,true));
	}

	@Override
	public JsonBaseResult deleteByIds(List<String> ids) {
		int err=ids.size();
		for (int i = 0; i < ids.size(); i++) {
			if(StringUtils.isNotBlank(ids.get(i))){
				Themes theme=themeDao.findById(ids.get(i));
				clearComment(theme.getComments());////手动清除主题下的评论
				themeDao.deleteById(ids.get(i));
				err--;
			}
		}
		return new JsonBaseResult("成功删除:"+err+"项内容",true);
	}
	private void clearComment(List<Comment> comment){
		List<String> ids=new ArrayList<>();
		for (int i = 0; i < comment.size(); i++) {
			ids.add(""+comment.get(i).getId());
		}
		for (int i = 0; i < ids.size(); i++) {
			if(StringUtils.isNotBlank(ids.get(i))){
				commentDao.deleteById(ids.get(i));
			}
		}
	}
}
