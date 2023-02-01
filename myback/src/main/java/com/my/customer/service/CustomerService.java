package com.my.customer.service;

import com.my.customer.dao.CustomerDAO;
import com.my.customer.dao.CustomerDAOOracle;
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class CustomerService {
	private CustomerDAO dao;
	public CustomerService() {
		dao = new CustomerDAOOracle();
	}
	
	//어떤 id를 사용하느냐에 따라 내정보검색, 혹은 관리자가 id검색이 될 수 도 있음!
	
	public Customer login(String id, String pwd) throws FindException{
		Customer c = dao.selectById(id);
		if(c.getPwd().equals(pwd)) {
			c.setPwd(""); //보안때문에 빈문자열로 표시
			return c;
		}else {
			throw new FindException("로그인 실패");
		}
	}
	
	//id로 고객정보를 검색
	public Customer findById(String id) throws FindException{
		Customer c = dao.selectById(id);
		return c; //이곳의 예외는 dao단에서 처리한 예외처리가 해줄것 !
	}
}
