package com.easeframe.core.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.easeframe.core.util.ReflectionUtil;

/**
 * Hibernate base DAO.
 * 
 * @author Chris
 * 
 * @param <T>
 *            entity
 * @param <ID>
 *            id
 */
public class HibernateDao<T, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * Get entity class type from subclass generic type definition.
	 * 
	 * eg. public class UserDao extends HibernateDao<User, Long>
	 */
	public HibernateDao() {
		this.entityClass = ReflectionUtil.getClassGenricType(getClass());
	}

	public HibernateDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Save or update entity.
	 */
	public void save(final T entity) {
		Validate.notNull(entity, "entity is required!");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/**
	 * Delete entity.
	 * 
	 * @param entity
	 */
	public void delete(final T entity) {
		Validate.notNull(entity, "entity is required!");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * Delete entity by id.
	 * 
	 * @param id
	 */
	public void delete(final ID id) {
		Validate.notNull(id, "id is required!");
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(),
				id);
	}

	/**
	 * Get entity by id.
	 * 
	 * @param id
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public T get(final ID id) {
		Validate.notNull(id, "id is required!");
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * Get entity list by id list.
	 * 
	 * @param ids
	 * @return entity list
	 */
	public List<T> get(final Collection<ID> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * Initialize the lazy property value.
	 * 
	 * eg. Hibernates.initLazyProperty(user.getGroups());
	 */
	public static void initLazyProperty(Object proxyedPropertyValue) {
		Hibernate.initialize(proxyedPropertyValue);
	}

	/**
	 * Get all entity.
	 * 
	 * @return entity list
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 * Get all entity with order.
	 * 
	 * @param orderByProperty
	 * @param isAsc
	 * @return entity list
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/**
	 * Find entity list by property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return entity
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Validate.notBlank(propertyName, "propertyName is required!");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * Find unique entity by property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Validate.notBlank(propertyName, "propertyName is required!");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * Find entity list by HQL
	 * 
	 * @param values
	 *            ordered parameters
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * Find entity list by HQL
	 * 
	 * @param values
	 *            named parameters
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * Find unique entity by HQL
	 * 
	 * @param values
	 *            ordered parameters
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * Find unique entity by HQL.
	 * 
	 * @param values
	 *            named parameters
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * Batch update/delete by HQL.
	 * 
	 * @param values
	 *            ordered parameters
	 * @return affected count
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * Batch update/delete by HQL.
	 * 
	 * @param values
	 *            named parameters
	 * @return affected count
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * Create Query by HQL and parameters.
	 * 
	 * @param values
	 *            ordered parameters
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Validate.notBlank(queryString, "queryString is required!");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * Create Query by HQL and parameters.
	 * 
	 * @param values
	 *            named parameters
	 */
	public Query createQuery(final String queryString,
			final Map<String, ?> values) {
		Validate.notBlank(queryString, "queryString is required!");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * Find entity list by Criteria.
	 * 
	 * @param criterions
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * Find unique entity by Criteria.
	 * 
	 * @param criterions
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * Create Criteria by Criterion.
	 * 
	 * @param criterions
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * Flush current session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * Add distinct transformer to Query.
	 * 
	 * @param query
	 * @return query
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * Add distinct transformer to Criteria.
	 * 
	 * @param criteria
	 * @return criteria
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * Get id name from entity.
	 * 
	 * @return id name
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

}
