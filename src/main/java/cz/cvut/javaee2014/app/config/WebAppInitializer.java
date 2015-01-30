/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config;

import cz.cvut.javaee2014.app.config.web.WebMvcConfig;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

/**
 *
 * @author Stenlik
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
            new OpenSessionInViewFilter(),
            new DelegatingFilterProxy("springSecurityFilterChain")
        };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        
        servletContext.setInitParameter( "log4jConfigLocation" , "WEB-INF/classes/log4j.properties" );
        
        Log4jConfigListener log4jListener = new Log4jConfigListener();
	servletContext.addListener( log4jListener );
        
        /*FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");*/
        
        super.onStartup(servletContext); 
        logger.error(" -------------------> Hello ");
        
        /*FilterRegistration charEncodingfilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
        charEncodingfilterReg.setInitParameter("forceEncoding", "true");
        charEncodingfilterReg.addMappingForUrlPatterns(null, false, "/*"); */
        
    }  
    
}
