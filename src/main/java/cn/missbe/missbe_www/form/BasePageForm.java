package cn.missbe.missbe_www.form;

/**
 * 分页查询基础类
 *
 * @author liaoxing
 * @date 2016年7月29日 上午4:44:31
 */
public class BasePageForm {
	private int pageSize = 10;
	// 前端传起始条数
	private int startRow = 0;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		pageSize = pageSize > 100 ? 100 : pageSize;
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前页数
	 *
	 * @return
	 */
	public int getPageNow() {
		return startRow / pageSize + 1;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	@Override
	public String toString() {
		return "BasePageForm [pageSize=" + pageSize + ", startRow=" + startRow + "]";
	}
}