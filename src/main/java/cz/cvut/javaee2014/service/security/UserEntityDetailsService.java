/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.security;

import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.service.repository.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stenlik
 */
@Service
public class UserEntityDetailsService implements UserDetailsService, ClientDetailsService{

    @Autowired
    private UserManager um;
    @Autowired
    private AuthenticateAssembler aa;
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {

        System.out.println("SECURITY> Searching user [ " + string + " ]");

        UserEntity user = um.findByLogin(string);
        System.out.println("SECURITY> USER " + user);
        try {
            System.out.println("SECURITY> Found user [ " + user.getEmail() + " ]");
        } catch (Exception e) {
            System.out.println("SECURITY> Can't found user [ " + string + " ]");
        }
        if (user == null) {
            throw new UsernameNotFoundException("SECURITY> Can't find user [ " + string + " ]");
        }
 
        return aa.buildUserFromUserEntity(user);

    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        System.out.println("FINDING CLIENT ID "+clientId);
        return null;
    }
    
}
