/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.controller.rest.base;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
 
/**
*
* @author Toms
*/
public abstract class BaseRESTController<T extends BaseEntity> {

    private Logger logger = Logger.getLogger(BaseRESTController.class);

    private BaseManager<T> repo;


    public BaseRESTController(BaseManager<T> repo) {
        this.repo = repo;
    }

    @RequestMapping
    public @ResponseBody List<T> listAll() {
        return this.repo.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Map<String, Object> create(@RequestBody T json) {
        //logger.debug("create() with body {} of type {}", json, json.getClass());

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

    @RequestMapping(value="/{id}", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Map<String, Object> update(@PathVariable Long id, @RequestBody T json) {
        //logger.debug("update() of id#{} with body {}", id, json);
        //logger.debug("T json is of type {}", json.getClass());

        T entity = this.repo.findById(id);
        try {
            BeanUtils.copyProperties(entity, json);
        }
        catch (Exception e) {
            logger.warn("while copying properties", e);
            //throw Throwables.propagate(e);
        }

        //logger.debug("merged entity: {}", entity);

        T updated = this.repo.update(entity);
        //logger.debug("updated enitity: {}", updated);

        Map<String, Object> m = new HashMap();
        m.put("success", true);
        m.put("id", id);
        m.put("updated", updated);
        return m;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(@PathVariable Long id) {
        this.repo.delete(id);
        Map<String, Object> m = new HashMap();
        m.put("success", true);
        return m;
    }
}
