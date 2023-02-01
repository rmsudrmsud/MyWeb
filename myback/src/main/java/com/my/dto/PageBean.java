package com.my.dto;

import java.util.List;

import lombok.Getter;
@Getter
//값을 조회만 할 수 있는 Getter
//페이지사용시 PageBean<Product> pb = new PageBean(~)

//타입 제네릭 T. product가 T에대입.
public class PageBean<T> {
	//한페이지에 보여줄 최대목록수 3개상품을 보여줌
	public final static int CNT_PER_PAGE=3; //값을 바꿀수없게 상수선언 final static 자바에서상수선언 보통대문자
	//페이지 그룹수? // <이전> [1], [2] <다음> 2개!
	public final static int CNT_PER_PAGE_GROUP=2;
	private List<T> list;
	//totalCnt : 총 게시물 개수
	private int totalCnt;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int currentPage;
	
	public PageBean(int currentPage, List<T>list, int totalCnt) {
		this.currentPage = currentPage;
		this.list = list;
		this.totalCnt = totalCnt;
		totalPage = (int)Math.ceil((double)totalCnt/CNT_PER_PAGE);
		startPage = (currentPage-1)/CNT_PER_PAGE_GROUP*CNT_PER_PAGE_GROUP+1;
		endPage = startPage + CNT_PER_PAGE_GROUP - 1;
	}
}
