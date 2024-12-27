package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class BoardDao {
	// DB Connection
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.26:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
	
	// Insert
	// new book insert
	public int insertNewBoard(BoardVo vo, Long userId) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values(null, ?, ?,  0 , date_format(NOW(), '%Y-%m-%d %H:%i:%s'), ?, 1, 0, ?);");
		) {			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, (getMaxgNo() + 1));
			pstmt.setLong(4, userId);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;		
	}
	
	// reply book insert
	public int insertReplyBoard(String title, String content, int replygNo, int replyoNo, int replyDepth, Long userId) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values(null, ?, ?, 0 , date_format(NOW(), '%Y-%m-%d %H:%i:%s'), ?, ?, ?, ?);");
			) {			
			updateReplyBoard(replygNo, replyoNo, replyDepth);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, replygNo);
			pstmt.setInt(4, (replyoNo + 1));
			pstmt.setInt(5, (replyDepth + 1));
			pstmt.setLong(6, userId);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
	}
	
	// Delete
	// titleId 기준으로 delete 
	public int deleteByTitleId(int titleId) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
		) {
			pstmt.setInt(1, titleId);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
	}
	
	// Update
	// InsertReplyBoard시 기존 o_no update.
	public int updateReplyBoard(int replygNo, int replyoNo, int replyDepth) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ? ;");
		) { 
			pstmt.setInt(1, replygNo);
			pstmt.setInt(2, replyoNo + 1);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
	}
	
	// 글 수정 Update
	public int updateBoard(BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET title=?, contents=?,reg_date=date_format(NOW(), '%Y-%m-%d') WHERE id= ?;");
		) { 
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
	}
	
	// 조회시 조회수 Update
	public int updateBoardHit(BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET hit = hit + 1 WHERE id= ?;");
		) { 
			pstmt.setLong(1, vo.getId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 

		return count;	
	}
	
	
	
	// Read 
	// page에 맞는 글들을 read 하는 sql문
	public List<BoardVo> findByPage(String keyword, int pageIdx, int pageSize) {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from board where title LIKE ? or contents LIKE ? order by g_no desc, o_no asc limit ?,?;");	
		) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
            pstmt.setInt(3, (pageIdx - 1) * pageSize);
            pstmt.setInt(4, pageSize);
			
            ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_id = rs.getLong(9);
				
				UserVo userVo = new UserDao().findById(user_id);
				String userName = userVo.getName();

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(user_id);
				vo.setUserName(userName);
				
				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}

	// TitleID를 이용해서 게시글 정보 가져오는 코드
	public BoardVo findById(int titleId) {
		BoardVo vo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from board where id = ?;");	
		) {
			pstmt.setInt(1, titleId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_id = rs.getLong(9);
				
				vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(user_id);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 

		return vo;
	}

	// gNo의 Max값을 read하는 sql문
	public int getMaxgNo() {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select COALESCE(max(g_no) , 0) as maxgno from board;");	
			ResultSet rs = pstmt.executeQuery();
		) {
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}
	
	// Keyword에 맞는 글들의 수를 read하는 sql문
	public int getBoardCount(String keyword) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select count(id) from webdb.board WHERE title LIKE ? or contents LIKE ?;");	
		) {	
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}
	
}

