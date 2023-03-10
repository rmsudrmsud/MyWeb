//		String contextPath = request.getContextPath();// 지금사용중인 ConTextPath 반환 : /myback
//		String servletPath = request.getServletPath(); // 서블릿에 대한 url정보를 반환
//		String uri = request.getRequestURI();
//		String url = request.getRequestURL().toString();
//		System.out.println("contextPath = " + contextPath);
//		System.out.println("servletPath = " + servletPath);
//		System.out.println("uri = " + uri); // uri = /myback/product/list , uri = /myback/product/info 맨끝의 product/ 이뭔지
//											// 구분하고싶으면?
//		System.out.println("url = " + url);
//		String[] arr = uri.split("/");
//		String subPath = arr[arr.length - 1]; // uri = /myback/product/a 맨끝의 product/"" 이뭔지 구분해서 수행
//		//여기아래로는 난이도있는코드 자바기본이 잘되어있어야함!
//		String envFileName = "product.properties"; //환경정보가 담겨있는 파일
//		envFileName = getServletContext().getRealPath(envFileName);
//		Properties env = new Properties();// 라이브러리
//		env.load(new FileInputStream(envFileName));//그파일을 로드해야해서 FileInputStream사용 이 파일이 JVM에서 쓸수있는 환경정보가 되도록 설정한 것
//		String className = env.getProperty("subPath"); //subPath 에 해당하는 프로퍼티 이름으로 값 찾기
//		try {
//			Class clazz = Class.forName(className);//클래스네임을 가지고 런타임. 로드한 클래스 기반으로 객체생성
//			//Class.forName의 인자로 사용해서 JVM메모리 위쪽으로올려주는것(런타임다이나믹로드 실행시에필요한것을 올려줌..?)
//			//ListController가 위쪽으로 올라온것
//			//Object obj = clazz.newInstance(); deprecated 되어도 안되는건 아니지만 api를 찾아봐서 대체할 녀석 찾아보기
//			Object obj = clazz.getDeclaredConstructor().newInstance();//프로퍼티 키에 해당하는 값. 클래스를찾아서 JVM메모리 위로 올린것을 객체생성!
//			Controller controller = (Controller)obj; //컨트롤러 인터페이스에서 파생된 녀석이니까 ..?
//			String result = controller.execute(request, response);
//			response.getWriter().print(result);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		
//		
//		
////		if ("list".equals(subPath)) {
////			list(request, response);
////		} else if ("info".equals(subPath)) {
////			info(request, response);
////		}
//
//	}
//
////	private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
////		response.setContentType("application/json;charset=UTF-8");
////		response.addHeader("Access-Control-Allow-Origin", "*"); // "헤더","접속할 ip"
////		// cors정책위반 때문에 해결하기위해서 addHeader
////		PrintWriter out = response.getWriter();
////		String cp = request.getParameter("currentPage");
////		// http://localhost:8888/myback/productlist
////		// http://localhost:8888/myback/productlist?currentPage=
////		int currentPage = 1;
////		if (cp != null && !"".equals(cp)) {
////			currentPage = Integer.parseInt(cp);
////		}
////		ObjectMapper mapper = new ObjectMapper();
////		ProductService service = new ProductService();
////		try {
////			// List<Product> list = service.findAll(currentPage);
////			// String jsonStr = mapper.writeValueAsString(list);
////			// Map<String, Object> map = service.findAll(currentPage);
////			// String jsonStr = mapper.writeValueAsString(map);
////
////			PageBean<Product> pb = service.findAll(currentPage);
////			String jsonStr = mapper.writeValueAsString(pb); // pagebean내용을 문자열로만들어서 응답!
////			out.print(jsonStr);
////		} catch (FindException e) {
////			e.printStackTrace();
////			Map<String, String> map = new HashMap<>();
////			map.put("msg", e.getMessage());
////			String jsonStr = mapper.writeValueAsString(map);
////			out.print(jsonStr);
////		}
////	}
////	
////	//상셍정보를해보려면 dao, oracle sql 서비스 만들고 프론트까지!
////	private void info(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
////		response.setContentType("application/json;charset=UTF-8");
////		response.addHeader("Access-Control-Allow-Origin", "*"); // "헤더","접속할 ip"
////		// cors정책위반 때문에 해결하기위해서 addHeader
////		response.getWriter().print("상품상세정보입니다");
////	}
//
//}

package com.my.product.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.control.Controller;

@WebServlet("/product/*")
public class ProductController extends HttpServlet { private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString(); //url : StringBuffer 타입이라 toString 필요
		System.out.println("contextPath=" + contextPath);
		System.out.println("servletPath=" + servletPath);
		System.out.println("uri=" + uri); //uri=/myback/product/list, uri=/myback/product/info
		System.out.println("url=" + url);
		
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];
		String envFileName = "product.properties";
		//배포된 실제경로찾는 realpath. getServletContext 최상위경로 반환 : //myback getServletPath : /product
		//getContextPath() : 지금 사용하는 ContextPath를 반환 → contextPath: /myback
		//getServletPath() : 서블릿에 대한 url 정보를 반환 → servletPath: /product
		envFileName = getServletContext().getRealPath(envFileName);
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));//파일인풋스트림(스트림리더)으로 읽어주고 객체로 올려줌 .load가 필요한 인자 :stream
		String className = env.getProperty(subPath);//proeperties 키 값 찾아냄
		try {
			Class clazz = Class.forName(className);//클래스로 다시만들어서 jvm으로 올려줌
			Object obj = clazz.getDeclaredConstructor().newInstance(); //클래스의 객체 생성 newInstance
			Controller controller = (Controller)obj; //객체 사용할 수 있도록 
			String result = controller.execute(request, response); //컨트롤러execute 객체메서드 호출
			response.getWriter().print(result);//json타입으로 가져온값을 getWriter로 뽑아서 결과응답
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if("list".equals(subPath)) {
//			list(request, response);
//		}else if("info".equals(subPath)) {
//			info(request, response);
//		}
		
	}
//	private void info(HttpServletRequest request, HttpServletResponse response)
//		    throws IOException, ServletException{
//		response.getWriter().print("상품상세정보입니다");
//	}
//	private void list(HttpServletRequest request, HttpServletResponse response)
//	    throws IOException, ServletException{
//		response.setContentType("application/json;charset=UTF-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		PrintWriter out = response.getWriter();
//		String cp = request.getParameter("currentPage");
//		//http://localhost:8888/myback/productlist
//		//http://localhost:8888/myback/productlist?currentPage=
//		int currentPage = 1;
//		if(cp != null && !"".equals(cp)) {
//			currentPage = Integer.parseInt(cp);
//		}		
//		ObjectMapper mapper = new ObjectMapper();
//		ProductService service = new ProductService();
//		try {
//			//List<Product> list = service.findAll(currentPage);
//			//String jsonStr = mapper.writeValueAsString(list);
//			//Map<String, Object> map = service.findAll(currentPage);
//			//String jsonStr = mapper.writeValueAsString(map);
//			
//			PageBean<Product> pb = service.findAll(currentPage);
//			String jsonStr = mapper.writeValueAsString(pb);
//			out.print(jsonStr);
//		} catch (FindException e) {
//			e.printStackTrace();
//			Map<String, String> map = new HashMap<>();
//			map.put("msg", e.getMessage());
//			String jsonStr = mapper.writeValueAsString(map);
//			out.print(jsonStr);
//		}
//	}
}

