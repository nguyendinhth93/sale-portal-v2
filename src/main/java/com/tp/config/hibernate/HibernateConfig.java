package com.tp.config.hibernate;

import com.tp.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by hopnv on 11/06/2017.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.tp"})
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = {"com.tp.dwh.repo"})
public class HibernateConfig {

    @Autowired
    private Environment environment;

    @Primary
    @SuppressWarnings("Duplicates")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("hibernate.c3p0.timeout"));
        properties.put("hibernate.c3p0.max_statements", environment.getRequiredProperty("hibernate.c3p0.max_statements"));
        properties.put("hibernate.c3p0.idle_test_period", environment.getRequiredProperty("hibernate.c3p0.idle_test_period"));
        properties.put("hibernate.cache.user_query_cache", environment.getRequiredProperty("hibernate.cache.user_query_cache"));

        return properties;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.tp.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceUnitName(Const.PERSISTENCE.SALE_PORTAL);
        return em;
    }

  /*  @Primary
    @Bean
    @Autowired
    public EntityManager entityManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        EntityManager em = localContainerEntityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);
        return em;
    }*/

    @Primary
    @Bean(name = "transactionManager")
    @Autowired
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory")LocalContainerEntityManagerFactoryBean emf) throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());
        return transactionManager;
    }

}
