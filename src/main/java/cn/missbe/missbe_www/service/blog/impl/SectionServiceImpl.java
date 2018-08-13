package cn.missbe.missbe_www.service.blog.impl;

import cn.missbe.missbe_www.dao.blog.SectionDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.service.blog.SectionService;
import cn.missbe.missbe_www.util.HqlMapUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:21
 */
@Service
public class SectionServiceImpl implements SectionService {
    @Resource
    private SectionDao sectionDao;
    @Resource
    private MenuService menuService;

    @Override
    public Section findById(String id) {
        return sectionDao.findById(id);
    }

    @Override
    public JsonBaseResult save(String menu, String description, String content, String account, String id) {
        Menu m = menuService.findById(menu);
        if (m == null) {
            return new JsonBaseResult("菜单不存在!", false);
        }
        if (StringUtils.isNotBlank(id)) {
            Section section1 = this.findById(id);
            if (section1 == null) {
                return new JsonBaseResult("该栏目不存在！", false);
            } else {
                section1.setMenu(menu);
                section1.setDescription(description);
                section1.setAuthorAccount(account);
                section1.setContent(content);
                section1.setMenuName(m.getName());
                return sectionDao.update(section1);
            }
        } else {
            Section section1 = new Section();
            section1.setMenu(menu);
            section1.setMenuName(m.getName());
            section1.setAuthorAccount(account);
            section1.setContent(content);
            section1.setDescription(description);
            return sectionDao.save(section1);
        }
    }

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm, String account) {
        dataTableForm.putParam("authorAccount", account);
        return sectionDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        long errorSize = ids.size();
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                sectionDao.deleteById(id);
                errorSize--;
            }
        }
        return new JsonBaseResult("成功删除" + (ids.size() - errorSize) + "个栏目.", true);
    }

    @Override
    public List<Section> findByMenu(String menuId) {
        if (StringUtils.isBlank(menuId)) {
            return Collections.emptyList();
        }
        return sectionDao.findListByHql("o.menu = :menu", HqlMapUtil.paramsToMap("menu", menuId));
    }

    @Override
    public JsonBaseResult listByMenu(String id) {
        List<Section> sections = this.findByMenu(id);
        if (sections.isEmpty()) {
            return new JsonBaseResult("该菜单还没有栏目，需要先添加栏目.", false);
        }
        return new JsonBaseResult(sections, true);
    }

    @Override
    public JsonBaseResult indexShowById(String id, String account) {
        Section section = this.findById(id);
        if (section == null) {
            return new JsonBaseResult("该栏目已不存在.", false);
        } else {
            Section oldSection = findIndexShowSection(account);
            if (oldSection != null) {
                oldSection.setIndexShow(false);
                JsonBaseResult oldRes = sectionDao.update(oldSection);
                if (oldRes.isSuccess()) {
                    section.setIndexShow(true);
                    return sectionDao.update(section);
                } else {
                    return oldRes;
                }
            } else {
                section.setIndexShow(true);
                return sectionDao.update(section);
            }
        }
    }

    @Override
    public Section findIndexShowSection(String account) {
        return sectionDao.uniqueResultByHql("o.authorAccount=:account AND o.indexShow = true", HqlMapUtil.paramsToMap("account", account));
    }
}
