package cn.missbe.missbe_www.dao.comment;

import cn.missbe.missbe_www.dao.BaseDao;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.entity.comment.Themes;

import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
public interface CommentDao extends BaseDao<Comment> {
	List<Comment> findByTopicId(Themes themes);
}
