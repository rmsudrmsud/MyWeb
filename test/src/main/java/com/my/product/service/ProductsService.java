package com.my.product.service;

import java.util.List;

import com.my.dto.PageBean;
import com.my.exception.FindException;
import com.my.products.dao.ProductsDAO;
import com.my.products.dao.ProductsDAOOracle;
import com.my.products.vo.Products;


public class ProductsService {
	
	public Products info(String prodNo) throws FindException{
		ProductsDAO dao = new ProductsDAOOracle();
		dao.selectByProdNo(prodNo);
		return dao.selectByProdNo(prodNo);
	}
	
	public PageBean<Products> findAll(int currentPage) throws FindException{
		ProductsDAO dao;
		dao = new ProductsDAOOracle();
		//currentPage :현재페이지
		int cntPerPage = 3; //한페이지에 보여줄 최대목록수
		int startRow = (currentPage-1)*cntPerPage + 1;
		int endRow = currentPage*cntPerPage;
		//return dao.selectAll(startRow, endRow);
		List<Products> list = dao.selectAll(startRow, endRow);
		
		int totalCnt = dao.totalCnt(); //총상품수
		PageBean<Products> pb = new PageBean(currentPage, list, totalCnt);
		return pb;
	}
	
	//테스트용 메서드!
	public static void main(String[] args) {
		ProductsDAO dao = new ProductsDAOOracle();
		try {
			Products p =  dao.selectByProdNo("C0001");
			System.out.println(p.toString());
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
	
}
