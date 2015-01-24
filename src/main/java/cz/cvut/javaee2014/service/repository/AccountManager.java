/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.repository;

import cz.cvut.javaee2014.model.entity.AccountEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stenlik
 */
@Service
public class AccountManager extends BaseManager<AccountEntity> {
    
    @PostConstruct
    public void init(){
        super.setClass(AccountEntity.class);
    }
}
