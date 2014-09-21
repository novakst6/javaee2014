/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.repository;

import cz.cvut.javaee2014.model.entity.UserEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stenlik
 */
@Service
public class UserManager extends BaseManager<UserEntity> {
    
    @PostConstruct
    private void init(){
        super.setClass(UserEntity.class);
    }
    
    public UserEntity findByLogin(String login){
        return (UserEntity) em.getCurrentSession().createQuery("SELECT u FROM UserEntity as u WHERE u.login = :login")
                .setParameter("login", login)
                .uniqueResult();
    }
    
}
