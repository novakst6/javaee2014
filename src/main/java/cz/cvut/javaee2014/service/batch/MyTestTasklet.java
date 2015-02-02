/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.batch;

import cz.cvut.javaee2014.app.config.ApplicationConfig;
import cz.cvut.javaee2014.app.config.ApplicationContextProvider;
import cz.cvut.javaee2014.app.config.WebAppInitializer;
import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.service.repository.UserManager;
import java.util.List;
import javax.batch.api.AbstractBatchlet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Toms
 */
public class MyTestTasklet extends AbstractBatchlet{


    @Override
    public String process() throws Exception {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        UserManager um = (UserManager)context.getBean(UserManager.class);
        Logger.getRootLogger().info("UM "+um);
        List<UserEntity> findAll = um.findAll();
        Logger.getRootLogger().info("UM "+findAll);
        Logger.getRootLogger().info(" ---------- Batch: Hello tasklet!");
        return "OK";
    }

    
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        Logger.getRootLogger().info(" ---------- Batch: Hello tasklet!");
//        return RepeatStatus.FINISHED;
//    }


}