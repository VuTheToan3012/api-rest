package com.urielsoft.cids.management.common.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Security Controller
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-04
 */
@Controller
public class SecurityController {

	/**
	 * Login Page
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/index", "/login"})
	public String login() throws Exception {
		return "login";
	}
}
