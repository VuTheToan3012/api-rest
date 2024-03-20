package com.urielsoft.cids.management.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Handler process after login success
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-06
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(authentication != null){
			Locale userLocale=null;
			try{

				userLocale= LocaleUtils.toLocale(request.getParameter("lang"));
				if (userLocale==null){

					userLocale=Locale.ENGLISH;
				}


			}catch (Exception e){
				log.error(ExceptionUtils.getStackTrace(e));
				userLocale=Locale.ENGLISH;
			}

			this.localeResolver.setLocale(request,response,userLocale);
		}
			response.sendRedirect("/main");
	}
}
