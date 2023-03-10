package com.my.order.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.exception.FindException;
import com.my.order.service.OrderService;
import com.my.order.vo.OrderInfo;

public class ListController implements Controller {

	private OrderService service;

	public ListController() {
		service = new OrderService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.21:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");// 쿠키허용

		HttpSession session = request.getSession();
		String logined = (String) session.getAttribute("logined");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		if (logined == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", -2);
			map.put("msg", "로그인 하세요");
			result = mapper.writeValueAsString(map);
		} else {
			try {
				List<OrderInfo> list = service.findByOrderId(logined);
				result = mapper.writeValueAsString(list);
			} catch (FindException e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<>();
				map.put("status", -1);
				map.put("msg", e.getMessage());
				result = mapper.writeValueAsString(map);
			}
		}
		
		return result;
		
	}

}
