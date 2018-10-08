package common.tools.mybatisUtils.pagination;

import org.apache.commons.lang3.StringUtils;

public class BaseModel {
	
	private int page;
	
	private int rows;
	
	private String order;
	
	private String sort;
	

	public int getPage() {
		if(page<=0){
			return 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		if(rows<=0){
			return 20;
		}
		if(rows>200){
			return 200;
		}
		return rows;
	}
	
	public String getOrder() {
		if(order == null || order.equals("")){
			return "desc";
		}
		return order;
	}
	
	public String getOrder(String order) {
		return StringUtils.isEmpty(this.order)?order:this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}
	
	public String getSort(String def) {
		return sort==null?def:sort.trim();
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
