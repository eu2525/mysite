package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.formLogin((formLogin) -> {
        		formLogin
        			.loginPage("/user/login")
        			.loginProcessingUrl("/user/auth")
        			.usernameParameter("email")
        			.passwordParameter("password")
        			.defaultSuccessUrl("/")
        			.failureUrl("/user/login?result=fail");
//        			.failureUrl( new AuthenticationFailureHandler() {
//        				@Override
//						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        					request.setAttribute("email", request.getParameter("email"));
//        					request.setAttribute("result", "fail");
//        					request
//        						.getRequestDispatcher("/user/login")
//        						.forward(request,response);
//						}	
//        			});
        	})
        	.authorizeHttpRequests((authorizeRequests) -> {
        		/* ACL */
        		authorizeRequests
		        		.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
		        		.authenticated()
		        		.anyRequest()
		        		.permitAll();
	        		
        	});
    	
    	return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncode) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncode);
    	
    	return new ProviderManager(authenticationProvider);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4 /* 4 ~ 31 */);
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
    	return new UserDetailsServiceImpl(userRepository); 
    }
}
