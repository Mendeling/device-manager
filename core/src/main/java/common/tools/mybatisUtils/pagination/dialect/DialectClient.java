/*
 * Copyright © 2012-2013 mumu@yfyang. All Rights Reserved.
 */

package common.tools.mybatisUtils.pagination.dialect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import common.tools.mybatisUtils.pagination.dialect.db.DB2Dialect;
import common.tools.mybatisUtils.pagination.dialect.db.H2Dialect;
import common.tools.mybatisUtils.pagination.dialect.db.HSQLDialect;
import common.tools.mybatisUtils.pagination.dialect.db.MySQLDialect;
import common.tools.mybatisUtils.pagination.dialect.db.OracleDialect;
import common.tools.mybatisUtils.pagination.dialect.db.PostgreSQLDialect;
import common.tools.mybatisUtils.pagination.dialect.db.SQLServer2005Dialect;
import common.tools.mybatisUtils.pagination.dialect.db.SQLServerDialect;
import common.tools.mybatisUtils.pagination.dialect.db.SybaseDialect;


/**
 * <p>
 * 数据库分页方获取.
 * </p>
 *
 * @author poplar.yfyang
 * @version 1.0 2011-11-18 下午2:54
 * @since JDK 1.5
 */
public class DialectClient implements Serializable {
	private static final long serialVersionUID = 8107330250767760951L;
	private static final Map<DBMS, Dialect> DBMS_DIALECT = new HashMap<DBMS, Dialect>();

	/**
	 * 根据数据库名称获取数据库分页查询的方实现
	 *
	 * @param dbms 数据库名
	 * @return 数据库分页方实现
	 */
	public static Dialect getDbmsDialect(DBMS dbms) {
		if (DBMS_DIALECT.containsKey(dbms)) {
			return DBMS_DIALECT.get(dbms);
		}
		Dialect dialect = createDbmsDialect(dbms);
		DBMS_DIALECT.put(dbms, dialect);
		return dialect;
	}

	/**
	 * 插入自定义方的实
	 *
	 * @param exDialect 方言实现
	 */
	public static void putEx(Dialect exDialect) {
		DBMS_DIALECT.put(DBMS.EX, exDialect);
	}

	/**
	 * 创建数据库方
	 *
	 * @param dbms 数据
	 * @return 数据
	 */
	private static Dialect createDbmsDialect(DBMS dbms) {
		switch (dbms) {
			case MYSQL:
				return new MySQLDialect();
			case ORACLE:
				return new OracleDialect();
			case DB2:
				return new DB2Dialect();
			case POSTGRE:
				return new PostgreSQLDialect();
			case SQLSERVER:
				return new SQLServerDialect();
			case SQLSERVER2005:
				return new SQLServer2005Dialect();
			case SYBASE:
				return new SybaseDialect();
			case H2:
				return new H2Dialect();
			case HSQL:
				return new HSQLDialect();
			default:
				throw new UnsupportedOperationException("Empty dbms dialect");
		}
	}


}
