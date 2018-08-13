package cn.missbe.missbe_www.service.blog;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:20
 */
public interface BlogService {
    Blog findById(String id);

    PaginationResult paginateSearch(DataTableForm dataTableForm, String account);

    JsonBaseResult deleteByIds(List<String> ids);

    JsonBaseResult save(String menu, String section, String title, String content, String id, String account, String nickname);

    JsonBaseResult changeRecommendByIds(List<String> ids);

    List<Blog> findSectionBlogs(int page, int pageSize, String sectionId, boolean hot);

    List<Blog> findUserRecommendBlogs(String account);

    List<Blog> findUserNewBlogs(String account);

    List<Blog> findUserHotBlogs(String account);

    Blog findUserAbout(String account);

    List<Blog> findUserMenuBlogs(String account, String menuId, int page);

    List<Blog> findUserMenuNewBlogs(String account, String menuId);

    List<Blog> findUserMenuHotBlogs(String account, String menuId);

    long countUserMenuBlogs(String account, String menuId);

    List<Blog> findUserSectionBlogs(String account, String sectionId, int page);

    long countUserSectionBlogs(String account, String sectionId);

    List<Blog> findUserSectionNewBlogs(String account, String sectionId);

    List<Blog> findUserSectionHotBlogs(String account, String sectionId);

    Blog findSectionLastBlog(long sectionId, long blogId);

    Blog findSectionNextBlog(long sectionId, long blogId);

    void update(Blog blog);
}
