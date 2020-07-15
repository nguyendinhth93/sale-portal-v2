package com.tp.config.spring;

import net.sf.ehcache.management.ManagementService;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.management.MBeanServer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hopnv on 11/06/2017.
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@EnableCaching
@ComponentScan(basePackages = "com.tp")
@EnableJpaRepositories(basePackages = "com.tp.repo")
public class CMSConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public CustomScopeConfigurer customScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map<String, Object> viewScope = new HashMap<>();
        viewScope.put("view", new ViewScope());
        configurer.setScopes(viewScope);
        return configurer;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public ManagementService managementService() {
        ManagementService managementService = new ManagementService(ehCacheCacheManager().getObject(), mBeanServer(), true, true, true, true);
        managementService.init();
        return managementService;
    }

    @Bean
    public MBeanServerFactoryBean mBeanServerFactory() {
        MBeanServerFactoryBean mBeanServerFactory = new MBeanServerFactoryBean();
        mBeanServerFactory.setLocateExistingServerIfPossible(true);
        mBeanServerFactory.afterPropertiesSet();
        return mBeanServerFactory;
    }

    @Bean
    public MBeanServer mBeanServer() {
        return mBeanServerFactory().getObject();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
