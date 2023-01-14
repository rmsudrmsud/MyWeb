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
import com.my.product.service.ProductsService;
import com.my.products.vo.Products;

public class ListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		String cp = request.getParameter("currentPage");
		int currentPage = 1;
		if(cp != null && !"".equals(cp)) {
			currentPage = Integer.parseInt(cp);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		ProductsService service = new ProductsService();
		
		
			PageBean<Products> pb;
			try {
				pb = service.findAll(currentPage);
				String jsonStr = mapper.writeValueAsString(pb);
				return jsonStr;
			} catch (FindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Map<String, String> map = new HashMap<>();
				String jsonStr = mapper.writeValueAsString(map);
				return jsonStr;
			}
		
		
	}

}
