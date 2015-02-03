/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.batch;

import cz.cvut.javaee2014.app.config.ApplicationConfig;
import cz.cvut.javaee2014.app.config.ApplicationContextProvider;
import cz.cvut.javaee2014.app.config.WebAppInitializer;
import cz.cvut.javaee2014.model.entity.FileEntity;
import cz.cvut.javaee2014.model.entity.ImageEntity;
import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.service.repository.FileManager;
import cz.cvut.javaee2014.service.repository.ImageManager;
import cz.cvut.javaee2014.service.repository.UserManager;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.batch.api.AbstractBatchlet;
import org.apache.log4j.Logger;
import org.springframework.batch.core.scope.context.JobContext;
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
        
        
        // kontexty
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();        
        
        //
        //UserManager um = (UserManager)context.getBean(UserManager.class);
        //Logger.getRootLogger().info("UM "+um);
        //List<UserEntity> findAll = um.findAll();
        //Logger.getRootLogger().info("UM "+findAll);
        
        // properties z conf. souborů
        String small = context.getEnvironment().getProperty("images.small");
        int smallW = Integer.parseInt(small.split("x")[0]);
        int smallH = Integer.parseInt(small.split("x")[1]);
        String medium = context.getEnvironment().getProperty("images.medium");
        int mediumW = Integer.parseInt(medium.split("x")[0]);
        int mediumH = Integer.parseInt(medium.split("x")[1]);
        String large = context.getEnvironment().getProperty("images.large");
        int largeW = Integer.parseInt(large.split("x")[0]);
        int largeH = Integer.parseInt(large.split("x")[1]);
        String absDir = context.getEnvironment().getProperty("filesys.root") + "/";
        String relDir = context.getEnvironment().getProperty("filesys.upload") + "/";
        
        
        // projedeme obrázky
        ImageManager imgManager = (ImageManager)context.getBean(ImageManager.class);
        FileManager fileManager = (FileManager)context.getBean(FileManager.class);
        for(ImageEntity image : imgManager.findAll()){
            boolean updated = false;
            if(image.getSmall() == null){
                image.setSmall(createFile(image.getOriginal(), absDir, relDir, "_[small]", smallW, smallH));
                fileManager.create(image.getSmall());
                updated = true;
            }
            if(image.getMedium() == null){
                image.setMedium(createFile(image.getOriginal(), absDir, relDir, "_[medium]", mediumW, mediumH));
                fileManager.create(image.getMedium());
                updated = true;
            }
            if(image.getLarge() == null){
                image.setLarge(createFile(image.getOriginal(), absDir, relDir, "_[large]", largeW, largeH));
                fileManager.create(image.getLarge());
                updated = true;
            }
            if(updated){
                imgManager.update(image);
            }
        }
        
        
        Logger.getRootLogger().info(" ---------- Batch: Hello tasklet!");
        return "OK";
    }
    
    private FileEntity createFile(FileEntity originalFileEntity, String absDir, String relDir, String postFix, int width, int height){
        
        //
        FileEntity newFileEntity = new FileEntity();
        
        // název a cesta
        String name = originalFileEntity.getFileName();
        int pos = name.lastIndexOf(".");        
        String preName = name.substring(0, pos) + postFix;
        String ext = name.substring(pos);
        name = preName + ext;
        String relPath = relDir + name;
        
        // do entity
        newFileEntity.setFileName(name);
        newFileEntity.setFilePath(relPath);
        
        // konverze
        ImageResizer.resizeToFile(
                new File(absDir+originalFileEntity.getFilePath()), 
                new File(absDir+newFileEntity.getFilePath()),
                width, height);
        
        // 
        return newFileEntity;
    }

    
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        Logger.getRootLogger().info(" ---------- Batch: Hello tasklet!");
//        return RepeatStatus.FINISHED;
//    }


}