/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Stenlik
 */
@Entity
public class UserEntity extends BaseEntity {
    
    private String login;
    private String password;
    @NotEmpty
    private String email;
    @ManyToMany
    private Set<UserOauthCredentialsEntity> oauth = new HashSet<UserOauthCredentialsEntity>();
    @ManyToMany
    private Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserOauthCredentialsEntity> getOauth() {
        return oauth;
    }

    public void setOauth(Set<UserOauthCredentialsEntity> oauth) {
        this.oauth = oauth;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
    }



    
    
    
}
