package com.my.order.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.order.vo.OrderInfo;

public interface OrderDAO {
	//orderInfo 에 lines들이 여러개 들어갈 수 있으니 여기에 매개변수 orderInfo 하나로 가능
	void insert(OrderInfo orderInfo) throws AddException;
	
	List<OrderInfo> selectByOrderId(String id) throws FindException;
}


