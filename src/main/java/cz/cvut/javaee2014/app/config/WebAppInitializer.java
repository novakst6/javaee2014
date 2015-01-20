/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config;

import cz.cvut.javaee2014.app.config.web.WebMvcConfig;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author Stenlik
 */
@PropertySource("classpath:config/app.properties")
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
            new DelegatingFilterProxy("springSecurityFilterChain"),
            new MultipartFilter()
        };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        
        // logger
        //servletContext.setInitParameter( "log4jConfigLocation" , "file:log4j.properties" );        
        /*Log4jConfigListener log4jListener = new Log4jConfigListener();
	servletContext.addListener( log4jListener );*/
        initLogger();
        Logger.getRootLogger().info("Work dir: "+System.getProperty("user.dir")); 
        
        // root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class, WebAppInitializer.class);
        //servletContext.addListener(new ContextLoaderListener(rootContext));

        // spring security
        servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain")).addMappingForUrlPatterns(null, false, "/*");

        // file upload - multipart
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setMultipartConfig(
                new MultipartConfigElement(
                        "/", 
                        25 * 1024 * 1024,   // max file size
                        125 * 1024 * 1024,  // max request size
                        1 * 1024 * 1024)    // hdd write treshold
        );        
        
        super.onStartup(servletContext); //To change body of generated methods, choose Tools | Templates.
    }
    
    //<editor-fold defaultstate="collapsed" desc="Logger init">
    
    @Value("${filesys.root}")
    public String rootDirectory;
    
    public void initLogger() {
        
        try{
            
            // hlavní nastavení
            Logger.getRootLogger().setLevel(Level.INFO);
            Logger.getRootLogger().setAdditivity(false);
            
            // konzole
            //Appender ca = Logger.getRootLogger().getAppender("stdout");
            //ca.setLayout(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
            ConsoleAppender console = new ConsoleAppender();
            String PATTERN = "%d [%p|%c|%C{1}] %m%n";
            console.setLayout(new PatternLayout(PATTERN)); 
            console.setThreshold(Level.INFO);
            console.activateOptions();
            
            // cz.cvut.javaee2014
            Logger.getRootLogger().getLoggerRepository().getLogger("cz.cvut.javaee2014").setAdditivity(false);
            
            // spring
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework").setAdditivity(false);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework").setLevel(Level.INFO);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework").addAppender(console);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.web").setAdditivity(false);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.web").setLevel(Level.INFO);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.web").addAppender(console);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.security").setAdditivity(false);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.security").setLevel(Level.ERROR);
            Logger.getRootLogger().getLoggerRepository().getLogger("org.springframework.security.web.access").setLevel(Level.OFF);
            

            // The TTCC_CONVERSION_PATTERN contains more info than
            // the pattern we used for the root logger
            /*Logger pkgLogger = Logger.getRootLogger().getLoggerRepository().getLogger("robertmaldon.moneymachine");
            pkgLogger.setLevel(Level.DEBUG);
            pkgLogger.addAppender(new ConsoleAppender(
                   new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));*/

        
            // do souboru
            String filePath = rootDirectory + "/logs/appLog.txt";
            PatternLayout layout = new PatternLayout("%-5p %d %m%n");
            RollingFileAppender appender = new RollingFileAppender(layout, filePath);
            appender.setName("myFileLog");
            appender.setMaxFileSize("1MB");
            appender.activateOptions();
            Logger.getRootLogger().addAppender(appender);
        
        } catch ( IOException e ) {
            Logger.getRootLogger().warn("Chyba při otevírání logu do souboru");
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    //</editor-fold>    

}
