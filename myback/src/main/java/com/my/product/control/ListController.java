package com.my.product.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.dto.PageBean;
import com.my.exception.FindException;
import com.my.product.service.ProductService;
import com.my.product.vo.Product;

public class ListController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*"); // "헤더","접속할 ip"
		// cors정책위반 때문에 해결하기위해서 addHeader
		String cp = request.getParameter("currentPage");
		// http://localhost:8888/myback/productlist
		// http://localhost:8888/myback/productlist?currentPage=
		int currentPage = 1;
		if (cp != null && !"".equals(cp)) {
			currentPage = Integer.parseInt(cp);
		}
		ObjectMapper mapper = new ObjectMapper();
		ProductService service = new ProductService(); //productservice 타입의 객체 생성
		try {
			PageBean<Product> pb = service.findAll(currentPage);
			String jsonStr = mapper.writeValueAsString(pb); // pagebean내용을 문자열로만들어서 응답!
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
	
	
	
}
