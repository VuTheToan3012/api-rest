package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-06
 */
@Controller
public class MainPageController {

	/**
	 * Main Page
	 *
	 * @return
	 */
	@RequestMapping("/main")
	public String main(){
		return "redirect:cids-monitor";
	}
}
