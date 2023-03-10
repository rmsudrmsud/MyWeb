package com.my.order.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.order.vo.OrderInfo;
import com.my.order.vo.OrderLine;

public class OrderDAOOracle implements OrderDAO {
	private SqlSessionFactory sqlSessionFactory;
	public OrderDAOOracle() {
		String resource = "config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insert(OrderInfo orderInfo) throws AddException {
		//이 작업이 한 트렌젝션단위로 처리되어야 함!
		//마이바티스에서 쓰이는 SqlSession session 는 db와의 연결을 유지하는 커넥션객체와 같은 역할!
		//그래서 이곳에쓰이는 SqlSession이 다르면 다른 트렌젝션. 이 객체를 insert에서 획득하게 !
		if(sqlSessionFactory == null) {
			throw new AddException("예외발생:sqlSessionFactory가 null입니다");
		}
		
		//session을 인자로 넣어줌
		SqlSession session = sqlSessionFactory.openSession();
		try {
		insertInfo(session, orderInfo);
		//System.out.println(orderInfo.getOrderNo());
		
		for(OrderLine orderLine: orderInfo.getLines()) {
			orderLine.setOrderNo(orderInfo.getOrderNo());
			insertLine(session, orderLine);
		}
		//정상적으로 For문까지 처리되었을 시!
		session.commit();
		}catch(Exception e) {//문제가발생시 세션롤백후 메시지 출력!
			session.rollback();
			e.printStackTrace();
		}finally {
			if(session != null) {
			session.close();
			}
		}
		
	}

	//이곳에서세션을 사용하기 위해 매개변수로 세션도 선언해줌!
	private void insertInfo(SqlSession session, OrderInfo orderInfo) throws Exception{
		session.insert("com.my.order.OrderMapper.insertInfo", orderInfo);
	}
	
	private void insertLine(SqlSession session, OrderLine orderLine) throws Exception{
		session.insert("com.my.order.OrderMapper.insertLine", orderLine);
	}
	
	@Override
	public List<OrderInfo> selectByOrderId(String id) throws FindException {
		if(sqlSessionFactory == null) {
			throw new FindException("예외발생:sqlSessionFactory가 null입니다");
		}
		//session을 인자로 넣어줌
		SqlSession session = sqlSessionFactory.openSession();
		List<OrderInfo> list = session.selectList("com.my.order.OrderMapper.selectByOrderId",id);
		return list;
	}
	
//	public static void main(String[] args) {
//		OrderDAOOracle dao = new OrderDAOOracle();
//		try {
//			List<OrderInfo> list = dao.selectByOrderId("id1");
//			for(OrderInfo info:list) {
//				int orderNo = info.getOrderNo();
//				Date orderDt = info.getOrderDt();
//				List<OrderLine> lines = info.getLines();
//				System.out.println(orderNo + ":" + orderDt);
//				for(OrderLine line:lines) {//라인의 번호, 수량, 정보
//					System.out.println(line.getOrderNo()+ ":" + line.getOrderQuantity()+":" + line.getOrderP());
//				}
//				System.out.println("----------------");
//				//System.out.println(info);
//			}
//		} catch (FindException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//메인메서드는 테스트로만 쓰고 테스트끝난 이후에는 삭제하는게 퍼포먼스에 좋음! 
	//자바는 메인메서드를 보통 끝쪽에 작성하고 테스트이후에 삭제 혹은 주석처리!
//	public static void main(String[] args) {
//		OrderDAOOracle dao = new OrderDAOOracle();
//		OrderInfo orderinfo = new OrderInfo();
//		orderinfo.setOrderId("id1"); //주문자 아이디
//		List<OrderLine> lines = new ArrayList<>();
//		for(int i=1; i<=2; i++) {
//		//line 타입의 line 한개 
//		OrderLine line = new OrderLine();
//		Product p = new Product();
//		p.setProdNo("C000"+i); //주문상품 번호
//		line.setOrderP(p);
//		line.setOrderQuantity(i); //주문수량
//		lines.add(line);
//		}
//		
//		orderinfo.setLines(lines);
//		try {
//			dao.insert(orderinfo);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
