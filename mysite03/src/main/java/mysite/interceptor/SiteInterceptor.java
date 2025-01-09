package mysite.interceptor;

import org.checkerframework.checker.units.qual.h;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {
	private final LocaleResolver localeResolver;
	// SiteService를 아직 구현 안해서 주석 처리
	// private final SiteService siteServce;
	
	public SiteInterceptor(LocaleResolver localeResolver) { //, SiteService siteService) {
		this.localeResolver = localeResolver;
//		this.SiteService = siteService
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
//		// siteService
//		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
//		// 맨 처음에 null일거니깐 그 때는 객체를 등록함
//		if(siteVo == null) {
//			siteVo = siteService.getSite();
//			request.getServletContext().setAttribute("siteVo", siteVo);
//		}
//		
		
		return true;
	}

}
