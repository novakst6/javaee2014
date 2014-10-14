/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config.hibernate;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Stenlik
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@PropertySource({"classpath:config/jdbc.properties"})
@ComponentScan({"cz.cvut.javaee2014"})
public class PersistenceConfig {

    @Autowired
    private Environment env;

   @Bean
   public SessionFactory sessionFactory() {
       return new LocalSessionFactoryBuilder(dataSource()).scanPackages("cz.cvut.javaee2014.model.entity").addProperties(hibernateProperties()).buildSessionFactory();
   }
   
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

   @Bean
   @Autowired
   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(sessionFactory);
      return txManager;
   }
    
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }

    public Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.query.substitutions", "true 1, false 0");
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }

}
