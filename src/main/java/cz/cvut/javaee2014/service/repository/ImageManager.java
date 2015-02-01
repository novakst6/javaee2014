/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.repository;

import cz.cvut.javaee2014.model.entity.ImageEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stenlik
 */
@Service
public class ImageManager extends BaseManager<ImageEntity> {
    
    @PostConstruct
    public void init(){
        super.setClass(ImageEntity.class);
    }
}
