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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.springboot.microservices.sample.data.write",sqlSessionFactoryRef="writeSqlSessionFactory")	/*멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정*/
@EnableTransactionManagement
public class WriteDataSourceConfig {
    @Bean(name="writeDataSource")
    @ConfigurationProperties(prefix="spring.write.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name="writeSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("writeDataSource") DataSource writeDataSource, ApplicationContext applicationContext)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(writeDataSource);
        factoryBean.setTypeAliasesPackage("com.springboot.microservices.sample.model");
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/write/*.xml"));
        return factoryBean.getObject();
    }
 
    @Bean(name="writeSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory writeSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(writeSqlSessionFactory);
    }
 
}