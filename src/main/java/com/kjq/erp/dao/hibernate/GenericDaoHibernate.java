package com.kjq.erp.dao.hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.kjq.erp.dao.GenericDao;
import com.kjq.erp.util.GenericUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 	GenericDaoHibernate
 */
public abstract class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    
    public GenericDaoHibernate(){
    	this.persistentClass = GenericUtils.getGenericClass(getClass());
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
    
    @Autowired
    @Required
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return getHibernateTemplate().loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return (T) getHibernateTemplate().merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
    	getHibernateTemplate().delete(this.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        
        int index = 0;
        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
    }
    
    //extend
    
    abstract public DetachedCriteria getCriteria(Map filterMap);
    
    public void orderCriteria(DetachedCriteria criteria, Map orderMap) {
		if(orderMap!=null && !orderMap.isEmpty()){
			Iterator iter = orderMap.keySet().iterator();
			while(iter.hasNext()){
				String fieldName = (String)iter.next();
				String orderType = (String)orderMap.get(fieldName);
				//支持一层嵌套
				if ("asc".equalsIgnoreCase(orderType)) {
                    criteria.addOrder(Order.asc(fieldName));
                } else {
                    criteria.addOrder(Order.desc(fieldName));
                }
			}
		}
	}
    
    protected boolean isNotEmpty(Object obj){
        if(obj == null)
            return false;
        return !"".equals(obj);
    }
    
    
    public Long getCount(Map filterMap){
    	Session session = getSession();
    	DetachedCriteria detachedCriteria = getCriteria(filterMap);
    	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    	Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
    	releaseSession(session);
    	return count;
    }
    
    public List findByAll(){
    	return findByAll(new HashMap(),new HashMap());
    }
    
    public List findByAll(Map filterMap,Map orderMap){
    	DetachedCriteria criteria = getCriteria(filterMap);
    	orderCriteria(criteria, orderMap);
    	return getHibernateTemplate().findByCriteria(criteria);
    }
    
    public List findBy(Map filterMap,Map orderMap,int firstResult, int maxResults){
    	DetachedCriteria criteria = getCriteria(filterMap);
    	orderCriteria(criteria, orderMap);
    	return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }
    
    /**
     * 
     * 根据HQL及参数查询
     * find(hql)
     * find(hql,new Object[]{})
     * @param hql
     * @param values
     * @return
     */
    public List find(String hql, Object... values) {
        if (values.length == 0)
            return getHibernateTemplate().find(hql);
        else
            return getHibernateTemplate().find(hql, values);
    }
    
    
    /**
     * 根据属性值查找记录
     * @param propName
     * @param propValue
     * @return
     */
    public List findByPropertyValue(String propName,String propValue){
    	DetachedCriteria criteria = getCriteria(new HashMap());
    	criteria.add(Restrictions.eq(propName, propValue));
    	return getHibernateTemplate().findByCriteria(criteria);
    }
    /**
     * 根据属性值查找记录数
     * @param propName
     * @param propValue
     * @return
     */
    public Long getCountByPropertyValue(String propName,String propValue){
    	Session session = getSession();
    	DetachedCriteria detachedCriteria = getCriteria(new HashMap());
    	detachedCriteria.add(Restrictions.eq(propName, propValue));
    	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    	Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
    	releaseSession(session);
    	return count;
    }
    
    /**
     * 判断对象某列的值在数据库中不存在重复
     *
     * @param names 在POJO里相对应的属性名,列组合时以逗号分割
     *              如"name,loginid,password"
     */
    public boolean isConflictWithOther(Object entity, String names) {
    	Session session = getSession();
    	DetachedCriteria detachedCriteria = getCriteria(new HashMap());
    	Criteria criteria = detachedCriteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
        String[] nameList = names.split(",");
        try {
            for (String name : nameList) {
                criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
            }

            String keyName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
            if (keyName != null) {
                Object id = PropertyUtils.getProperty(entity, keyName);
                //如果是update,排除自身
                if (id != null)
                    criteria.add(Restrictions.not(Restrictions.eq(keyName, id)));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        Long count = (Long)criteria.uniqueResult();
        releaseSession(session);
        return  count > 0;
    }
    
    /**
     * 根据传入的HQL、分页游标、页数、查询条件参数(可选)获取列表
     * 
     * @param hql
     * @param pageNo
     * @param pageSize
     * @param parameter
     * @return
     */
    public List findByHql(String hql, int pageNo, int pageSize, Object... parameter){
    	Session session = getSession();
    	Query query = session.createQuery(hql);
    	if (parameter != null){
	        for (int i = 0; i < parameter.length; i++) {
	            query.setParameter(i, parameter[i]);
	        }
    	}
    	
    	query.setFirstResult((pageNo-1)*pageSize);
        query.setMaxResults(pageSize); 
    	
        List result = query.list(); 
        releaseSession(session);
    	return result;
    }
}
