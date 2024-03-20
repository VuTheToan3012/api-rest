package com.urielsoft.cids.management.config;

import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration for Database
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-04
 */
@Configuration
@EnableTransactionManagement
@MapperScans(value = {
		@MapperScan(basePackages = {"com.urielsoft.cids.management"}, annotationClass = Db1Mapper.class, sqlSessionTemplateRef = "sqlSessionTemplateDb1"),
})
@RequiredArgsConstructor
public class DatabaseConfig {

	private final ApplicationContext applicationContext;

	/**
	 * [DB1] DB Config
	 *
	 * @return
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "db1")
	public HikariConfig hikariConfigDb1() {
		return new HikariConfig();
	}

	/**
	 * [DB1] DB DataSource
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Primary
	public DataSource dataSourceDb1() throws Exception {
		return new HikariDataSource(hikariConfigDb1());
	}

	/**
	 * [DB1] SQL Session Factory Bean
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactoryDb1() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSourceDb1());
		sqlSessionFactoryBean.setConfigLocation(this.applicationContext.getResource("classpath:mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(this.applicationContext.getResources("classpath:mappers/db1/**/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * [DB1] SQL Session Template
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Primary
	public SqlSessionTemplate sqlSessionTemplateDb1() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactoryDb1());
	}

	/**
	 * [DB1] Transaction Manager Bean
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Primary
	public DataSourceTransactionManager transactionManagerDb1() throws Exception {
		return new DataSourceTransactionManager(dataSourceDb1());
	}
}
