package mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mysite.repository.GuestBookRepository;
import mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	private GuestBookRepository guestBookRepository;
	
	public GuestBookService(GuestBookRepository guestBookRepository) {
		this.guestBookRepository = guestBookRepository;
	}
	
	public List<GuestBookVo> getContentsList() {
		return guestBookRepository.findAll();
	}
	
	public void deleteContents(Long id, String password) {
		guestBookRepository.deleteByIdAndPassword(id, password);
	}
	
	public void addContents(GuestBookVo vo) {
		guestBookRepository.insert(vo);
	}
	
}
