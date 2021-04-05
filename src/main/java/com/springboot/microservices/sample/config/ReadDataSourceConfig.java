package com.springboot.microservices.sample.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.springboot.microservices.sample.data.read",sqlSessionFactoryRef="readSqlSessionFactory")	/*멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정*/
@EnableTransactionManagement
public class ReadDataSourceConfig {
    @Primary
    @Bean(name="readDataSource")
    @ConfigurationProperties(prefix="spring.read.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Primary
    @Bean(name="readSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("readDataSource") DataSource readDataSource, ApplicationContext applicationContext)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(readDataSource);
        factoryBean.setTypeAliasesPackage("com.springboot.microservices.sample.model");
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/read/*.xml"));
        return factoryBean.getObject();
    }
 
    @Primary
    @Bean(name="readSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory readSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(readSqlSessionFactory);
    }
    
}