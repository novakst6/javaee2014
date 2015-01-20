/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config.web;

import cz.cvut.javaee2014.app.config.hibernate.PersistenceConfig;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * @author Stenlik
 */
@Configuration
@ComponentScan(basePackages = {"cz.cvut.javaee2014"})
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    PersistenceConfig persistenceConfig;
    
//    	@Bean
//	public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
//		ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
//		contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
//
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/WEB-INF/jsp/");
//		viewResolver.setSuffix(".jsp");
//
//		MappingJacksonJsonView defaultView = new MappingJacksonJsonView();
//		defaultView.setExtractValueFromSingleKeyModel(true);
//
//		ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
//		contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
//		contentViewResolver.setDefaultViews(Arrays.<View> asList(new MappingJacksonJsonView()));
//		contentViewResolver.setViewResolvers(Arrays.<ViewResolver> asList(viewResolver));
//		contentViewResolver.setDefaultViews(Arrays.<View> asList(defaultView));
//		return contentViewResolver;
//	}

    
    @Bean
    public ViewResolver resolver() {
        InternalResourceViewResolver url = new InternalResourceViewResolver();
        url.setPrefix("/WEB-INF/jsp/");
        url.setSuffix(".jsp");
        return url;
    }
    
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenSessionInViewInterceptor interceptor = new OpenSessionInViewInterceptor();
        interceptor.setSessionFactory(persistenceConfig.sessionFactory());
        registry.addWebRequestInterceptor(interceptor);
    }

    

}
