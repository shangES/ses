package com.mk.framework.grid.page;

import java.io.Serializable;

public class Page implements Serializable {

	public static int DEFAULT_PAGESIZE = 12;
	private int pageno; // 当前页码
	private int pagesize;// 每页记录数
	private int total; // 总页数
	private int totalrows; // 总记录数
	private int start; // 起始位置
	private int end; // 结束位置
	private int nextPageNo;// 下页页码
	private int prePageNo;// 上页页码

	public Page() {
		setTotal(1);
		setPageno(1);
	}

	public void init() {
		if (pageno <= 0) {
			pageno = 1;
		}
		if (pagesize <= 0) {
			pagesize = DEFAULT_PAGESIZE;
		}
		int start = 1;
		if (totalrows <= (pageno - 1) * pagesize) {
			if (totalrows % pagesize == 0) {
				pageno = totalrows / pagesize;
			} else {
				pageno = totalrows / pagesize + 1;
			}
		}
		if (pageno <= 0) {
			pageno = 1;
		}
		start = pagesize * (pageno - 1);
		int totalpage = 1;
		if (totalrows % pagesize == 0) {
			totalpage = totalrows / pagesize;
		} else {
			totalpage = totalrows / pagesize + 1;
		}
		total = totalpage;
		this.start = start;
		this.end = start + pagesize;
		this.nextPageNo = pageno + 1;
		this.prePageNo = pageno - 1;
	}

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public int getPageno() {
		return pageno;
	}

	public int getTotal() {
		return total;
	}

	public void setPageno(int i) {
		pageno = i;
	}

	public void setTotal(int i) {
		total = i;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public int getPrePageNo() {
		return prePageNo;
	}

	public void setPrePageNo(int prePageNo) {
		this.prePageNo = prePageNo;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
}
