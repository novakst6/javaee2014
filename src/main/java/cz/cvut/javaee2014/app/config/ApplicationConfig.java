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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 *
 * @author Stenlik
 */
@Configuration
@ComponentScan //(basePackages = {"cz.cvut.javaee2014"})
@EnableCaching //@Cachable required
public class ApplicationConfig {
    
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }
    
    @Bean
    public ShaPasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }
 
    @Bean
    public static CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }    
    
    @Bean
    public ApplicationContextProvider appContextProvider(){
        return new ApplicationContextProvider();
    }
}
