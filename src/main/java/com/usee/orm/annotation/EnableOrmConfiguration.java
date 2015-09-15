/** 
 * Project Name:orm.configuration 
 * File Name:EnableOrmConfiguration.java 
 * Package Name:com.usee.orm.configuration
 * Date:Sep 15, 201510:28:42 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.usee.orm.constants.OrmType;

/** 
 * ClassName: EnableOrmConfiguration  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(OrmConfigurationSelector.class)
@Documented
public @interface EnableOrmConfiguration
{
	OrmType type() default OrmType.Hibernate;
	
	String[] packageToScan() default {};
	
	boolean showSql() default false;
	
}
