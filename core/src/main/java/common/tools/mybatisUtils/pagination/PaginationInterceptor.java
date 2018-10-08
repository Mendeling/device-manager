/*
 * Copyright © 2012-2013 mumu@yfyang. All Rights Reserved.
 */

package common.tools.mybatisUtils.pagination;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import common.tools.mybatisUtils.pagination.dialect.DBMS;
import common.tools.mybatisUtils.pagination.dialect.Dialect;
import common.tools.mybatisUtils.pagination.dialect.DialectClient;
import common.tools.mybatisUtils.pagination.helper.CountHelper;

/**
 * <p>
 * Mybatis数据库分页插.
 * 拦截StatementHandler的prepare方法
 * </p>
 *
 * @author poplar.yfyang
 * @version 1.0 2011-11-18 下午12:31
 * @since JDK 1.5
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
public class PaginationInterceptor implements Interceptor, Serializable {
	
	
	   public static final int INDEX_NOT_FOUND = -1;
	    /** The empty String {@code ""}. */
	    public static final String EMPTY = "";
	    /** The dot String {@code ","}. */
	    public static final String DOT_CHAR = ",";
	    /** The blank String {@code " "}. */
	    public static final String BLANK_CHAR = " ";
	    /** The equal sign String {@code "="} */
	    public static final String EQUAL_SIGN_CHAR = "=";
	    /**
	     * The like String {@code "like"}
	     */
	    public static final String LIKE_CHAR = " like ";
	    private static final String INJECTION_SQL = ".*([';]+|(--)+).*";
	    public static String LIKE_FORMATE = "'%%%s%%'";
	    public static String LIKE_PER_FORMATE = "'%s%%'";
	    public static String PER_LIKE_FORMATE = "'%%%s'";
	    public static String EQUAL_FORMATE = "'%s'";
    /** serial Version */
    private static final long serialVersionUID = -6075937069117597841L;
    private static final ThreadLocal<Integer> PAGINATION_TOTAL = new ThreadLocal<Integer>();
    private static final ThreadLocal<PagingCriteria> PAGE_REQUEST = new ThreadLocal<PagingCriteria>();
    /** mapped statement parameter index. */
    private static final int MAPPED_STATEMENT_INDEX = 0;
    /** sql id , in the mapper xml file. */
    private static String _sql_regex = "[*]";
    /** DataBase dialect. */
    protected Dialect _dialect;
    
    
    
    /**
     * perform paging interception.
     *
     * @param queryArgs Executor.query params.
     */
    private void processIntercept(final Object[] queryArgs) {
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[1];
        //the need for paging intercept.
//        boolean interceptor = ms.getId().matches(_sql_regex);
        boolean interceptor = ms.getId().indexOf(_sql_regex)>=0;
        //obtain paging information.
		final PagingCriteria pageRequest = interceptor ? PagingParametersFinder.instance.findCriteria(parameter) : PagingCriteria
				.getDefaultCriteria();
		PAGE_REQUEST.set(pageRequest);

        final RowBounds oldRow = (RowBounds) queryArgs[2];
        final RowBounds rowBounds = (interceptor) ? offset_paging(oldRow, pageRequest) : oldRow;
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();

        if (_dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            final BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            Connection connection = null;
            
            String new_sql = sortAndFilterSql(sql, pageRequest);
            try {
                //get connection
                connection = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
                int count = CountHelper.getCount(new_sql, connection, ms, parameter, boundSql, _dialect);
                PAGINATION_TOTAL.set(count);
            } catch (SQLException e) {
                log.error("The total number of access to the database failure.", e);
            } finally {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    log.error("Close the database connection error.", e);
                }
            }
            
            if (_dialect.supportsLimit()) {
                new_sql = _dialect.getLimitString(new_sql, offset, limit);
                offset = RowBounds.NO_ROW_OFFSET;
            } else {
                new_sql = _dialect.getLimitString(new_sql, 0, limit);
            }
            if (log.isDebugEnabled()) {
                log.debug("pagination sql is :[" + new_sql + "]");
            }
            limit = RowBounds.NO_ROW_LIMIT;

            queryArgs[2] = new RowBounds(offset, limit);

            BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, new_sql);

            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    /**
     * Gets pagination total.
     *
     * @return the pagination total
     */
    public static int getPaginationTotal() {
        if (PAGINATION_TOTAL.get() == null) {
            return 0;
        }
        return PAGINATION_TOTAL.get();
    }

    /**
     * Gets page request.
     *
     * @return the page request
     */
    public static PagingCriteria getPageRequest() {
        return PAGE_REQUEST.get();
    }

    /** clear total context. */
    public static void clean() {
        PAGE_REQUEST.remove();
        PAGINATION_TOTAL.remove();
    }
    /**
     * Like value.
     *
     * @param value the value
     * @return the string
     */
    public static String likeValue(String value) {
        return String.format(LIKE_FORMATE, transactSQLInjection(value));
    }
    
    public static String formatValue(String value, String valuePattern) {
    	if(valuePattern ==null || valuePattern.length()==0){
    		return transactSQLInjection(value);
    	}
    	return String.format(valuePattern, transactSQLInjection(value));
    }
    public static String transactSQLInjection(String sql) {
    	if(sql == null){
    		return "";
    	}
        return sql.replaceAll(INJECTION_SQL, " ");
    }
    /**
     * Set the paging information,to RowBuounds.
     *
     * @param rowBounds rowBounds.
     * @return rowBounds.
     */
    private static RowBounds offset_paging(RowBounds rowBounds, PagingCriteria pageRequest) {
        // rowBuounds has offset.
        if (rowBounds.getOffset() == RowBounds.NO_ROW_OFFSET) {
            if (pageRequest != null) {
                return new RowBounds(pageRequest.getDisplayStart(), pageRequest.getDisplaySize());
            }
        }
        return rowBounds;
    }

    /**
     * Sort and filter sql.
     *
     *
     * @param sql the sql
     * @param pagingCriteria the paging criteria
     * @return the string
     */
    private static String sortAndFilterSql(String sql, PagingCriteria pagingCriteria) {
        boolean order = false; //SqlRemoveHelper.containOrder(sql);
        final List<SearchField> searchFields = pagingCriteria.getSearchFields();
        if (searchFields != null && !searchFields.isEmpty()) {
            List<String> where_field = Lists.newArrayList();
            for (SearchField searchField : searchFields) {
                // fix inject sql
            	if (!isEmpty(searchField.getOperator())) {
            		if (!isEmpty(searchField.getTable())) {
            			where_field.add(searchField.getTable() + "." + searchField.getField() + searchField.getOperator() + formatValue(searchField.getValue(), searchField.getValuePattern()));
            		} else {
            			where_field.add(searchField.getField() + searchField.getOperator() + formatValue(searchField.getValue(), searchField.getValuePattern()));
            		}
            	} else {
            		where_field.add(searchField.getField() + LIKE_CHAR + likeValue(searchField.getValue()));
            	}
            }
            boolean where = false; //SqlRemoveHelper.containWhere(sql);
            String orderBy = StringUtils.EMPTY;
            if (order) {
                /*String[] sqls = sql.split(SqlRemoveHelper.ORDER_REGEX);
                sql = sqls[0];
                orderBy = CountHelper.SQL_ORDER + sqls[1];*/
            }
            /*原来
             * sql = String.format((where ? CountHelper.OR_SQL_FORMAT : CountHelper.WHERE_SQL_FORMAT), sql
                    , Joiner.on(CountHelper.OR_JOINER).skipNulls().join(where_field), orderBy);*/
           /* 2014/12/16原来 sql = String.format((where ? CountHelper.OR_SQL_FORMAT : CountHelper.WHERE_SQL_FORMAT), sql
                    , Joiner.on(CountHelper.AND_JOINER).skipNulls().join(where_field), orderBy);*/
            
            
            //新加
            String[] arys = new String[2];
    		if(sql.contains("group by")){
    			arys = sql.split("group by");
    			arys[1] = " group by " + arys[1];
    			sql = arys[0];
    		}
    		if(arys[1] == null || "".equals(arys[1].trim())){
    			arys[1] = "";
    		}
    		sql = String.format((where ? CountHelper.OR_SQL_FORMAT : CountHelper.WHERE_SQL_FORMAT), sql
                    , Joiner.on(CountHelper.AND_JOINER).skipNulls().join(where_field), orderBy);
    		
    		sql += arys[1];
            
            
            //return sql;
        }

        final List<SortField> sortFields = pagingCriteria.getSortFields();
        if (sortFields != null && !sortFields.isEmpty()) {
            List<String> field_order = Lists.newArrayList();
            for (SortField sortField : sortFields) {
                field_order.add(sortField.getField() + BLANK_CHAR + sortField.getDirection().getDirection());
            }
            sql = String.format(order ? CountHelper.SQL_FORMAT : CountHelper.ORDER_SQL_FORMAT, sql
                    , Joiner.on(DOT_CHAR).skipNulls().join(field_order));
            /*return String.format(order ? CountHelper.SQL_FORMAT : CountHelper.ORDER_SQL_FORMAT, sql
                    , Joiner.on(StringHelper.DOT_CHAR).skipNulls().join(field_order));*/
        }

        return sql;
    }

    /**
     * Copy from bound sql.
     *
     * @param ms the ms
     * @param boundSql the bound sql
     * @param sql the sql
     * @return the bound sql
     */
    public static BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
                                            String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    //see: MapperBuilderAssistant
    private static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keyProperties = ms.getKeyProperties();
        builder.keyProperty(keyProperties == null ? null : keyProperties[0]);

        //setStatementTimeout()
        builder.timeout(ms.getTimeout());

        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    

    @Override
    public Object plugin(Object o) {
        if (Executor.class.isAssignableFrom(o.getClass())) {
            return Plugin.wrap(new PaginationExecutor((Executor) o), this);
        }
        return Plugin.wrap(o, this);

    }

    /**
     * 设置属�，支持自定义方类和制定数据库的方式
     * <p>
     * <code>dialectClass</code>,自定义方类�可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlRegex</code> 要拦截的SQL ID
     * </p>
     * 如果同时配置<code>dialectClass</code><code>dbms</code>,则以<code>dbms</code>为主
     *
     * @param p 属?
     */
    @Override
    public void setProperties(Properties p) {
    	String dialectClass = p.getProperty("dialectClass");
        DBMS dbms = null;
        if (StringUtils.isEmpty(dialectClass)) {
            String dialect = p.getProperty("dbms");
            Preconditions.checkArgument(StringUtils.isNotEmpty(dialect), "dialect property is not found!");
            dbms = DBMS.valueOf(dialect.toUpperCase());
            Preconditions.checkNotNull(dbms, "plugin not super on this database.");
        } else {
            try {
				Dialect dialect1 = (Dialect) (Class.forName(dialectClass).newInstance());
				Preconditions.checkNotNull(dialect1, "dialectClass is not found!");
				DialectClient.putEx(dialect1);
				dbms = DBMS.EX;
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }


        _dialect = DialectClient.getDbmsDialect(dbms);

        String sql_regex = p.getProperty("sqlRegex");
        if (StringUtils.isNotEmpty(sql_regex)) {
            _sql_regex = sql_regex;
        }
        clean();
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
    /**
	 * 判断个字符串是否为空
	 */
	public static boolean isEmpty(String s) {
		return (s == null || s.trim().equals("")) ? true : false;
	}
}
