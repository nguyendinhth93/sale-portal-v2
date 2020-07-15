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
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan({"com.tp.crmhcm"})
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(entityManagerFactoryRef = "crmHCMEntityManagerFactory",
        transactionManagerRef = "crmHCMTransactionManager", basePackages = {"com.tp.crmhcm.repo"})
public class HibernateCRMHCMConfig {

    @Autowired
    private Environment environment;

    @SuppressWarnings("Duplicates")
    @Bean(name = "crmHCMDataSource")
    public DataSource crmHCMDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getRequiredProperty("crmhcm.jdbc.url"));
        dataSource.setDriverClassName(environment.getRequiredProperty("crmhcm.jdbc.driverClassName"));
        dataSource.setUsername(environment.getRequiredProperty("crmhcm.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("crmhcm.jdbc.password"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("crmhcm.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("crmhcm.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("crmhcm.hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("crmhcm.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("crmhcm.hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("crmhcm.hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("crmhcm.hibernate.c3p0.timeout"));
        properties.put("hibernate.c3p0.max_statements", environment.getRequiredProperty("crmhcm.hibernate.c3p0.max_statements"));
        properties.put("hibernate.c3p0.idle_test_period", environment.getRequiredProperty("crmhcm.hibernate.c3p0.idle_test_period"));
        properties.put("hibernate.cache.user_query_cache", environment.getRequiredProperty("crmhcm.hibernate.cache.user_query_cache"));

        return properties;
    }

    @Bean(name = "crmHCMEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.SQL_SERVER);
        vendorAdapter.setDatabasePlatform("com.tp.config.hibernate.SQlServerDBDialect");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(crmHCMDataSource());
        em.setPackagesToScan("com.tp.crmhcm.model");
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceUnitName(Const.PERSISTENCE.CRM_HCM);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(name = "crmHCMTransactionManager")
    public PlatformTransactionManager crmHCMTransactionManager(
            @Qualifier("crmHCMEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
