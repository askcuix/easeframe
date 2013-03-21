package com.easeframe.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Collection util.
 * 
 * @author Chris
 * 
 */
public class CollectionUtil {

	/**
	 * Extract property value from object collection to list.
	 * 
	 * @param collection
	 *            objects
	 * @param propertyName
	 *            extract property name
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List extractToList(final Collection collection,
			final String propertyName) {
		List list = new ArrayList(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtil.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * Extract property value from object collection to string with specified
	 * separator.
	 * 
	 * @param collection
	 *            objects
	 * @param propertyName
	 *            extract property name
	 * @param separator
	 *            separator
	 */
	@SuppressWarnings("rawtypes")
	public static String extractToString(final Collection collection,
			final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * Convert collection to string by toString() with specified separator.
	 * 
	 * @param collection
	 * @param separator
	 * @return collection string with separator
	 */
	@SuppressWarnings("rawtypes")
	public static String convertToString(final Collection collection,
			final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * Check collection is empty.
	 * 
	 * @param collection
	 * @return return true if collection is empty.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Check collection is not empty.
	 * 
	 * @param collection
	 * @return return true if collection is not empty.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	/**
	 * Get first element of collection.
	 * 
	 * @param collection
	 * @return first element, return null if collection is empty.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * Get last element of collection.
	 * 
	 * @param collection
	 * @return last element, return null if collection is empty.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * Consolidate a list from 2 collections.
	 * 
	 * @param a
	 *            collection
	 * @param b
	 *            collection
	 * @return consolidated list
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * Subtract a collection from another collection to a list.
	 * 
	 * @param a
	 *            source collection
	 * @param b
	 *            subtract collection
	 * @return subtracted list
	 */
	public static <T> List<T> subtract(final Collection<T> a,
			final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * Consolidate a intersection list from 2 collection.
	 * 
	 * @param a
	 *            collection
	 * @param b
	 *            collection
	 * @return intersection list
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
