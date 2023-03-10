package com.my.customer.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class CustomerDAOOracle implements CustomerDAO {
	private SqlSessionFactory sqlSessionFactory;

	public CustomerDAOOracle() {
		String resource = "config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Customer selectById(String id) throws FindException {
		
			if(sqlSessionFactory == null) {
				throw new FindException("예외발생:sqlSessionFactory가 null입니다.");
			}
			
			SqlSession session = sqlSessionFactory.openSession();
			Customer c = session.selectOne("com.my.customer.CustomerMapper.selectById", id); //"네임스페이스속성값.select태그 id 속성값 ", "selectone 일땐 정확한값"
			if(c!= null) {
				session.close();
				return c;
			}else{
				throw new FindException("아이디에 해당하는 고객이 없습니다.");
			}
		//return null; 리턴이 필요없는경우 -> 다 예외처리를 해주었기때문에 !
	}
	
	//메인메서드
	public static void main(String[] args) {
		CustomerDAOOracle dao = new CustomerDAOOracle();
		try {
			System.out.println(dao.selectById("id1"));
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
