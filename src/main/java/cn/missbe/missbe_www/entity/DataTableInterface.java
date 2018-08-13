package cn.missbe.missbe_www.entity;

/**
 * @author liaoxing
 * @date 16/9/10 13:34
 */
public interface DataTableInterface {
    /**
     * 列表头的中文数组
     *
     * @return
     */
    String[] dataTableTrs();

    /**
     * 有序的数组值
     *
     * @return
     */
    String[] toDataTableArray();

    /**
     * 有序的实体变量属性名字
     *
     * @return
     */
    String[] indexVarNames();

    /**
     * 搜索字段的名字
     */
    String searchVar();
}
