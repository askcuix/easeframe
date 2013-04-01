package com.easeframe.core.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Pagination request.
 * 
 * @author Chris
 * 
 */
public class PageRequest {

	protected int pageNo = 1;
	protected int pageSize = 10;

	protected String orderBy = null;
	protected String orderDir = null;

	protected boolean countTotal = true;

	public PageRequest() {
	}

	public PageRequest(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * Get current page number, page number start from 1, default is 1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * Set current page number, page number start from 1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * Get record count per page, default is 10.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Set record count per page.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * Get order by field, multiple field separate by ','.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Set order by field, multiple field separate by ','.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Get order by direction.
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * Set order by direction.
	 * 
	 * @param orderDir
	 *            desc or asc, multiple field separate by ','
	 */
	public void setOrderDir(final String orderDir) {
		String lowcaseOrderDir = StringUtils.lowerCase(orderDir);

		// validation
		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
		for (String orderDirStr : orderDirs) {
			if (!StringUtils.equals(Sort.DESC, orderDirStr)
					&& !StringUtils.equals(Sort.ASC, orderDirStr)) {
				throw new IllegalArgumentException("Order direction "
						+ orderDirStr + " is invalid!");
			}
		}

		this.orderDir = lowcaseOrderDir;
	}

	/**
	 * Get order info.
	 */
	public List<Sort> getSort() {
		String[] orderBys = StringUtils.split(orderBy, ',');
		String[] orderDirs = StringUtils.split(orderDir, ',');
		Validate.isTrue(orderBys.length == orderDirs.length,
				"The number of order by fields not equal to order directions!");

		List<Sort> orders = new ArrayList<Sort>();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}

		return orders;
	}

	/**
	 * Check order info is it set.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils
				.isNotBlank(orderDir));
	}

	/**
	 * Get is it need count total.
	 */
	public boolean isCountTotal() {
		return countTotal;
	}

	/**
	 * Set is it need count total.
	 */
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	/**
	 * Calculate first record of current page in total records, sequence from 0.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}

	public static class Sort {
		public static final String ASC = "asc";
		public static final String DESC = "desc";

		private final String property;
		private final String dir;

		public Sort(String property, String dir) {
			this.property = property;
			this.dir = dir;
		}

		public String getProperty() {
			return property;
		}

		public String getDir() {
			return dir;
		}
	}
}
