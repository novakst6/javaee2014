/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.controller.web;

import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.model.entity.UserRoleEntity;
import cz.cvut.javaee2014.service.repository.UserManager;
import cz.cvut.javaee2014.service.repository.UserRoleManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Stenlik
 */

@Controller
public class RootController {
    
    @Autowired
    private UserRoleManager urm;
    
    @Autowired
    private UserManager um;
    
    @Autowired
    ShaPasswordEncoder spe;
    
    @RequestMapping(value = {"denied.htm"},method = {RequestMethod.GET})
    public void denied(HttpServletResponse response){
        response.setHeader("Content-Type", "text/html");
        response.setHeader("success", "yes");
        
        // odeslĂˇnĂ­ textu
        try {
            PrintWriter writer;
            writer = response.getWriter();
            writer.write("<html><h1>DENIED</h1></html>");
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.ERROR, null, ex);
        }        
    }
    
    @RequestMapping(value = {"login.htm"},method = {RequestMethod.GET})
    public String login(){
        Logger.getLogger(RootController.class.getName()).log(Level.ERROR," -------------------- Logger.getLogger() ");
        LogManager.getLogger(RootController.class.getName()).info(" -------------------- LogManager.getLogger() ");
        Logger.getRootLogger().log(Level.INFO," -------------------- Logger.getRootLogger() ");
        System.out.println(" -------------------- SOUT ");
        return "login";
    }
    
    @RequestMapping(value = {"/"},method = {RequestMethod.GET})
    public String index(){
        UserRoleEntity r1 = new UserRoleEntity();
        r1.setName("ROLE_USER");
        r1.setDescription("Role for regular user of application.");
        urm.add(r1);
        
        UserRoleEntity r2 = new UserRoleEntity();
        r2.setName("ROLE_ADMIN");
        r2.setDescription("Role for administrator of application.");
        urm.add(r2);
        
        UserEntity user1 = new UserEntity();
        user1.setLogin("stenlik");
        user1.setPassword(spe.encodePassword("123456", ""));
        user1.getRoles().add(r1);
        user1.setEmail("stenlik@gmail.com");
        um.add(user1);
        
        UserEntity user2 = new UserEntity();
        user2.setLogin("stenlik2");
        user2.setPassword(spe.encodePassword("123456", ""));
        user2.getRoles().add(r2);
        user2.setEmail("stenlik2@gmail.com");
        um.add(user2);
        return "index";
    }
    
}
