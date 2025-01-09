package mysite.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mysite.service.UserService;
import mysite.vo.UserVo;

@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// "{exist : false or true } 
	@ResponseBody
	@RequestMapping("/checkemail")
	public Object checkEmail(@RequestParam(value="email", required=true, defaultValue="" ) String email) {
		UserVo userVo = userService.getUser(email);
		System.out.println(userVo);
		return Map.of("exist", userVo != null);
	}
}
