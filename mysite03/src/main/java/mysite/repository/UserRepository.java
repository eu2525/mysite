package mysite.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;

	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {		
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
	}


	public UserVo findById(Long id) {
		return sqlSession.selectOne("user.findById", id);
	}


	public int update(UserVo vo) {		
		return sqlSession.update("user.update", vo);
	}

	public UserVo findByEmail(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user.findByEmail", email);
	}
	
	
}
