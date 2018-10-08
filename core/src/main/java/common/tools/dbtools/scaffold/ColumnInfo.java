package common.tools.dbtools.scaffold;

import org.apache.commons.lang3.StringUtils;

public class ColumnInfo {
	private String name;
	private String type;
	private int size;
	private int digits;
	private boolean nullable;
	private String comment;
	private final WordsParser wordsParser;

	public ColumnInfo(String name, String type, int size, int decimalDigits, int nullable,String comment) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.digits = decimalDigits;
		if (nullable == 1){
            this.nullable = true;
        }
		this.comment = comment;
		if (ScaffoldUtil.isUpperCase(name) || name.contains(ScaffoldUtil.UNDER_LINE)) {
			this.wordsParser = new UnderlineSplitWordsParser();
		} else {
			this.wordsParser = new UncapitalizeWordsParser();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String parseJavaType() {
		String jdbcType = StringUtils.upperCase(getType());
		String result = "Integer";
		if (jdbcType.contains("CHAR")) {
			result = "String";
		}
		if (jdbcType.contains("CLOB")) {
			result = "String";
		}
		if (jdbcType.contains("TEXT")) {
			result = "String";
		}
		if (jdbcType.equals("DATE")) {
			result = "java.time.LocalDate";
		}
		if (jdbcType.equals("DATE")) {
			result = "java.time.LocalDate";
		}
		if (jdbcType.equals("TIME")) {
			result = "java.time.LocalTime";
		}
		if (jdbcType.equals("DATETIME")) {
			result = "java.time.LocalDateTime";
		}
		if (jdbcType.contains("DECIMAL") || getDigits() > 0) {
			result = "java.math.BigDecimal";
		}
		return result;
	}

	public String parseJdbcType() {
		String javaType = parseJavaType();
		String result = "NUMERIC";
		if ("String".equals(javaType)) {
			result = "VARCHAR";
		}
		if (javaType.equalsIgnoreCase("int")) {
			result = "INTEGER";
		}
		if (javaType.contains("Date")) {
			result = "DATE";
		}
		return result;
	}

	public String parseFieldName() {
		return wordsParser.parseWords(name);
	}

	@Override
	public String toString() {
		return name + " " + type + " " + size + " " + digits + " " + nullable+ " " + comment;
	}
}
