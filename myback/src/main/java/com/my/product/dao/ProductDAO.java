package com.my.product.dao;

import java.util.List;

import com.my.exception.FindException;
import com.my.product.vo.Product;

public interface ProductDAO {
	
	
	/**
	 * 상품 목록을 검색한다
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @return 상품목록
	 * @throws 상품목록 검색시 FindException 예외 발생
	 */
	public List<Product> selectAll(int startRow, int endRow) throws FindException;
	
	
	/**
	 * 총 상품수를 검색한다
	 * @return
	 * @throws FindException
	 */
	public int totalCnt() throws FindException;
	
	/**
	 * 상품번호에 해당하는 상품을 검색한다
	 * @param prodNo 상품번호
	 * @return 상품정보
	 * @throws FindException
	 */
	public Product selectByProdNo(String prodNo) throws FindException;
}
