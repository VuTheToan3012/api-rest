package com.urielsoft.cids.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-04
 */
@SpringBootApplication
public class CidsManagementMain {

	/**
	 * for CIDS Management Start with Spring Boot embedded tomcat
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		var app = new SpringApplication(CidsManagementMain.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}
}
