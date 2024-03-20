package com.urielsoft.cids.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-25
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

	/**
	 * 비동기 (Async) 쓰레드 관리를 위한 쓰레드풀
	 *
	 * @return
	 */
	@Bean
	public Executor defaultThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

		threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
		threadPoolTaskExecutor.setThreadNamePrefix("DEFAULT_THREAD_POOL-");
		threadPoolTaskExecutor.setQueueCapacity(500);
		threadPoolTaskExecutor.initialize();

		return threadPoolTaskExecutor;
	}
}
