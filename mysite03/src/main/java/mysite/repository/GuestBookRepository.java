package mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private DataSource dataSource;

	private SqlSession sqlSession;
	
	public GuestBookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<GuestBookVo> findAll() {		
		return sqlSession.selectList("guestbook.findAll");
	}

	public int insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}

	public int deleteByIdAndPassword(Long id, String password) {
		return sqlSession.delete("guestbook.deleteByIdAndPassword", Map.of("id", id, "password", password));		
	}	
	
}
