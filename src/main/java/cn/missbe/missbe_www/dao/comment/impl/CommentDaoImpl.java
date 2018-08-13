package cn.missbe.missbe_www.dao.comment.impl;

import cn.missbe.missbe_www.dao.comment.CommentDao;
import cn.missbe.missbe_www.dao.impl.BaseDaoImpl;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {
	@Override
	public List<Comment> findByTopicId(Themes theme) {

		String sql="select * from comment_missbe where comment_id IN ("+
				"select comments_themes_id from themes_comments_mgt where themes_comments_id=?)";
		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
		sqlQuery.setInteger(0,theme.getId());
		sqlQuery.addEntity(Comment.class);
		List<Object> objects=sqlQuery.list();

		List<Comment> comments=new ArrayList<>();
		for (int i = 0; i <objects.size() ; i++) {
			Object o=objects.get(i);
			if(o instanceof  Comment){
				comments.add((Comment) o);
			}
		}
		return comments;
	}
}
