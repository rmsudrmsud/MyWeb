package com.my.cart.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;

public class ListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		response.setContentType("application/json; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.21:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키 허용
		
		HttpSession session = request.getSession();
		Map<String, Integer> cart = (Map) session.getAttribute("cart");
		
		List<Map<String,Object>> list = new ArrayList<>();
		//Map의 keySet은 키들의 집합
		Set<String> keys = cart.keySet(); //키들
		for(String prodNo : keys) {
				Integer quantity = cart.get(prodNo);
				Map<String, Object> subMap = new HashMap<>();
				subMap.put("prodNo", prodNo);
				subMap.put("quantity", quantity);
				list.add(subMap);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(list);
		
		
		//목록을 사용자들이 보고싶어한다면, cart의 내용을 하나하나 꺼내줘서
		//String result = mapper.writeValueAsString(cart);
		
		//Map<Product, Integer> resultMap = new HashMap<>();map 만들고 
		//이런 패턴으로 내용을 채워서 
		//resultMap.put(p, cart.get(prodNo)); map 채우고 
		
		//장바구니의 상품번호별 상품사세정보얻기 
		//for(String prodNo : cart.keySet()) { db에서 값을찾아오고 
			//Product p = session.selectOne(" ", prodNo);
		//}
		//ObjectMapper mapper = new ObjectMapper();
		//String result = mapper.writeValueAsString(resultMap); json문자열맵 값 꺼내기
		return result;
		
	}

}
