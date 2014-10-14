/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 *
 * @author Stenlik
 */
@Configuration
@ComponentScan(basePackages = {"cz.cvut.javaee2014"})
@EnableCaching //@Cachable required
@PropertySource({"classpath:config/filesystem.properties"})
public class ApplicationConfig {
    
    @Autowired
    private Environment env;
    
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }
    
    @Bean
    public ShaPasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        Long maxUploadSize = Long.parseLong(env.getProperty("app.filesystem.upload.max"));
        resolver.setMaxUploadSize(maxUploadSize);
        return resolver;
    }    
}
