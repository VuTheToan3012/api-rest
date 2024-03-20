package com.urielsoft.cids.management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration for Security
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-04
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	private final AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

	private final AuthenticationFailureHandler defaultAuthenticationFailureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.authorizeRequests()	
				.antMatchers("/login", "/static/**", "/css/**", "/images/**", "/fonts/**", "/js/**", "/vendors/**", "/api/advertising-management/list", "/api/locale/all-locale-message").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				.and()
					.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/loginProc")
					.defaultSuccessUrl("/main")
					.successHandler(this.defaultAuthenticationSuccessHandler)
					.failureHandler(this.defaultAuthenticationFailureHandler)
					.usernameParameter("username")
					.passwordParameter("password")
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc"))
					.logoutSuccessUrl("/index")
					.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.and()
				.build();
	}
}
