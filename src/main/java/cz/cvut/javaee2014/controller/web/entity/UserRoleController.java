/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.controller.web.entity;

import cz.cvut.javaee2014.model.entity.UserRoleEntity;
import cz.cvut.javaee2014.model.form.UserRoleForm;
import cz.cvut.javaee2014.service.repository.UserRoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Stenlik
 */

@Controller
@RequestMapping(value = {"entity"})
public class UserRoleController {
    
    @Autowired
    private UserRoleManager urm;
    
    @RequestMapping(value = {"role.htm"},method = {RequestMethod.GET})
    public String roleGET(ModelMap m, UserRoleForm formModel){
        m.addAttribute("roles", urm.findAll());
        m.addAttribute("formModel", formModel);
        return "entity/user_role/role";
    }
    
    @RequestMapping(value = {"role.htm"},method = {RequestMethod.POST})
    public String rolePOST(UserRoleForm formModel, ModelMap m){
        UserRoleEntity ure = new UserRoleEntity();
        ure.setName(formModel.getName());
        urm.create(ure);
        return "redirect:role.htm";
    }
    
    @RequestMapping(value = {"index.htm"},method = {RequestMethod.GET})
    public String indexGET(ModelMap m){
        m.addAttribute("roles", urm.findAll());
        return "entity/user_role/index";
    }
    
    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.GET})
    public String indexDetailGET(ModelMap m,@RequestParam(value = "id") Long id){
        UserRoleEntity role = urm.findById(id);
        m.addAttribute("description", role.getDescription());
        return "entity/user_role/detail";
    }
}
