/*
 * Copyright © 2012-2013 mumu@yfyang. All Rights Reserved.
 */

package common.tools.mybatisUtils.pagination.dialect.db;

import common.tools.mybatisUtils.pagination.dialect.Dialect;

/**
 * A dialect compatible with the H2 database.
 *
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class H2Dialect implements Dialect {

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
     * @param limit             分页每页显示纪录条数
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    private String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return sql + ((offset > 0) ? " limit " + limitPlaceholder + " offset "
                + offsetPlaceholder : " limit " + limitPlaceholder);
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    @Override
    public String getCountString(String querySqlString) {
        String sql = SQLServer2005Dialect.getNonOrderByPart(querySqlString);
        return "select count(1) from (" + sql + ") as tmp_count";
    }
}