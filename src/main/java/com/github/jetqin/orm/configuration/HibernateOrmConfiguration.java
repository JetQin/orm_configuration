/** 
 * Project Name:orm.configuration 
 * File Name:HibernateOrmConfiguration.java 
 * Package Name:com.usee.orm.configuration
 * Date:Sep 15, 201510:32:37 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.github.jetqin.orm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/** 
 * ClassName: HibernateOrmConfiguration  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
public class HibernateOrmConfiguration extends OrmConfiguration 
{
	@Bean
	public PlatformTransactionManager transactionManager()
	{
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		txManager.setDataSource(dataSource());
		return txManager;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(packagesToScan);
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}
}
