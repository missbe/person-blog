package cn.missbe.missbe_www.service.blog;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Menu;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:20
 */
public interface MenuService {
    Menu findById(String menu);

    JsonBaseResult save(String id, String name, String englishName, String content, String account);

    PaginationResult paginateSearch(DataTableForm dataTableForm, String account);

    JsonBaseResult deleteByIds(List<String> ids);

    List<Menu> findAllByAccount(String account);

    Menu findByMenuAndAccount(String menu, String account);
}
