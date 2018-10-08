package common.tools.dbtools.scaffold;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class ScaffoldGen {

	private static Logger logger = LoggerFactory.getLogger(ScaffoldGen.class);
	private static final String NULLABLE = "NULLABLE";
	private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
	private static final String COLUMN_SIZE = "COLUMN_SIZE";
	private static final String TYPE_NAME = "TYPE_NAME";
	private static final String MYSQL = "mysql";
	private static final String COLUMN_NAME = "COLUMN_NAME";
	private static final String JDBC_PASSWORD = "spring.datasource.password";
	private static final String JDBC_USER = "spring.datasource.username";
	private static final String JDBC_URL = "spring.datasource.url";
	private static final String JDBC_DRIVER = "spring.datasource.driver-class-name";
	private static final String JDBC_SCHEMA = "schema";
	private static final String CONFIG_PROPERTIES = "application.yml"; // 配置路径文件
	private Connection conn;
	private String schema;
	private DatabaseMetaData metaData;
	private final String pkgName;
	private final String clzName;
	private final String tblName;
	private final String clzComment;


	/**
	 * 
	 * @param pkgName
	 * @param clzName
	 * @param clzComment
	 * @param tblName
	 */
	public ScaffoldGen(String pkgName, String clzName, String clzComment, String tblName) {
		this.pkgName = pkgName;
		this.clzName = StringUtils.capitalize(clzName);
		this.clzComment = clzComment;
		this.tblName = tblName;
	}

	public void execute() {
		execute(false);
	}

	public void execute(boolean debug) {
		if (!initConnection()) {
			logger.debug("数据库配置文件错误，请保持正确路径：application.yml" + CONFIG_PROPERTIES);
			return;
		}
		TableInfo tableInfo = parseDbTable(this.tblName);
		if (tableInfo == null) {
			return;
		}

		ScaffoldBuilder sf = new ScaffoldBuilder(this.pkgName, this.clzName, this.clzComment, tableInfo);
		List<FileGenerator> list = sf.buildGenerators();
		for (FileGenerator gen : list) {
			gen.execute(debug);
		}
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> getYamlObject(){
		YamlMapFactoryBean  config = new YamlMapFactoryBean();
		config.setResources(new ClassPathResource(CONFIG_PROPERTIES));
		Map<String,Object> yamlParam = config.getObject();
		Map<String ,Object> springMap = (Map<String, Object>) yamlParam.get("spring");
		Map<String ,Object> datasourceMap = (Map<String, Object>) springMap.get("datasource");
		Map<String ,Object> result = Maps.newHashMap();
		datasourceMap.forEach((k,v)->{
			result.put("spring.datasource."+k, v);
		});
		return result;
	}
	
	private boolean initConnection() {
		String driver = null;
		String url = StringUtils.EMPTY;
		String user = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		String schema = StringUtils.EMPTY;
		Map<String,Object> yamlParam = this.getYamlObject();
		try {
			driver =  (String) yamlParam.get(JDBC_DRIVER);
			url =  (String) yamlParam.get(JDBC_URL);
			user =  (String) yamlParam.get(JDBC_USER);
			password =  (String) yamlParam.get(JDBC_PASSWORD);
			schema = (String) yamlParam.get(JDBC_SCHEMA);
			if (StringUtils.isNotBlank(schema)) {
				this.schema = schema;
			}
			if (driver.toLowerCase().contains(MYSQL)) {
				this.schema = "information_schema";
			}
			if (StringUtils.isBlank(this.schema)) {
				this.schema = user;
			}
			Class.forName(driver);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.debug("Jdbc driver not found - " + driver);
			return false;
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			// conn = DriverManager.getConnection(url);
			if (conn == null) {
				logger.debug("Database connection is null");
				return false;
			}
			metaData = conn.getMetaData();
			if (metaData == null) {
				logger.debug("Database MetaData is null");
				return false;
			}
		}
		catch (SQLException e) {
			logger.debug("Database connect failed");
			e.printStackTrace();
		}
		return true;
	}

	private TableInfo parseDbTable(String tableName) {
		TableInfo tableInfo = new TableInfo(tableName);
		ResultSet rs = null;
		logger.debug("parseDbTable begin");
		try {
			rs = metaData.getPrimaryKeys(conn.getCatalog(), schema, tableName);
			if (rs.next()) {
				tableInfo.setPrimaryKey(rs.getString(COLUMN_NAME));
			}
			if (rs.next()) {
				return null;
			}
		}
		catch (SQLException e) {
			logger.error("Table " + tableName + " parse error.");
			e.printStackTrace();
			return null;
		}
		logger.debug("PrimaryKey : " + tableInfo.getPrimaryKey());

		try {
			rs = metaData.getColumns(conn.getCatalog(), schema, tableName, "");
			if (!rs.next()) {
				logger.debug("Table " + schema + "." + tableName + " not found.");
				return null;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				String columnName = rs.getString(COLUMN_NAME);
				String columnType = rs.getString(TYPE_NAME);
				int datasize = rs.getInt(COLUMN_SIZE);
				int digits = rs.getInt(DECIMAL_DIGITS);
				int nullable = rs.getInt(NULLABLE);
				String comment = rs.getString("REMARKS");
				ColumnInfo colInfo = new ColumnInfo(columnName, columnType, datasize, digits, nullable, comment);
				logger.debug("DB column : " + colInfo);
				logger.debug("Java field : " + colInfo.parseFieldName() + " / " + colInfo.parseJavaType());
				tableInfo.addColumn(colInfo);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("Table " + tableName + " parse error.");
		}
		logger.debug("parseDbTable end");
		return tableInfo;
	}

}
