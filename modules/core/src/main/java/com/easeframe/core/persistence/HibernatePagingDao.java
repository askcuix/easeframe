package com.easeframe.core.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;

import com.easeframe.core.persistence.PageRequest.Sort;
import com.easeframe.core.util.ReflectionUtil;

/**
 * Hibernate paging base DAO.
 * 
 * @author Chris
 * 
 * @param <T>
 *            entity
 * @param <ID>
 *            id
 */
public class HibernatePagingDao<T, ID extends Serializable> extends
		HibernateDao<T, ID> {

	/**
	 * Get all pagination info.
	 */
	public Page<T> getAll(final PageRequest pageRequest) {
		return findPage(pageRequest);
	}

	/**
	 * Pagination query by HQL.
	 * 
	 * @param pageRequest
	 * @param hql
	 * @param values
	 *            ordered parameters
	 * 
	 * @return pagination result
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final PageRequest pageRequest, String hql,
			final Object... values) {
		Validate.notNull(pageRequest, "pageRequest is required!");

		Page<T> page = new Page<T>(pageRequest);

		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalItems(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHql(hql, pageRequest);
		}
		Query q = createQuery(hql, values);

		setPageParameterToQuery(q, pageRequest);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * Pagination query by HQL.
	 * 
	 * @param page
	 * @param hql
	 * @param values
	 *            named parameters
	 * 
	 * @return pagination result
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<T> findPage(final PageRequest pageRequest, String hql,
			final Map<String, ?> values) {
		Validate.notNull(pageRequest, "pageRequest is required!");

		Page<T> page = new Page<T>(pageRequest);

		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalItems(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHql(hql, pageRequest);
		}

		Query q = createQuery(hql, values);
		setPageParameterToQuery(q, pageRequest);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * Pagination query by Criteria.
	 * 
	 * @param page
	 * @param criterions
	 * 
	 * @return pagination result
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final PageRequest pageRequest,
			final Criterion... criterions) {
		Validate.notNull(pageRequest, "pageRequest is required!");

		Page<T> page = new Page<T>(pageRequest);

		Criteria c = createCriteria(criterions);

		if (pageRequest.isCountTotal()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalItems(totalCount);
		}

		setPageRequestToCriteria(c, pageRequest);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * Add order by to HQL according to pageRequest.
	 */
	protected String setOrderParameterToHql(final String hql,
			final PageRequest pageRequest) {
		StringBuilder builder = new StringBuilder(hql);
		builder.append(" order by");

		for (Sort orderBy : pageRequest.getSort()) {
			builder.append(String.format(" %s %s,", orderBy.getProperty(),
					orderBy.getDir()));
		}

		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

	/**
	 * Add pagination info to Query.
	 */
	protected Query setPageParameterToQuery(final Query q,
			final PageRequest pageRequest) {
		q.setFirstResult(pageRequest.getOffset());
		q.setMaxResults(pageRequest.getPageSize());
		return q;
	}

	/**
	 * Add pagination info to Criteria.
	 */
	protected Criteria setPageRequestToCriteria(final Criteria c,
			final PageRequest pageRequest) {
		Validate.isTrue(pageRequest.getPageSize() > 0,
				"Page Size must larger than zero");

		c.setFirstResult(pageRequest.getOffset());
		c.setMaxResults(pageRequest.getPageSize());

		if (pageRequest.isOrderBySetted()) {
			for (Sort sort : pageRequest.getSort()) {
				if (Sort.ASC.equals(sort.getDir())) {
					c.addOrder(Order.asc(sort.getProperty()));
				} else {
					c.addOrder(Order.desc(sort.getProperty()));
				}
			}
		}
		return c;
	}

	/**
	 * Count result.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * Count result.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String countHql = "select count (*) "
				+ removeSelect(removeOrders(orgHql));
		return countHql;
	}

	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Count result from Criteria.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// Clear Projection, ResultTransformer and OrderBy first
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtil.getFieldValue(impl,
					"orderEntries");
			ReflectionUtil.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("Unexpected exception: {}", e.getMessage());
		}

		Long totalCountObject = (Long) c.setProjection(Projections.rowCount())
				.uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// set back Projection, ResultTransformer and OrderBy
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtil.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("Unexpected exception: {}", e.getMessage());
		}

		return totalCount;
	}

}
