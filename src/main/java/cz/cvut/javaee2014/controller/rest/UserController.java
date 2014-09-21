/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Stenlik
 */

@RestController
@RequestMapping(value = {"resources"})
public class UserController {
    
    @RequestMapping(value = {"index.htm"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(){
        return "TEST";
    }
    
    @RequestMapping(value = {"indexS.htm"}, method = {RequestMethod.GET})
    @ResponseBody
    public String indexSec(){
        return "{test:test:sec}";
    }
    
    @RequestMapping(value = {"login.htm"}, method = {RequestMethod.GET})
    @ResponseBody
    public String login(){
        return "{test:test:login}";
    }
}
