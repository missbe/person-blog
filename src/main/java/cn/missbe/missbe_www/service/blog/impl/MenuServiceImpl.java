package cn.missbe.missbe_www.service.blog.impl;

import cn.missbe.missbe_www.dao.blog.MenuDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.service.blog.MenuService;
import cn.missbe.missbe_www.util.HqlMapUtil;
import cn.missbe.missbe_www.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:21
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Override
    public Menu findById(String menu) {
        return menuDao.findById(menu);
    }

    @Override
    public JsonBaseResult save(String id, String name, String englishName, String content, String account) {
        if (StringUtils.isNotBlank(id)) {
            Menu menuOld = this.findById(id);
            if (menuOld == null) {
                return new JsonBaseResult("不存在该菜单.", false);
            }
            if (!menuOld.getAuthorAccount().equals(account)) {
                return new JsonBaseResult("您无权修改该菜单!", false);
            }
            menuOld.setEnglishName(englishName);
            menuOld.setName(name);
            menuOld.setContent(content);
            return menuDao.update(menuOld);
        } else {
            Menu m = new Menu();
            m.setAuthorAccount(account);
            m.setName(name);
            m.setEnglishName(englishName);
            m.setContent(content);
            return menuDao.save(m);
        }
    }

    @Override
    public PaginationResult paginateSearch(DataTableForm dataTableForm, String account) {
        dataTableForm.putParam("authorAccount", account);
        return menuDao.paginateSearch(dataTableForm);
    }

    @Override
    public JsonBaseResult deleteByIds(List<String> ids) {
        long errorSize = ids.size();
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                menuDao.deleteById(id);
                errorSize--;
            }
        }
        return new JsonBaseResult("成功删除" + (ids.size() - errorSize) + "个菜单.", true);
    }

    @Override
    public List<Menu> findAllByAccount(String account) {
        return menuDao.findListByHql("o.authorAccount=:account", HqlMapUtil.paramsToMap("account", account));
    }

    @Override
    public Menu findByMenuAndAccount(String menu, String account) {
        return menuDao.uniqueResultByHql("o.menu = :menu AND o.authorAccount=:account", HqlMapUtil.paramsToMap("menu,account", menu, account));
    }
}
