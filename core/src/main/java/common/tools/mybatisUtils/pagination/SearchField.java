package common.tools.mybatisUtils.pagination;

import java.io.Serializable;

/**
 * <p>
 * The DataTables search filed model.
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-09-12 10:22 AM
 * @since JDK 1.5
 */
public class SearchField implements Serializable {

    private static final long serialVersionUID = -8493268499000005405L;
    
    public String table;
    /** field name */
    private final String field;
    /** True if the individual column filter should be treated as a regular expression for advanced filtering, false if not */
    private final boolean regex;
    /** Indicator for if a column is flagged as sortable or not on the client-side */
    private final boolean searchable;
    /** search value. */
    private final String value;
    
    /**
     * 操作
     */
    private String operator;
    /**
     * 值模
     */
    private String valuePattern;

    /**
     * Instantiates a new Search field.
     *
     * @param field the field
     * @param regex the regex
     * @param searchable the searchable
     * @param value the value
     */
    public SearchField(String field, boolean regex, boolean searchable, String value) {
        this.field = field;
        this.regex = regex;
        this.searchable = searchable;
        this.value = value;
    }
    
    public SearchField(String field, boolean regex, boolean searchable, String value, String operator, String valuePattern, String table) {
    	this(field, regex, searchable, value);
    	this.operator = operator;
    	this.valuePattern = valuePattern;
    	this.table = table;
    }
    
    public SearchField(String field,  String value) {
        this.field = field;
        this.regex = false;
        this.searchable = true;
        this.value = value;
    }
    public SearchField(String field, String value, String operator) {
    	this(field, false, true, value);
    	this.operator = operator;
    }
    public SearchField(String field, String value, String operator, String valuePattern) {
    	this(field, false, true, value);
    	this.operator = operator;
    	this.valuePattern = valuePattern;
    }
    public SearchField(String field, String value, String operator, String valuePattern,String table) {
    	this(field, false, true, value);
    	this.operator = operator;
    	this.valuePattern = valuePattern;
    	this.table = table;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets field.
     *
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Is regex.
     *
     * @return the boolean
     */
    public boolean isRegex() {
        return regex;
    }

    /**
     * Is searchable.
     *
     * @return the boolean
     */
    public boolean isSearchable() {
        return searchable;
    }

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValuePattern() {
		return valuePattern;
	}

	public void setValuePattern(String valuePattern) {
		this.valuePattern = valuePattern;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
}
