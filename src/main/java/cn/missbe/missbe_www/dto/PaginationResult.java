package cn.missbe.missbe_www.dto;

import cn.missbe.missbe_www.entity.DataTableInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果集,泛型结果集类型
 *
 * @author lx
 * @date 2015-8-2
 */
public final class PaginationResult<E> {

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List<String[]> data = new ArrayList<>();


    public PaginationResult(long total, int draw, List<E> results) {
        super();
        this.recordsTotal = total;
        this.recordsFiltered = total;
        this.draw = draw;
        int i = 0;
        if (results != null && !results.isEmpty()) {
            if (results.get(0) instanceof DataTableInterface) {
                for (E e : results) {
                    data.add(((DataTableInterface) e).toDataTableArray());
                }
            }
        }
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}
