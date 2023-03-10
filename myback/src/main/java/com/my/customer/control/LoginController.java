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

public class LoginController implements Controller {
	
	private CustomerService service;
	public LoginController() {
		service = new CustomerService();
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "http://192.168.0.21:5500");
		response.addHeader("Access-Control-Allow-Credentials", "true");//쿠키 허용
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		HttpSession session = request.getSession();
		session.removeAttribute("loginedId"); // 이미로그인된경우 이전로그인정보를 없애기위한 개념!
		try {
			Customer c = service.login(id, pwd);
			session.setAttribute("logined", c.getId()); // 성공시 고객의 id 세션에저장 !
			//session.setAttribute("logined", c); 고객정보 전체저장 
			//세션의 getId=j세션..의아이디? 한번더 포스트맨요청해도 같은 세션id 이지만 다른 웹브라우저에서 요청하면 다른세션객체 ID, 다른네트워크에서해도 다른 클라이언트로 판단함!
			System.out.println("로그인성공시 세션아이디 : " + session.getId());
			result = mapper.writeValueAsString(c);
			
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1); //실패시 status라는 키 
			map.put("msg", e.getMessage());
			result = mapper.writeValueAsString(map);	
		}
		return result;
		//로그인 테스트는 포스트맨 ! 크로스오리진 헤더가져오
		
	}

}
