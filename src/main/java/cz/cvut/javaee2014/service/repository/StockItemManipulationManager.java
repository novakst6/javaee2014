/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.repository;

import cz.cvut.javaee2014.model.entity.StockItemManipulationEntity;
import cz.cvut.javaee2014.service.repository.dao.BaseManager;
import javax.annotation.PostConstruct;

/**
 *
 * @author Stenlik
 */
public class StockItemManipulationManager extends BaseManager<StockItemManipulationEntity> {
        @PostConstruct
    public void init(){
        super.setClass(StockItemManipulationEntity.class);
    }
}
