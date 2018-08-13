package cn.missbe.missbe_www.service.comment;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.comment.Themes;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

/**
 * Created by missbe_www on 2017/7/5 0005.
 */
public interface ThemeService {
	void update(Themes Themes);

	PaginationResult paginateSearch(DataTableForm dataTableForm, User user);

	JsonBaseResult save(String id, String name, String avator,String content,String user,String blog);

	List<Themes> findAll();

	Themes findById(String id);

	void deleteById(String id);

	JsonBaseResult deleteByIds(List<String> ids);

	JsonBaseResult changeRecommendByIds(List<String> ids);

	long count(String blog_id);

	List<Themes> findByPage(int i, int pageSize, String id);
}
