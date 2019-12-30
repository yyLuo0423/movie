package com.movie.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@ComponentScan("com.wxcore.com.model.generated")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JooqConfig {
  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();

    dataSource.setUrl("jdbc:mysql://localhost:3306/wxdemo");
    dataSource.setUsername("root");
    dataSource.setPassword("");
    dataSource.setConnectionInitSqls(Arrays.asList("set names utf8mb4;"));

    return dataSource;
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  }

  @Bean
  public DefaultDSLContext dsl(org.jooq.Configuration configuration) {
    return new DefaultDSLContext(configuration);
  }

  @Bean
  public DefaultConfiguration configuration(
      DataSourceConnectionProvider connectionProvider) {

    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider);
    jooqConfiguration.set(SQLDialect.MYSQL);

    return jooqConfiguration;
  }

  @Bean(initMethod = "migrate")
  Flyway flyway() {
    Flyway flyway = new Flyway();
    flyway.setBaselineOnMigrate(true);
    flyway.setDataSource("jdbc:mysql://localhost:3306/wxdemo", "root", "");
    return flyway;
  }
}
