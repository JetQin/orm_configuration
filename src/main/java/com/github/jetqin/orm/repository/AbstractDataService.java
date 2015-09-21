/***********************************************************************
 *
 * 
 *
 * @file        AbstractDataService.java
 *
 * @copyright   Copyright: 2014-2016 Usee Co. Ltd.
 * @creator     JetQin <br/>
 * @create-time 2014年9月8日
 *
 *
 ***********************************************************************/
package com.github.jetqin.orm.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author jet
 *
 */
@Component
@Transactional
public abstract class AbstractDataService<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport
{
	
    private SessionFactory sessionFactory;
	
	private Class<T> clazz;

	public void setClazz(final Class<T> clazzToSet)
	{
		clazz = clazzToSet;
	}

	@Transactional
	public T findOne(PK pk)
	{
		return (T) getCurrentSession().get(clazz, pk);
	}
	
	@Transactional
	protected List<T> findBySql(String sql)
	{
		Query query = getCurrentSession().createQuery(sql);
		return query.list()  ;
		
	}

	@Transactional
	protected T findEntityBySql(String sql)
	{
		Query query = getCurrentSession().createQuery(sql);
		return (T) query.uniqueResult()  ;
		
	}
	

	@Transactional
	protected List<T> findByNamedQuery(Query query)
	{
		return null == query.list() ? null : query.list() ;
		
	}
	

	public List<T> findByParameter(Map parameters)
	{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.allEq(parameters));
		List<T> results = (List<T>) getHibernateTemplate().findByCriteria(criteria);
		return results;
		
	}
	
	public T findEntityByParameter(Map parameters)
	{

		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.allEq(parameters));
		List<T> results = (List<T>) getHibernateTemplate().findByCriteria(criteria);
		if(null != results && results.size() > 0)
		{
			return results.get(0);
		}
		return null;

	}

	@Transactional
	public List<T> findAll()
	{
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<T> results = criteria.list();
		return results;
	}

	public Long getTotalCount()
	{
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Long totalSize = Long.parseLong(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		return totalSize;
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public void save(final T entity)
	{
		getCurrentSession().save(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	protected T saveAndGet(final T entity)
	{
		getCurrentSession().save(entity);
		getCurrentSession().flush();
		return entity;
	}

	@Transactional
	public T update(final T entity)
	{
		return (T) getCurrentSession().merge(entity);
	}
	
	@Transactional
	public void delete(final T entity)
	{
		getCurrentSession().delete(entity);
	}

	public void deleteById(PK pk)
	{
		final T entity = findOne(pk);
		delete(entity);
	}

	protected final Session getCurrentSession()
	{
		return currentSession();
	}
	
	@Resource(name = "sessionFactory")
	public void setLocalSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
		super.setSessionFactory(sessionFactory);
	}

}
