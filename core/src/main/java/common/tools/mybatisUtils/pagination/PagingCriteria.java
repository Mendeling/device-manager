/*
 * Copyright © 2012-2013 mumu@yfyang. All Rights Reserved.
 */

package common.tools.mybatisUtils.pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @Class Name PagingCriteria
 * @Author Administrator
 * @Create In 2014710
 */
public final class PagingCriteria {
	/** The constant DEFAULT_CRITERIA. */
	private static final PagingCriteria DEFAULT_CRITERIA = new PagingCriteria(0, PagingCriteria.DEFAULT_SIZE, PagingCriteria.DEFAULT_SIZE);
	/** default page size. */
	private static final int DEFAULT_SIZE = 10;
	/** start display */
	private final int displayStart;
	/** disaplaySize */
	private final int displaySize;
	/** sort fields */
	private final List<SortField> sortFields;
	/** search field information */
	private final List<SearchField> searchFields;
	/** pageNumber */
	private final int pageNumber;

	/**
	 * Instantiates a new Paging criteria.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param pageNumber
	 *            the page number
	 * @param sortFields
	 *            the sort fields
	 * @param searchFields
	 *            the search information
	 */
	private PagingCriteria(int displayStart, int displaySize, int pageNumber, List<SortField> sortFields, List<SearchField> searchFields) {
		this.displayStart = displayStart;
		this.displaySize = displaySize;
		this.pageNumber = pageNumber;
		this.sortFields = sortFields;
		this.searchFields = searchFields;
	}

	/**
	 * Instantiates a new Paging criteria and not sort\search.
	 *
	 * @param displaySize
	 *            the display size
	 * @param displayStart
	 *            the display start
	 * @param pageNumber
	 *            the page number
	 */
	private PagingCriteria(int displaySize, int displayStart, int pageNumber) {
		this.displaySize = displaySize;
		this.displayStart = displayStart;
		this.pageNumber = pageNumber;
		this.searchFields = null; // new ArrayList<SearchField>(0);
									// //Lists.newArrayListWithCapacity(0);
		this.sortFields = null; // new ArrayList<SortField>(0);
								// //Lists.newArrayListWithCapacity(0);
	}

	/**
	 * Instantiates a new Paging criteria and no search.
	 *
	 * @param displaySize
	 *            the display size
	 * @param displayStart
	 *            the display start
	 * @param pageNumber
	 *            the page number
	 * @param sortFields
	 *            the sort fields
	 */
	private PagingCriteria(int displaySize, int displayStart, int pageNumber, List<SortField> sortFields) {
		this.sortFields = sortFields;
		this.displaySize = displaySize;
		this.displayStart = displayStart;
		this.pageNumber = pageNumber;
		this.searchFields = new ArrayList<SearchField>(0); // Lists.newArrayListWithCapacity(0);
	}

	/**
	 * Instantiates a new Paging criteria and no sort.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param searchFields
	 *            the search fields
	 * @param pageNumber
	 *            the page number
	 */
	private PagingCriteria(int displayStart, int displaySize, List<SearchField> searchFields, int pageNumber) {
		this.displayStart = displayStart;
		this.displaySize = displaySize;
		this.searchFields = searchFields;
		this.pageNumber = pageNumber;
		this.sortFields = new ArrayList<SortField>(0); // Lists.newArrayListWithCapacity(0);
	}

	/**
	 * Create criteria with all paramter.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param pageNumber
	 *            the page number
	 * @param sortFields
	 *            the sort fields
	 * @param searchFields
	 *            the search fields
	 * @return the paging criteria
	 */
	public static PagingCriteria createCriteriaWithAllParamter(int displayStart, int displaySize, int pageNumber, List<SortField> sortFields,
			List<SearchField> searchFields) {
		return new PagingCriteria(displayStart, displaySize, pageNumber, sortFields, searchFields);
	}

	/**
	 * Create criteria with sort.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param pageNumber
	 *            the page number
	 * @param sortFields
	 *            the sort fields
	 * @return the paging criteria
	 */
	public static PagingCriteria createCriteriaWithSort(int displaySize, int displayStart, int pageNumber, List<SortField> sortFields) {
		return new PagingCriteria(displaySize, displayStart, pageNumber,sortFields);
	}

	/**
	 * Create criteria with search.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param pageNumber
	 *            the page number
	 * @param searchFields
	 *            the search fields
	 * @return the paging criteria
	 */
	public static PagingCriteria createCriteriaWithSearch(int displayStart, int displaySize, int pageNumber, List<SearchField> searchFields) {
		return new PagingCriteria(displayStart, displaySize, searchFields, pageNumber);
	}

	/**
	 * Create criteria.
	 *
	 * @param displayStart
	 *            the display start
	 * @param displaySize
	 *            the display size
	 * @param pageNumber
	 *            the page number
	 * @return the paging criteria
	 */
	public static PagingCriteria createCriteria(int displaySize, int displayStart,  int pageNumber) {
		return new PagingCriteria(displaySize, displayStart, pageNumber);
	}

	/**
	 * Get default criteria.
	 *
	 * @return the paging criteria
	 */
	public static PagingCriteria getDefaultCriteria() {
		return DEFAULT_CRITERIA;
	}

	/**
	 * Gets display start.
	 *
	 * @return the display start
	 */
	public Integer getDisplayStart() {
		return displayStart;
	}

	/**
	 * Gets display size.
	 *
	 * @return the display size
	 */
	public Integer getDisplaySize() {
		return displaySize;
	}

	/**
	 * Gets search fields.
	 *
	 * @return the search fields
	 */
	public List<SearchField> getSearchFields() {
		if (this.searchFields == null) {
			return new ArrayList<SearchField>(0); // Lists.newArrayListWithCapacity(0);
		}
		return Collections.unmodifiableList(searchFields);
	}

	/**
	 * Gets sort fields.
	 *
	 * @return the sort fields
	 */
	public List<SortField> getSortFields() {
		if (this.sortFields == null) {
			return new ArrayList<SortField>(0); // Lists.newArrayListWithCapacity(0);
		}
		return Collections.unmodifiableList(sortFields);
	}

	/**
	 * Gets page number.
	 *
	 * @return the page number
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

}
