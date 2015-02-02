/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.controller.web;

import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.model.entity.UserRoleEntity;
import cz.cvut.javaee2014.model.entity.form.TestValidate;
import cz.cvut.javaee2014.service.repository.UserManager;
import cz.cvut.javaee2014.service.repository.UserRoleManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    
    //<editor-fold defaultstate="collapsed" desc="Basic pages">
    
    @RequestMapping(value = {"front"},method = {RequestMethod.GET})
    public String front(Model model){
        return "front";
    }   
    
    @RequestMapping(value = {"front/**"},method = {RequestMethod.GET})
    public String frontRedir(){        
        return "redirect:/front";
    } 
    
    @RequestMapping(value = {"back"},method = {RequestMethod.GET})
    public String back(Model model){
        return "back";
    }  
    
    @RequestMapping(value = {"back/**"},method = {RequestMethod.GET})
    public String backRedir(){        
        return "redirect:/back";
    } 
    
    @RequestMapping(value = {"admin"},method = {RequestMethod.GET})
    public String admin(Model model, HttpServletRequest req){
            
        // datum a čas
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);
	model.addAttribute("time", formattedDate );
        
        // ip adresa
        model.addAttribute("ip", req.getRemoteAddr() );
        
        return "admin";
    }   
    
    @RequestMapping(value = {"admin/**"},method = {RequestMethod.GET})
    public String adminRedir(){        
        return "redirect:/admin";
    }   
    
    //</editor-fold>
    
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
    
//    @RequestMapping(value = "valid.htm", method = RequestMethod.POST)
//    public String testValidate(@Valid @ModelAttribute("model") TestValidate model, BindingResult err){
//        if(err.hasErrors()){
//            System.out.println("ERROR");
//        }
//        return null;
//    }
    
    @RequestMapping(value = {"/"},method = {RequestMethod.GET})
    public String index(){
        UserRoleEntity r1 = new UserRoleEntity();
        r1.setName("ROLE_USER");
        r1.setDescription("Role for regular user of application.");
        urm.create(r1);
        
        UserRoleEntity r2 = new UserRoleEntity();
        r2.setName("ROLE_ADMIN");
        r2.setDescription("Role for administrator of application.");
        urm.create(r2);
        
        UserEntity user1 = new UserEntity();
        user1.setLogin("stenlik");
        user1.setPassword(spe.encodePassword("123456", ""));
        user1.getRoles().add(r1);
        user1.setEmail("stenlik@gmail.com");
        um.create(user1);
        
        UserEntity user2 = new UserEntity();
        user2.setLogin("stenlik2");
        user2.setPassword(spe.encodePassword("123456", ""));
        user2.getRoles().add(r2);
        user2.setEmail("stenlik2@gmail.com");
        um.create(user2);
        return "index";
    }
    
    

    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleException(MethodArgumentNotValidException ex) {
        
        RootLogger.getRootLogger().info("BAD REQUEST");
        
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());        
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        
        String resp = "";
        for(String s : errors){
            resp += s + "\n";
        }
        
        Logger.getLogger(RootController.class.getName()).info( "BAD REQUEST, info:" );
        Logger.getLogger(RootController.class.getName()).info( resp );
        
        return resp;
    } */
    
    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleException(Exception ex) {
        
        RootLogger.getRootLogger().info("BAD REQUEST");
        
        
        return "this is bad";
    }*/
    
    
}
