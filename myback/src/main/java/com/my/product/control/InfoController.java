package com.my.product.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.control.Controller;
import com.my.exception.FindException;
import com.my.product.service.ProductService;
import com.my.product.vo.Product;

public class InfoController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*"); // "헤더","접속할 ip"
		String prodNo = request.getParameter("prodNo");
		ProductService service = new ProductService();
		//컨트롤? 서비스단에서는 throw없이그냥 예외처리
		try {
			Product p = service.info(prodNo);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(p);
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
	
}