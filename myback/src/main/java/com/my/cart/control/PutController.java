package com.my.cart.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.control.Controller;

public class PutController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//application/json 으로 하지않은 이유: 응답하지 않을거니까!
		response.setContentType("text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.21:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키 허용
		
		String prodNo = request.getParameter("prodNo"); //상품번호
		String quantity = request.getParameter("quantity"); //수량
		
		//장바구니가 클라이언트의 세션객체에 담김
		HttpSession session = request.getSession();
		Map<String, Integer> cart = (Map)session.getAttribute("cart"); //장바구니 세션 확인
		if(cart == null) {
			//cart == null이면 객체가 없다는 뜻이므로 하나만들어주고 세션에 추가하는 작업
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		int quantityNum = Integer.parseInt(quantity);
		Integer inte = (Integer) cart.get(prodNo);
		//inte == null이라면 수량이없다는 뜻
		if(inte != null) {//장바구니에 이미 상품이 있다면(그 상품번호) 수량만 증가
			quantityNum += inte;	
		}
		cart.put(prodNo, quantityNum);
		
		return "";
	}

}
