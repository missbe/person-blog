package cn.missbe.missbe_www.dao;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础Dao接口
 *
 * @param <T>
 * @author liaoxing
 * @date 2016年7月25日 下午4:24:41
 */
public interface BaseDao<T> {
    /**
     * 保存对象
     *
     * @param t
     */
    JsonBaseResult save(T t);

    /**
     * 删除对象
     *
     * @param t
     */
    void delete(T t);

    /**
     * 删除id为id的对象
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 删除对象通过编号列表
     *
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 删除满足条件的对象
     *
     * @param hql
     * @param param
     */
    void deleteByHql(String hql, Map<String, Object> param);

    /**
     * 更新对象
     *
     * @param t
     */
    JsonBaseResult update(T t);

    /**
     * 通过id查找对象
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 查找所有对象
     *
     * @return
     */
    List<T> findAll();

    /**
     * 通过Hql语句查找满足条件的对象列表
     *
     * @param hql
     * @param param
     * @return
     */
    List<T> findListByHql(String hql, Map<String, Object> param);

    /**
     * 分页查询满足对象列表
     *
     * @param page
     * @param pageSize
     * @param hql
     * @param param
     * @return
     */
    List<T> findList(int page, int pageSize, String hql, Map<String, Object> param);

    /**
     * 查询记录数
     *
     * @param hql   不包含select count(*) 的条件语句
     * @param param 参数
     * @return
     */
    long count(String hql, Map<String, Object> param);

    /**
     * 更新列表数据到数据库
     *
     * @param list
     */
    void updateList(List<T> list);

    /**
     * 特殊分页查找，返回 List<Map<String, Object>>
     *
     * @param hql
     * @param page
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> findListMapByHql(String hql, int page, int pageSize);

    /**
     * 列表保存
     *
     * @param collection
     */
    void saveList(Collection<T> collection);

    /**
     * 基本分页查找
     *
     * @param dataTableForm
     * @return
     */
    PaginationResult<T> paginateSearch(DataTableForm dataTableForm);

    /**
     * 查出置顶字段集的map list
     *
     * @param hql
     * @return
     */
    List<Map<String, Object>> findListMapByHql(String hql);

    /**
     * 寻找唯一的实体
     *
     * @param hql
     * @param param
     * @return
     */
    T uniqueResultByHql(String hql, Map<String, Object> param);

}