package com.lang.ftd.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import common.tools.mybatisUtils.pagination.PaginationInterceptor;

@Configuration
// 加上这个注解，使得支持事务
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {
	@Autowired
	private DataSource dataSource;
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		
	    return new DataSourceTransactionManager(dataSource);
	}
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(PaginationInterceptor pageHelper) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPlugins(new Interceptor[] { pageHelper });
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
  		 bean.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean
    public PaginationInterceptor pageHelper() {
    	PaginationInterceptor pageHelper = new PaginationInterceptor();
        Properties p = new Properties();
        p.setProperty("dbms", "MYSQL");
        p.setProperty("sqlRegex", "ByPage");
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
