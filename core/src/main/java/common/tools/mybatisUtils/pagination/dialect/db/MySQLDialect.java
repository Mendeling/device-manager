/*
 * Copyright © 2012-2013 mumu@yfyang. All Rights Reserved.
 */

package common.tools.mybatisUtils.pagination.dialect.db;

import common.tools.mybatisUtils.pagination.dialect.Dialect;

/**
 * Mysql方言的实
 *
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class MySQLDialect implements Dialect {


    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset),
                Integer.toString(limit));
    }

    public boolean supportsLimit() {
        return true;
    }

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql               实际SQL语句
     * @param offset            分页始纪录条
     * @param offsetPlaceholder 分页始纪录条数－占位符号
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" limit ");
        if (offset > 0) {
            stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
        } else {
            stringBuilder.append(limitPlaceholder);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCountString(String querySqlString) {
        String sql = SQLServer2005Dialect.getNonOrderByPart(querySqlString);
        sql = sql.replaceAll("SELECT", "select");
		sql = sql.replaceAll("FROM", "from");
		sql = sql.replace(sql.substring(6, sql.indexOf("from")), " count(1) ");
		
		if(sql.contains("group by")){
			sql = "select count(1) from (" + sql + ") as tmp_count";
		}
        
        return sql;
        
        //下面的代码是原来
       /* String countSql = "select count(1) from (" + sql + ") as tmp_count";
        return countSql;*/
    }
}
