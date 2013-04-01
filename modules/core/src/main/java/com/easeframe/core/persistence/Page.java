package com.easeframe.core.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Pagination info.
 * 
 * @author Chris
 * 
 * @param <T>
 *            record type
 */
public class Page<T> extends PageRequest implements Iterable<T> {

	protected List<T> result = null;
	protected long totalItems = -1;

	public Page() {
	}

	public Page(PageRequest request) {
		this.pageNo = request.pageNo;
		this.pageSize = request.pageSize;
		this.countTotal = request.countTotal;
		this.orderBy = request.orderBy;
		this.orderDir = request.orderDir;
	}

	/**
	 * get record list of page.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * set record list of page.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * Get total record count, default is -1.
	 */
	public long getTotalItems() {
		return totalItems;
	}

	/**
	 * Set total record count.
	 */
	public void setTotalItems(final long totalItems) {
		this.totalItems = totalItems;
	}

	@Override
	public Iterator<T> iterator() {
		return result.iterator();
	}

	/**
	 * Calculate total pages.
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) totalItems / (double) getPageSize());

	}

	/**
	 * Whether has next page.
	 */
	public boolean hasNextPage() {
		return (getPageNo() + 1 <= getTotalPages());
	}

	/**
	 * Whether is last page.
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/**
	 * Get next page number, the page number start from 1.
	 * 
	 * Return current page number if last page.
	 */
	public int getNextPage() {
		if (hasNextPage()) {
			return getPageNo() + 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * Whether has previous page.
	 */
	public boolean hasPrePage() {
		return (getPageNo() > 1);
	}

	/**
	 * Whether is first page.
	 */
	public boolean isFirstPage() {
		return !hasPrePage();
	}

	/**
	 * Get previous page number, page number start from 1.
	 * 
	 * Return current page number if first page.
	 */
	public int getPrePage() {
		if (hasPrePage()) {
			return getPageNo() - 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * Calculate page list, e.g. "first,23,24,25,26,27,last"
	 * 
	 * @param count
	 * @return pageNo
	 */
	public List<Integer> getSlider(int count) {
		int halfSize = count / 2;
		int totalPage = getTotalPages();

		int startPageNo = Math.max(getPageNo() - halfSize, 1);
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);

		if (endPageNo - startPageNo < count) {
			startPageNo = Math.max(endPageNo - count, 1);
		}

		List<Integer> result = new ArrayList<Integer>();
		for (int i = startPageNo; i <= endPageNo; i++) {
			result.add(i);
		}
		return result;
	}
}
