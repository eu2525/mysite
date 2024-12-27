package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void join(UserVo userVo) {
		userRepository.insert(userVo);
	}
	
	public UserVo getUser(Long no) {
		return userRepository.findById(no);
	}
	
	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public int update(UserVo userVo) {
		return userRepository.update(userVo);
	}
	
}
