/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.security;

import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.model.entity.UserRoleEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stenlik
 */
@Service
public class AuthenticateAssembler {
    
    @Transactional(readOnly = true)
    public User buildUserFromUserEntity(UserEntity userEntity) {

        String username = userEntity.getLogin();
        String password = userEntity.getPassword();
//        System.out.println("SECURITY> Username [" + username + "]");
//        System.out.println("SECURITY> Password [" + password + "]");

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean accoountNonLocked = true;

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<UserRoleEntity> roles = userEntity.getRoles();
        for (UserRoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        User user = new User(username, password, enabled, accountNonExpired, accountNonExpired, accoountNonLocked, authorities);

        return user;
    }
}
