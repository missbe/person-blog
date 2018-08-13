package cn.missbe.missbe_www.service.blog;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.blog.Section;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

/**
 * @author liaoxing
 * @date 16/9/10 00:20
 */
public interface SectionService {
    Section findById(String id);

    JsonBaseResult save(String menu, String description, String content, String account, String id);

    PaginationResult paginateSearch(DataTableForm dataTableForm, String account);

    JsonBaseResult deleteByIds(List<String> ids);

    List<Section> findByMenu(String menuId);

    JsonBaseResult listByMenu(String id);

    JsonBaseResult indexShowById(String id, String account);

    Section findIndexShowSection(String account);
}
