package com.my.products.dao;

import java.util.List;

import com.my.exception.FindException;
import com.my.products.vo.Products;

public interface ProductsDAO {
	/**
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws FindException
	 */
	public List<Products> selectAll(int startRow, int endRow) throws FindException;
	
	/**
	 * 
	 * @return
	 * @throws FindException
	 */
	public int totalCnt() throws FindException;
	
	/**
	 * 
	 * @param prodNo
	 * @return
	 * @throws FindException
	 */
	public Products selectByProdNo(String prodNo) throws FindException;
}
