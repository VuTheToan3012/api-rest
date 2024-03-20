package com.urielsoft.cids.management.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Thymeleaf
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-04
 */
@Configuration
public class ThymeleafConfig {

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
