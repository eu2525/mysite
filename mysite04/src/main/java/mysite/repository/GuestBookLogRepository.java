package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.SqlSession;

@Repository
public class GuestBookLogRepository {

//	private SqlSession sqlSession;
//	
//	public GuestBookLogRepository(SqlSession SqlSession) {
//		this.sqlSession = SqlSession;
//	}
//	
//	public int insert() {
//		return sqlSession.update("insert into guestbook_log values(current_date() , 1)");
//	}
//	
//	public int update() {
//		return sqlSession.update("update guestbook_log set count = count + 1 where date = current_date()");
//	}
//	
//	public int update(String regDate) {
//		return sqlSession.update("update guestbook_log set count = count - 1 where date_format(date, '%Y-%m-%d') = ? ", regDate);
//	}
	
}
