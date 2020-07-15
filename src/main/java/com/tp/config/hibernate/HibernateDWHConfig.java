package com.tp.config.hibernate;

import com.tp.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan({"com.tp.dwh"})
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(entityManagerFactoryRef = "dwhEntityManagerFactory",
        transactionManagerRef = "dwhTransactionManager", basePackages = {"com.tp.dwh.repo"})
public class HibernateDWHConfig {

    @Autowired
    private Environment environment;

    @SuppressWarnings("Duplicates")
    @Bean(name = "dwhDataSource")
    public DataSource dwhDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getRequiredProperty("dwh.jdbc.url"));
        dataSource.setDriverClassName(environment.getRequiredProperty("dwh.jdbc.driverClassName"));
        dataSource.setUsername(environment.getRequiredProperty("dwh.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("dwh.jdbc.password"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("dwh.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("dwh.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("dwh.hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("dwh.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("dwh.hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("dwh.hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("dwh.hibernate.c3p0.timeout"));
        properties.put("hibernate.c3p0.max_statements", environment.getRequiredProperty("dwh.hibernate.c3p0.max_statements"));
        properties.put("hibernate.c3p0.idle_test_period", environment.getRequiredProperty("dwh.hibernate.c3p0.idle_test_period"));
        properties.put("hibernate.cache.user_query_cache", environment.getRequiredProperty("dwh.hibernate.cache.user_query_cache"));

        return properties;
    }

    @Bean(name = "dwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dwhDataSource());
        em.setPackagesToScan("com.tp.dwh.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceUnitName(Const.PERSISTENCE.DHW);
        return em;
    }

    @Bean(name = "dwhTransactionManager")
    public PlatformTransactionManager dwhTransactionManager(
            @Qualifier("dwhEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
