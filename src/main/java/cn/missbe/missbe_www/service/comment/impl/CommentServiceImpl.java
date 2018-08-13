package cn.missbe.missbe_www.service.comment.impl;

import cn.missbe.missbe_www.dao.UserDao;
import cn.missbe.missbe_www.dao.comment.CommentDao;
import cn.missbe.missbe_www.dao.comment.ThemeDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.comment.CommentService;
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
public class CommentServiceImpl implements CommentService {
	@Resource
	private CommentDao commentDao;
	@Resource
	private ThemeDao themeDao;
	@Resource
	private UserDao userDao;

	@Override
	public void update(Comment comment) {
		commentDao.update(comment);
	}

	@Override
	public PaginationResult paginateSearch(DataTableForm dataTableForm,User user) {
		dataTableForm.putParam("user",user);
		return commentDao.paginateSearch(dataTableForm);
	}

	@Override
	public JsonBaseResult save(String id, String name,String avator, String content, String theme_id,String user_id) {
		if(content==null || content.isEmpty()){
			return new JsonBaseResult("评论内容不能为空，请重新输入...",false);
		}
		if(StringUtils.isNotBlank(id)){
			Comment comment=commentDao.findById(id);
			if(comment!=null){
				comment.setAvator(avator);
				comment.setContent(content);
				comment.setPublisher(name);
				comment.setStatus(false);
				if(StringUtils.isNotBlank(theme_id)){
					List<Themes> list=new ArrayList<Themes>();
					list.add(themeDao.findById(theme_id));
					comment.setTheme(list);
				}
				if(StringUtils.isNotBlank(user_id)){
					User user =userDao.findByUserAccount(user_id);
					comment.setUser(user);
				}
				comment.setTime(DateUtil.formateDateyyyy_MM_ddHHmmss(new Date()));
				return commentDao.update(comment);
			}else{
				return  new JsonBaseResult("你要修改的对象已不存在了!",false);
			}
		}else{
			Comment comment=new Comment();
			comment.setAvator(avator);
			comment.setContent(content);
			comment.setPublisher(name);
			comment.setStatus(false);
			if(StringUtils.isNotBlank(theme_id)){
				List<Themes> list=new ArrayList<Themes>();
				list.add(themeDao.findById(theme_id));
				comment.setTheme(list);
			}
			if(StringUtils.isNotBlank(user_id)){
				User user =userDao.findByUserAccount(user_id);
				comment.setUser(user);
			}
			comment.setTime(DateUtil.formateDateyyyy_MM_ddHHmmss(new Date()));
			JsonBaseResult res=commentDao.save(comment);
			if(res.isSuccess()){
				res.setResult("评论提交成功，等待审核..");
				PrintUtil.print("新的评论发表成功!");
			}else{
				res.setResult("评论发表出现错误！字数在0-800字哦！");
			}
			return res;
		}
	}

	@Override
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	@Override
	public Comment findById(String id) {
		return commentDao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		commentDao.deleteById(id);
	}

	@Override
	public JsonBaseResult deleteByIds(List<String> ids) {
		int err=ids.size();
		for (int i = 0; i < ids.size(); i++) {
			if(StringUtils.isNotBlank(ids.get(i))){
				commentDao.deleteById(ids.get(i));
				err--;
			}
		}
		return new JsonBaseResult("成功删除:"+err+"项内容",true);
	}

	@Override
	public JsonBaseResult changeRecommendByIds(List<String> ids) {
		long goSize = 0;
		long cancleSize = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				Comment comment = this.findById(id);
				if (comment != null) {
					comment.setStatus(!comment.isStatus());
					commentDao.update(comment);
					if (comment.isStatus()) {
						goSize++;
					} else {
						cancleSize++;
					}
				}
			}
		}
		return new JsonBaseResult("审核评论回复通过" + (goSize) + "项内容.未通过" + cancleSize + "项.", true);
	}

	@Override
	public List<Comment> findByTopicId(String id) {
		return commentDao.findByTopicId(themeDao.findById(id));
	}

	@Override
	public long count(String id) {
		Themes theme=themeDao.findById(id);
		return themeDao.count("o.theme=:theme and o.status=:status", HqlMapUtil.paramsToMap("theme,status", theme,true));

	}
}
