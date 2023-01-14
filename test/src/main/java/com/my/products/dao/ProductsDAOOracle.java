package com.my.products.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.exception.FindException;
import com.my.products.vo.Products;

public class ProductsDAOOracle implements ProductsDAO {

	@Override
	public List<Products> selectAll(int startRow, int endRow) throws FindException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Products> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin@localhost:1521:xe";
			String user = "system";
			String password = "chlrmsgud123";
			
			conn = DriverManager.getConnection(url, user, password);
			
			String selectAllPageSql = "SELECT\r\n"
					+ "* FROM (\r\n"
					+ "    SELECT rownum rn, a. * \r\n"
					+ "    FROM (SELECT * FROM products ORDER BY prod_no) a)\r\n"
					+ "WHERE rn BETWEEN ? AND ?";
			
			pstmt = conn.prepareStatement(selectAllPageSql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String prodNo = rs.getString("prod_No");
				String ProdName = rs.getString("prod_name");
				int prodPrice = rs.getInt("prod_price");
				
				Products p = new Products();
				p.setProdNo(prodNo);
				p.setProdName(ProdName);
				p.setProdPrice(prodPrice);
				
				list.add(p);
				
				}
			return list;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FindException("JDBC드라이브 로드 실패!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}				
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}				
			}
		}
	}

	@Override
	public int totalCnt() throws FindException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "system";
			String password = "chlrmsgud123";
			conn = DriverManager.getConnection(url, user, password);
			
			String selectCountSQL = "select count(*) from products";
			pstmt = conn.prepareStatement(selectCountSQL);
			rs = pstmt.executeQuery();
			
			int totalCnt = rs.getInt(1);
			
			return totalCnt;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FindException("JDBC 드라이버 로드 실패"); //throw : 예외강제발생
			//강제 예외 발생을 try-catch로 또 잡는 것이 아니라, 메서드를 호출할 쪽으로 떠넘기기
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage()); //예외의 상세 메세지 설정 
		} finally {	//어떤 이유든 이 메서드를 빠져나갈때는 finally가 수행이 된다. 
			//6. DB연결닫기
			//모든 에러내용을 확인할 필요는 없음.		
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}				
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}				
			}
		}
	}

	@Override
	public Products selectByProdNo(String prodNo) throws FindException {
		// TODO Auto-generated method stub
		Connection conn = null;

		//3. SQL 구문 송신
		//preparedStatement : 미리만들어진 절. 오라클은 sql구문이 미리있어야한다
		PreparedStatement pstmt = null;
		
		//4. 송신 결과 수신
		ResultSet rs = null;
		try {
			//1. JDBC 드라이버 로드(ojdbc.jar)
			//압축파일 내부에 들어있는 main 역할을 할 class를 JVM의 상단으로 올려주는 것
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; //@localhost, 1521:db , 포트번호 xe:서비스아이디 
			String user = "system";
			String password = "chlrmsgud123";
			conn = DriverManager.getConnection(url, user, password);
			//Product p = new Product(); 이곳에 선언할 경우 p에 list 가 while로 반복하는 마지막 값만 담김 
			String selectByProdNoSQL = "select * from products where prod_no=?"; 
			//#{startRow}, #{endRow} 마이바티스에서 
			pstmt = conn.prepareStatement(selectByProdNoSQL);
			pstmt.setString(1,prodNo);
			rs = pstmt.executeQuery();// session.selectList(); << 마이바티스에서

			//5. 수신 내용 활용
			//상품번호에해당하는 정보가 있거나 없거나 임으로 한개 if
			if(rs.next()) { 
			 	String prodName = rs.getString("PROD_NAME");
			 	int prodPrice = rs.getInt("prod_price");	
			 	String prodDetail = rs.getString("PROD_DETAIL");
			 	Date prodMfDt = rs.getDate("PROD_MF_DT");
			 	Products p = new Products();
			 	p.setProdNo(prodNo);
			 	p.setProdName(prodName);
			 	p.setProdPrice(prodPrice);
			 	p.setProdDetail(prodDetail);
			 	p.setProdMfDt(prodMfDt);
			 	
			 	return p;  //리스트에 p객체의값 담기
			}else {
				throw new FindException(prodNo +"상품이없습니다");
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); //실제예외내용 몇번째 줄 발생, 내용 등등
			throw new FindException("JDBC 드라이버 로드 실패"); //throw : 예외강제발생
			//강제 예외 발생을 try-catch로 또 잡는 것이 아니라, 메서드를 호출할 쪽으로 떠넘기기
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage()); //예외의 상세 메세지 설정 
		} finally {	//어떤 이유든 이 메서드를 빠져나갈때는 finally가 수행이 된다. 
			//6. DB연결닫기
			// 반드시 close를해야함. 하지않으면 데이터가 누수되어서 연결된 것 처럼보이지만 안될 수 있음
			//메서드를 빠져나가기 직전엔 무조건 finally를 거쳐감!
			//모든 에러내용을 확인할 필요는 없음.		
			//닫는순서 : 열기한 순서의 반대순서
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}				
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}				
			}//close여러개를 합치면 안되는 이유?
			//어떤부분은닫히고 어떤 다른부분은 안닫힐 수 있기 때문에 각각 따로 해주는게 안전하다
		}
	}

}
