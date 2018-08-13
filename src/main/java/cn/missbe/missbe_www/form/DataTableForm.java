package cn.missbe.missbe_www.form;

import cn.missbe.missbe_www.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * datatable分页查询基础类
 *
 * @author liaoxing
 * @date 2016年7月29日 上午4:44:31
 */
public class DataTableForm {
    private int length = 10;
    // 前端传起始条数
    private int start = 0;
    // dataTable 防缓存
    private int draw;
    // 搜索字
    private String searchValue;
    // 排序字段
    private int orderColumn;
    // 排序类型
    private String orderType;
    // 额外的hql参数
    private Map<String, Object> params = new HashMap<>();

    public DataTableForm(HttpServletRequest request) {
        this.length = Integer.valueOf(request.getParameter("length"));
        this.start = Integer.valueOf(request.getParameter("start"));
        this.draw = Integer.valueOf(request.getParameter("draw"));
        this.searchValue = StringUtils.sqlValidate(request.getParameter("search[value]"));
        this.orderColumn = Integer.valueOf(request.getParameter("order[0][column]"));
        this.orderType = request.getParameter("order[0][dir]");
    }

    public void putParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    /**
     * 获取当前页数
     *
     * @return
     */
    public int getPageNow() {
        return start / length + 1;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(int orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
