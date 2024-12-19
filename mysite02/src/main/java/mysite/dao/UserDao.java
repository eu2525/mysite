package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysite.vo.UserVo;

public class UserDao {
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.10:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
	

	public int insert(UserVo vo) {
		int count = 0;		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?, date_format(NOW(), '%Y-%m-%d'));");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo vo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, gender from user where email=? and password=?;");
		) {
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				
				vo = new UserVo();
				vo.setId(id);
				vo.setName(name);
				vo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return vo;
	}


	public UserVo findById(Long id) {
		UserVo vo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select name, gender, email from user where id=?;");
		) {
			
			pstmt.setLong(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString(1);
				String gender = rs.getString(2);
				String email = rs.getString(3);
			
				vo = new UserVo();
				vo.setId(id);
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return vo;
	}


	public int update(UserVo vo) {
		int count = 0;		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("UPDATE user SET name = ?, gender=?, join_date=date_format(NOW(), '%Y-%m-%d') WHERE email=?;");
			PreparedStatement pstmt2 = conn.prepareStatement("UPDATE user SET name = ?, password = ?, gender=?, join_date=date_format(NOW(), '%Y-%m-%d') WHERE email=?;");
		) {
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setString(3, vo.getEmail());
				
				count = pstmt1.executeUpdate();				
			}
			else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getPassword());
				pstmt2.setString(3, vo.getGender());
				pstmt2.setString(4, vo.getEmail());

				count = pstmt2.executeUpdate();				
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
	}
	
	
}
