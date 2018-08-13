package cn.missbe.missbe_www.service.comment;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.comment.Comment;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
public interface CommentService{
	void update(Comment Comment);

	PaginationResult paginateSearch(DataTableForm dataTableForm,User user);

	JsonBaseResult save(String id, String name, String avator,String content, String theme,String user);

	List<Comment> findAll();

	Comment findById(String id);

	void deleteById(String id);

	JsonBaseResult deleteByIds(List<String> ids);

	JsonBaseResult changeRecommendByIds(List<String> ids);

	List<Comment> findByTopicId(String id);

	long count(String id);
}
