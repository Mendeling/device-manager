package common.tools.dbtools.scaffold;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	private static final String ID = "id";
	private final String ENDL = "\n";
	private final String TAB = "    ";
	private final String TAB2 = TAB + TAB;
	private final String TAB3 = TAB2 + TAB;
	private final String TAB4 = TAB2 + TAB2;
	private String name;
	private String primaryKey;
	private List<ColumnInfo> columns;
	private List<FieldInfo> fields;

	public TableInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public void addColumn(ColumnInfo column) {
		if (columns == null){
            columns = new ArrayList<ColumnInfo>();
        }
		if (!columns.contains(column)) {
			columns.add(column);
			String type = column.parseJavaType();
			String name = column.parseFieldName();
			addFiled(new FieldInfo(type, name, column.getComment()));
		}
	}

	public void addFiled(FieldInfo field) {
		if (fields == null){
            fields = new ArrayList<FieldInfo>();
        }
		if (field.getName().equalsIgnoreCase(ID)){
            return;
        }
		if (!fields.contains(field)) {
			fields.add(field);
		}

	}

	public List<FieldInfo> getFields() {
		return fields;
	}

	public String getFieldsDeclareInfo() {
		StringBuffer sb = new StringBuffer();
		// StringBuffer sb = new StringBuffer(TAB+"private Integer id;"+ENDL);
		for (FieldInfo field : fields) {
			if (field.getName().equalsIgnoreCase(ID))
				continue;// id property is in the BaseModel
			// sb.append(TAB).append("/**").append(ENDL).append(TAB).append("*").append(StringUtils.isNotBlank(field.getComment())?field.getComment():field.getName()).append(ENDL).append(TAB).append("*/").append(ENDL);
			sb.append(TAB);
			sb.append("private ");
			sb.append(field.getType());
			sb.append(" ");
			sb.append(field.getName());
			sb.append(";");
			sb.append("//").append(StringUtils.isNotBlank(field.getComment()) ? field.getComment() : field.getName());
			sb.append(ENDL);
		}

		return sb.toString();
	}

	public String getFieldsGettersAndSettersInfo() {
		StringBuffer sb = new StringBuffer();
		for (FieldInfo field : fields) {
			if (field.getName().equalsIgnoreCase(ID)){
                continue;
            }
			sb.append(TAB).append("public ").append(field.getType()).append(" ").append("get" + StringUtils.capitalize(field.getName()) + "() {").append(ENDL);
			sb.append(TAB2).append("return " + field.getName() + ";").append(ENDL);
			sb.append(TAB).append("}").append(ENDL);
			sb.append(ENDL);
			sb	.append(TAB)
				.append("public void ")
				.append("set" + StringUtils.capitalize(field.getName()) + "(" + field.getType() + " " + field.getName() + ") {")
				.append(ENDL);
			sb.append(TAB2).append("this." + field.getName() + " = " + field.getName() + ";").append(ENDL);
			sb.append(TAB).append("}").append(ENDL);
		}
		return sb.toString();
	}

	public String getInsertStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + name);
		sb.append("( " + this.getColumnNames() + " ) values (");
		ColumnInfo col = null;
		sb.append("#{id,jdbcType=VARCHAR},");
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			sb.append("#{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append("}");
			if (i + 1 != columns.size()) {
				sb.append(",");
			}
		}
		sb.append(" )");
		return sb.toString();
	}

	public String getInsertBatch() {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + name).append("(").append(ENDL);		
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.parseJavaType().equals("String")){
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \"> " + ENDL);
			} else {
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null \"> " + ENDL);
				
			}
			sb.append(TAB4).append(col.getName()).append(",").append(ENDL);	
			sb.append(TAB3 + "</if>" + ENDL);
		}
		
		sb.append(TAB4).append(primaryKey).append(")values"
				+ "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">  (").append(ENDL);
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.parseJavaType().equals("String")){
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \"> " + ENDL);
			} else {
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null \"> " + ENDL);
				
			}
			sb.append(TAB4).append("#{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append("},"+ ENDL);			
			sb.append(TAB3 + "</if>" + ENDL);
		}
		sb.append(TAB3).append("#{id,jdbcType=VARCHAR}").append(")</foreach> ");		
		return sb.toString();
	}
	public String getInsertMapModel() {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + name).append("(").append(ENDL);		
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.parseJavaType().equals("String")){
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \"> " + ENDL);
			} else {
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null \"> " + ENDL);
				
			}
			sb.append(TAB4).append(col.getName()).append(",").append(ENDL);	
			sb.append(TAB3 + "</if>" + ENDL);
		}
		
		sb.append(TAB4).append(primaryKey).append(")values (").append(ENDL);
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.parseJavaType().equals("String")){
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \"> " + ENDL);
			} else {
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null \"> " + ENDL);
				
			}
			sb.append(TAB4).append("#{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append("},"+ ENDL);			
			sb.append(TAB3 + "</if>" + ENDL);
		}
		sb.append(TAB3).append("#{id,jdbcType=VARCHAR}").append(")");		
		return sb.toString();
	}
	public String getUpdateStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("update " + name + " set ");
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			sb.append(col.getName() + "=#{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append("}");
			if (i + 1 != columns.size()) {
				sb.append(",");
			}
		}
		sb.append(" where " + primaryKey + "=#{id,jdbcType=VARCHAR}");
		return sb.toString();
	}

	public String getResultMap() {
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<id property=\"id\"");
		sb.append(" column=\"" + primaryKey + "\"");
		sb.append(" jdbcType=\"VARCHAR\" />");
		sb.append(ENDL);
		for (ColumnInfo col : columns) {
			sb.append(TAB3);
			sb.append("<result property=\"" + col.parseFieldName() + "\" column=\"" + col.getName() + "\"");
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(" jdbcType=\"" + col.parseJdbcType() + "\"");
			}
			sb.append(" />");
			sb.append(ENDL);
		}
		return sb.toString();
	}

	public String getOtherCondition() {
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns) {
			sb.append(TAB3);
			if(col.parseJavaType().equals("String")){
				sb.append("<if test= \"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' and " + col.parseFieldName() + " != 'null' \">");
			} else {
				sb.append("<if test= \"" + col.parseFieldName() + " != null \">");
			}

			sb.append("	and " + col.getName() + " = #{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append(" }");
			sb.append("</if>");
			sb.append(ENDL);
		}
		return sb.toString();
	}

	public String getLikeCondition() {
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns) {
			sb.append(TAB3);
			if(col.parseJavaType().equals("String")){		
				sb.append("<if test= \"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' and " + col.parseFieldName() + " != 'null' \">");
			} else {
				sb.append("<if test= \"" + col.parseFieldName() + " != null \">");
			}
			if(col.parseJavaType().equals("String")){
				sb.append("	and " + col.getName() + " like \"%\"#{" + col.parseFieldName() + "}\"%\"");
			} else {
				sb.append("	and " + col.getName() + " = #{" + col.parseFieldName());
				if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
					sb.append(",jdbcType=" + col.parseJdbcType());
				}
				sb.append(" }");
			}
			sb.append("</if>");
			sb.append(ENDL);
		}
		return sb.toString();
	}

	public String getColumnNames() {
		StringBuffer sb = new StringBuffer();
		sb.append(primaryKey + ",");
		ColumnInfo column = null;
		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			sb.append(column.getName());
			if (i + 1 != columns.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String getUpdateMapModel() {// 鍔ㄦ�佸瓧娈垫洿鏂�
		StringBuffer sb = new StringBuffer();
		sb.append("update " + name + ENDL);
		sb.append(TAB2 + "<set>" + ENDL);
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.parseJavaType().equals("String")){
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \"> " + ENDL);
			} else {
				sb.append(TAB3 + "<if test=\"" + col.parseFieldName() + " != null \"> " + ENDL);				
			}
			sb.append(TAB4 + col.getName() + " = #{" + col.parseFieldName());
			if (!col.parseJdbcType().equals("DATE")) {// date涓嶄娇鐢╦dbcType,鐢ㄤ簬淇濊瘉骞存湀鏃�,鏃跺垎绉掑彲鐢�
				sb.append(",jdbcType=" + col.parseJdbcType());
			}
			sb.append("}," + ENDL);
			sb.append(TAB3 + "</if>" + ENDL);
		}
		sb.append(TAB2 + "</set>" + ENDL);
		sb.append(TAB2 + " where " + primaryKey + "=#{id,jdbcType=VARCHAR}");
		return sb.toString();
	}
}
