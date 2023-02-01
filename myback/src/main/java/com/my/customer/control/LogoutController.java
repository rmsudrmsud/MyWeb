package com.my.customer.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.customer.service.CustomerService;
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

public class LogoutController implements Controller {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.21:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키 허용
		
		//로그아웃시 세션을 죽일건지 loginedId 어트리뷰트만 지울건지 선택
		//장바구니같은 정ㅇ보를 남기고 지울것인가 세션을 그냥 다 지울것인가 ?
		HttpSession session = request.getSession();
		//세션을 강제로 죽이는 메서
		System.out.println("로그아웃시 세션아이디 : " + session.getId());
		session.invalidate();
	//	session.removeAttribute("loginedId"); // 이미로그인된경우 이전로그인정보를 없애기위한 개념!
		
		return "";
		//로그인 테스트는 포스트맨 ! 크로스오리진 헤더가져오
		
	}

}
