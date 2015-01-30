/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.controller.rest.base;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
 
/**
*
* @author Toms
* @param <T> Třída datové Entity
*/
public abstract class BaseRESTController<T extends BaseEntity> {

    private final Logger logger = Logger.getLogger(BaseRESTController.class);

    private final BaseManager<T> repo;


    public BaseRESTController(BaseManager<T> repo) {
        this.repo = repo;
    }

    @RequestMapping
    public @ResponseBody List<T> listAll() {
        return this.repo.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Map<String, Object> create(@RequestBody T json) {
        
        logger.info("Incoming REST: " + json.getClass()); 
        logger.info(json.toString());
        
        logger.info("Entity NEW: ");
        logger.info(ReflectionToStringBuilder.toString(json, ToStringStyle.MULTI_LINE_STYLE));
        
        T created = this.repo.create(json);

        Map<String, Object> m = new HashMap();
        m.put("success", true);
        m.put("id", json.getId());
        m.put("created", created);
        return m;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public @ResponseBody T get(@PathVariable Long id) {
        return this.repo.findById(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody T update(@PathVariable Long id, @RequestBody T json) {
        
        logger.info("Incoming REST Update: " + json.getClass()); 

        T entity = this.repo.findById(id);        
        
        logger.info("Entity to update: " + entity.toString()); 
        logger.info("Entity ID: " + entity.getId());
        
        logger.info("Entity OLD: ");
        logger.info(ReflectionToStringBuilder.toString(entity, ToStringStyle.MULTI_LINE_STYLE));
        logger.info("Entity JSON: ");
        logger.info(ReflectionToStringBuilder.toString(json, ToStringStyle.MULTI_LINE_STYLE));

        try{
            BeanUtils.copyProperties(json, entity);
            //((TerminalTypeEntity) entity).setName("Xxxxx");
        }catch(Exception e){
            logger.error("Error while copying properties", e);
        }
        logger.info("Entity copy finished?"); 
        logger.info("Entity after copy: ");
        logger.info(ReflectionToStringBuilder.toString(entity, ToStringStyle.MULTI_LINE_STYLE));
        
        this.repo.update(entity);
        logger.info("Entity update"); 
        logger.info("Entity after update: ");
        logger.info(ReflectionToStringBuilder.toString(entity, ToStringStyle.MULTI_LINE_STYLE));
        
        return entity;

        /*Map<String, Object> m = new HashMap();
        m.put("success", true);
        m.put("id", id);
        m.put("updated", entity);
        return m;*/
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(@PathVariable Long id) {
        this.repo.delete(id);
        Map<String, Object> m = new HashMap();
        m.put("success", true);
        return m;
    }
    
    @ExceptionHandler
    public void badRequestHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        logger.error("Exception for REST URL: " + request.getRequestURL());
        logger.error("Exception Raised: " + ex);
        
        try{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write("exception: " + ex);
            response.getWriter().close();
            
        }catch(IOException e){
            logger.error("Exception during exception handling :-)");
        }
    }   
}
