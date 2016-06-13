package com.chinainpay.apps.membercard.util;

public class PubPage {

	private int start;
	private int end;
	private int totalPage;
	private int page;

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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PubPage getPage(String str, int totalCount) {
		int count = 10;
		Integer page;
		if (str != null) {
			page = Integer.parseInt(str);
		} else {
			page = 1;
		}
		int start = count * (page - 1);
		int end = count * page;
		int totalPage;
		if (totalCount % count == 0) {
			totalPage = totalCount / count;
		} else {
			totalPage = (totalCount / count) + 1;
		}
		if (totalCount <= count) {
			totalPage = 1;
		}
		PubPage pp = new PubPage();
		pp.setPage(page);
		pp.setTotalPage(totalPage);
		pp.setStart(start);
		pp.setEnd(end);
		return pp;
	}
}
