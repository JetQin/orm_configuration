/** 
 * Project Name:orm.configuration 
 * File Name:OrmConfigurationSelector.java 
 * Package Name:com.usee.orm.annotation
 * Date:Sep 15, 201510:33:41 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.github.jetqin.orm.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.github.jetqin.orm.configuration.HibernateOrmConfiguration;
import com.github.jetqin.orm.constants.OrmType;

/** 
 * ClassName: OrmConfigurationSelector  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
public class OrmConfigurationSelector implements ImportSelector
{

	/* (non-Javadoc)
	 * @see org.springframework.context.annotation.ImportSelector#selectImports(org.springframework.core.type.AnnotationMetadata)
	 */
	public String[] selectImports(AnnotationMetadata importingClassMetadata)
	{
		OrmType type = (OrmType)importingClassMetadata.getAnnotationAttributes(EnableOrmConfiguration.class.getName()).get("type");
		if(OrmType.Hibernate.equals(type))
		{
			return new String[]{HibernateOrmConfiguration.class.getName()};
		}
		throw new IllegalArgumentException();
		
	}

}
