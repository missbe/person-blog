package cn.missbe.missbe_www.service.blog.impl;

import cn.missbe.missbe_www.dao.blog.BlogDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.blog.BlogService;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.service.blog.SectionService;
import cn.missbe.missbe_www.util.DateUtil;
import cn.missbe.missbe_www.util.HqlMapUtil;

import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:21
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private MenuService menuService;
    @Resource
    private SectionService sectionService;

    @Override
    public Blog findById(String id) {
        return blogDao.findById(id);
    }

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm, String account) {
        dataTableForm.putParam("authorAccount", account);
        return blogDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        long errorSize = ids.size();
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                blogDao.deleteById(id);
                errorSize--;
            }
        }
        return new JsonBaseResult("成功删除" + (ids.size() - errorSize) + "篇文章.", true);
    }

    @Override
    public JsonBaseResult save(String menu, String section, String title, String content, String id, String account, String nickname) {
        Section s = sectionService.findById(section);
        Menu m = menuService.findById(menu);
        if (m == null) {
            return new JsonBaseResult("菜单必须选择!", false);
        }
        if (s == null) {
            return new JsonBaseResult("栏目必须选择!", false);
        }
        if (!s.getMenu().equals(m.getId() + "")) {
            return new JsonBaseResult("栏目和菜单不对应，请检查后重试.", false);
        }
        if (StringUtils.isNotBlank(id)) {
            Blog blog = blogDao.findById(id);
            if (blog != null) {
                blog.setMenu(m.getId() + "");
                blog.setMenuName(m.getName());
                blog.setAuthorAccount(account);
                blog.setSection(section);
                blog.setContent(content);
                blog.setAuthor(nickname);
                blog.setImage(StringUtils.getOneSrcOfImg(content));
                blog.setSectionName(s.getDescription());
                blog.setTitle(title);
                JsonBaseResult res = blogDao.update(blog);
                return res;
            } else {
                return new JsonBaseResult("修改的对应文章已不存在", false);
            }
        } else {
            Blog blog = new Blog();
            blog.setMenu(m.getId() + "");
            blog.setMenuName(m.getName());
            blog.setAuthorAccount(account);
            blog.setSection(section);
            blog.setContent(content);
            blog.setAuthor(nickname);
            blog.setImage(StringUtils.getOneSrcOfImg(content));
            blog.setDate(DateUtil.formateDateyyyy_MM_ddHHmmss(new Date()));
            blog.setSectionName(s.getDescription());
            blog.setTitle(title);
            JsonBaseResult res = blogDao.save(blog);
            if (res.isSuccess()) {
                PrintUtil.print("新的博文发表了"+blog.getAuthor()+"发表文章"+blog.getSimpleContent() + blog.getAuthorAccount() + "/detail/" + blog.getId() + ".html", SystemLog.Level.info);
            }
            return res;
        }
    }

    @Override
    public JsonBaseResult changeRecommendByIds(List<String> ids) {
        long goSize = 0;
        long cancleSize = 0;
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                Blog blog = this.findById(id);
                if (blog != null) {
                    blog.setRecommend(!blog.isRecommend());
                    blogDao.update(blog);
                    if (blog.isRecommend()) {
                        goSize++;
                    } else {
                        cancleSize++;
                    }
                }
            }
        }
        return new JsonBaseResult("成功推荐" + (goSize) + "篇文章.取消" + cancleSize + "篇.", true);
    }

    @Override
    public List<Blog> findSectionBlogs(int page, int pageSize, String sectionId, boolean hot) {
        return blogDao.findList(page, pageSize, "o.section=:sectionId order by " + (hot ? "click" : "id") + " desc", HqlMapUtil.paramsToMap("sectionId", sectionId));
    }

    @Override
    public List<Blog> findUserRecommendBlogs(String account) {
        return blogDao.findList(1, 6, "o.authorAccount=:authorAccount AND o.recommend=true order by id desc", HqlMapUtil.paramsToMap("authorAccount", account));
    }

    @Override
    public List<Blog> findUserNewBlogs(String account) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount order by id desc", HqlMapUtil.paramsToMap("authorAccount", account));
    }

    @Override
    public List<Blog> findUserHotBlogs(String account) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount order by click desc", HqlMapUtil.paramsToMap("authorAccount", account));
    }

    @Override
    public Blog findUserAbout(String account) {
        List<Blog> list = blogDao.findList(1, 1, "o.authorAccount=:authorAccount AND o.title='关于我' order by id desc", HqlMapUtil.paramsToMap("authorAccount", account));
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public List<Blog> findUserMenuBlogs(String account, String menuId, int page) {
        return blogDao.findList(page, 6, "o.authorAccount=:authorAccount AND o.menu=:menu order by id desc", HqlMapUtil.paramsToMap("authorAccount,menu", account, menuId));
    }

    @Override
    public List<Blog> findUserMenuNewBlogs(String account, String menuId) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount AND o.menu=:menu order by id desc", HqlMapUtil.paramsToMap("authorAccount,menu", account, menuId));
    }

    @Override
    public List<Blog> findUserMenuHotBlogs(String account, String menuId) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount AND o.menu=:menu order by click desc", HqlMapUtil.paramsToMap("authorAccount,menu", account, menuId));
    }

    @Override
    public long countUserMenuBlogs(String account, String menuId) {
        return blogDao.count("o.authorAccount=:authorAccount AND o.menu=:menu", HqlMapUtil.paramsToMap("authorAccount,menu", account, menuId));
    }

    @Override
    public List<Blog> findUserSectionBlogs(String account, String sectionId, int page) {
        return blogDao.findList(page, 6, "o.authorAccount=:authorAccount AND o.section=:section order by id desc", HqlMapUtil.paramsToMap("authorAccount,section", account, sectionId));
    }

    @Override
    public long countUserSectionBlogs(String account, String sectionId) {
        return blogDao.count("o.authorAccount=:authorAccount AND o.section=:section", HqlMapUtil.paramsToMap("authorAccount,section", account, sectionId));
    }

    @Override
    public List<Blog> findUserSectionNewBlogs(String account, String sectionId) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount AND o.section=:section order by id desc", HqlMapUtil.paramsToMap("authorAccount,section", account, sectionId));
    }

    @Override
    public List<Blog> findUserSectionHotBlogs(String account, String sectionId) {
        return blogDao.findList(1, 12, "o.authorAccount=:authorAccount AND o.section=:section order by click desc", HqlMapUtil.paramsToMap("authorAccount,section", account, sectionId));
    }

    @Override
    public Blog findSectionLastBlog(long sectionId, long blogId) {
        List<Blog> lasts = blogDao.findList(1, 1, "o.section=:section AND o.id < :id order by id desc", HqlMapUtil.paramsToMap("section,id", sectionId + "", blogId));
        return lasts.isEmpty() ? null : lasts.get(0);
    }

    @Override
    public Blog findSectionNextBlog(long sectionId, long blogId) {
        List<Blog> nexts = blogDao.findList(1, 1, "o.section=:section AND o.id > :id order by id asc", HqlMapUtil.paramsToMap("section,id", sectionId + "", blogId));
        return nexts.isEmpty() ? null : nexts.get(0);
    }

    @Override
    public void update(Blog blog) {
        blogDao.update(blog);
    }
}
