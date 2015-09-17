/** 
 * Project Name:orm.configuration 
 * File Name:OrmConfiguration.java 
 * Package Name:com.usee.orm.configuration
 * Date:Sep 15, 201510:28:11 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.github.jetqin.orm.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.Data;

/**
 * ClassName: OrmConfiguration
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */

@Configuration
public @Data abstract class OrmConfiguration
{

	private static final String CONNECTION_DRIVER_CLASS = "connection.driver_class";
	private static final String CONNECTION_URL = "connection.url";
	private static final String CONNECTION_USERNAME = "connection.username";
	private static final String CONNECTION_PASSWORD = "connection.password";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String HIBERNATE_DBCP_INITIAL_SIZE = "hibernate.dbcp.initialSize";
	private static final String HIBERNATE_DBCP_MAX_ACTIVE = "hibernate.dbcp.maxActive";
	private static final String HIBERNATE_DBCP_MAX_IDLE = "hibernate.dbcp.maxIdle";
	private static final String HIBERNATE_DBCP_MIN_IDLE = "hibernate.dbcp.minIdle";

	private static final String HIBERNATE_CACHE_REGION_FACTORY_CLASS = "hibernate.cache.region.factory_class";
	private static final String HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
	private static final String HIBERNATE_CACHE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";

	// for Hibernate 4
	private static final String ORG_HIBERNATE_CACHE_EHCACHE_EH_CACHE_REGION_FACTORY = "org.hibernate.cache.ehcache.EhCacheRegionFactory";

	private boolean enableConnectionPool = true;
	private boolean enableCache;
	
	//package to scan
	protected String[] packagesToScan;
	protected boolean showSql;
	protected boolean generateSql;

	@Autowired
	Environment env;

	public abstract PlatformTransactionManager transactionManager();
	
	@Bean
	public DataSource dataSource()
	{
		BasicDataSource datasouce = new BasicDataSource();
		datasouce.setUrl(env.getProperty(CONNECTION_URL));
		datasouce.setDriverClassName(CONNECTION_DRIVER_CLASS);
		datasouce.setUsername(env.getProperty(CONNECTION_USERNAME));
		datasouce.setPassword(env.getProperty(CONNECTION_PASSWORD));
		return datasouce;

	}
	
	
	@Bean
	public EhCacheCacheManager ehCacheCacheManager()
	{
		return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean()
	{
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}
	

	protected Properties getHibernateProperties()
	{
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
		
		if (enableCache)
		{
			properties.put(HIBERNATE_CACHE_REGION_FACTORY_CLASS, ORG_HIBERNATE_CACHE_EHCACHE_EH_CACHE_REGION_FACTORY);
			properties.put(HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE, true);
			properties.put(HIBERNATE_CACHE_USE_QUERY_CACHE, true);
		}
		
		if (enableConnectionPool)
		{
			properties.put(HIBERNATE_DBCP_INITIAL_SIZE, env.getProperty(HIBERNATE_DBCP_INITIAL_SIZE));
			properties.put(HIBERNATE_DBCP_MAX_ACTIVE, env.getProperty(HIBERNATE_DBCP_MAX_ACTIVE));
			properties.put(HIBERNATE_DBCP_MAX_IDLE, env.getProperty(HIBERNATE_DBCP_MAX_IDLE));
			properties.put(HIBERNATE_DBCP_MIN_IDLE, env.getProperty(HIBERNATE_DBCP_MIN_IDLE));
		}
		return properties;
	}
}
