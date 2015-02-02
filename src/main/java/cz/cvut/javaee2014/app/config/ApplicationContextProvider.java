/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Stenlik
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;
 
    public static ApplicationContext getApplicationContext() {
        return context;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }
}
