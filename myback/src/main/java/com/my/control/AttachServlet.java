package com.my.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/attach/*")
public class AttachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];
		if("upload".equals(subPath)) {
			upload(request,response);
		}else if("download".equals(subPath)) {
			download(request,response);
		}
		
	}
	
	private void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		//요청데이터로 전달된 no
		String fileName = request.getParameter("no");
		String opt = request.getParameter("opt");
		String saveDirectory = "/Users/choigeunhyeong/Documents/attach";
		String subDirectory = fileName.substring(0,1); //서브디렉토리명
		File dir = new File(saveDirectory, subDirectory);
		dir.listFiles(); // 디렉토리 내부의 파일목록 배열
		File[] files = dir.listFiles();
		for(File f:files) {
			if(f.getName().split("_")[1].equals(fileName)){// 혹은 split으로_뒤쪽을 꺼내서 c0001을 정확히찾아내기 위해서
				//찾은경우
					//응답헤더 설정
				//nio..? 라이브러리에서 찾는
				String contentType = Files.probeContentType(f.toPath()); //파일의 형식 jpg뿐아니라 pdf,등등 여러개도 올릴수있게
				response.setHeader("Content-type", contentType);//응답형식
//					response.setHeader("Content-Type", "image/jpg");
					response.setHeader("Content-Length", ""+f.length());
					if("attachment".equals(opt)) {//다운로드
					//아래두개는 둘중하나만 사용
					//response.setHeader("content-disposition", "inline;filename=" + f.getName()); //바로응답. 한글깨짐 현상나오면 한글파일 인코딩
					response.setHeader("content-disposition", "attachment;filename=" + f.getName());//다운로드 방법 attachment;filename=" filename: 다운로드될 파일이름 다른것으로 바꿔도됨
					}else {//attachment가아니면 바로응답
						response.setHeader("content-disposition", "inline;filename=" + f.getName()); //바로응답. 한글깨짐 현상나오면 한글파일 인코딩
					}
					FileInputStream fis = new FileInputStream(f);
					OutputStream os = response.getOutputStream();
					int readValue = -1;
					while((readValue = fis.read())!= -1) {//응답 바디에 채울 내용부분 while
						os.write(readValue);
					}
			}//백엔드가 클라이언트에게 응답할땐 정확하게 알려줘야함(몇바이트로 응답할것이고.. 등등)
			//브랜치테스트
			
		}
		//찾지못한경우
		
		
	}
	
	private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
//		InputStream is = request.getInputStream();
//		int readValue = -1;
//		while((readValue = is.read()) != -1) {
//			System.out.print((char)readValue);
//		}
		
		//첨부파일이 저장될 폴더
		String saveDirectory = "/Users/choigeunhyeong/Documents/attach";
		String tempDirectory = "/Users/choigeunhyeong/Documents/attachtemp";
		
		
		int maxPostSize = 100*1024;
		String encoding = "UTF-8";
		//파일첨부
		//MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
		MultipartRequest mr = new MultipartRequest(request, tempDirectory, maxPostSize, encoding);
		
		//File file = mr.getFile("f");
		File[] files = new File(tempDirectory).listFiles();
		for(File file: files) {
			String fileName = file.getName();
			long fileLength = file.length();
			System.out.println("fileName=" + fileName + ", fileLength=" + fileLength);
	
			//폴더생성
			String subDirectory = fileName.substring(0, 1);//C G
			File dir = new File(saveDirectory, subDirectory);
			if(!dir.exists()) {
				dir.mkdir();
			}		
			//파일 옮기기
			//File origin = new File(saveDirectory, fileName);//원본
			File origin = new File(tempDirectory, fileName);//원본
			
			File copy = new File(dir, UUID.randomUUID() + "_" + fileName); //복사본
			
			//1)원본읽기
			FileInputStream fis = null;
			fis = new FileInputStream(origin);
			
			//2)복사본붙여넣기
			FileOutputStream fos = null;
			fos = new FileOutputStream(copy);
			
			int readValue = -1;
			while((readValue = fis.read()) != -1) {
				fos.write(readValue);
			}
			
			fos.close();
			fis.close();
			
			//3)원본삭제
			origin.delete();
		}
		
		
		String t = mr.getParameter("t");

	}
}


//@WebServlet("/upload")
//public class AttachServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	protected void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//이곳에서 업로드, 다운로드 다하고싶어서 do post에서 service
//		
//		response.addHeader("Access-Control-Allow-Origin", "*"); // "헤더","접속할 ip" 동일근원정책 위반 해결
//		
		//--라이브러리 없이 파일업로드 start--
		//InputStream is = request.getInputStream(); //요청시에 어떤 정보가 오는지 인풋스트림으로 체크, 이미지파일 업로드할거면 한글깨짐 고려안해도될듯?
//		//1바이트씩 읽어서 출력해보기(인풋스트림의 처음부터끝까지 읽어야 함으로 반복문도 사용
//		int readValue = -1;
//		while((readValue = is.read()) != -1) {
//			System.out.print((char)readValue); //텍스트파일은 (char)파일로변환 ,이미지는 저러면 안됨!
//		}
		//--라이브러리 없이 파일업로드 end--
		
		
		//cos.jar 활용
//		String saveDirectory ="/Users/choigeunhyeong/Documents/attach";
//		int maxPostSize = 100*1024; //100kb 파일 
//		String encoding = "UTF-8";
//		
//		//파일첨부
//		MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding); //요청, savedirecoty 에 저장 매개변수를 조절해서 원하는값 . 인코딩, 맥시멈사이즈지정
//		//String t = mr.getParameter("t"); 이건뭐징?
//	
//		//File file = mr.getFile("f");  파일 여러개선택해도 최종파일 하나만 선택됨.
//		//cos.jar를 계속 활용해서 여러개를 업로드 하고싶다면
//		File[] files = new File(saveDirectory).listFiles(); //디렉토리에 업로드된 파일들을 천부찾아서 
//		for(File file:files) {
//			String fileName = file.getName();
//			long fileLength = file.length();
//			System.out.println("fileName="+fileName+",fileLength="+fileLength);			
//			
//			//서브디렉토리 만드는작업 (폳더생성)
//			String subDirectory = fileName.substring(0,1);
//			File dir = new File(saveDirectory, subDirectory); //파일디렉토리 객체생성 new File(부모, 자식)
//			if(!dir.exists()) {
//				dir.mkdir(); // 실제 디렉토리만드는것 
//			}
//			
//			//파일 옮기기 작업
//			File origin = new File(saveDirectory, fileName); //원본
//			File copy = new File(dir,UUID.randomUUID()+"_"+fileName); // 복사본. UUID.randomUUID(): 중복되지않는 문자값을 랜덤으로 뽑아서 
//			//자바유틸패키지에있는 라이브러리 UUID
//			
//			//원본읽기
//			FileInputStream fis = null;
//			fis = new FileInputStream(origin);
//			
//			//복사본 붙여넣기
//			FileOutputStream fos = null;
//			fos = new FileOutputStream(copy);
//			int readValue = -1; //1바이트씩 읽어서 복사붙여넣기 -1: 파일의 끝
//			while((readValue = fis.read()) != -1) {
//				fos.write(readValue);
//			}
//			fos.close();
//			fis.close();
//			
//			//원본제거
//			origin.delete();
//		}
//	}
//
//}
