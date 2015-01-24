/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Stenlik
 */
@Entity
public class StockCategoryEntity extends BaseEntity {
    String name;
    String memo;
    @ManyToOne
    StockCategoryEntity parent;
    @OneToMany(mappedBy = "parent")
    Set<StockCategoryEntity> childern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public StockCategoryEntity getParent() {
        return parent;
    }

    public void setParent(StockCategoryEntity parent) {
        this.parent = parent;
    }

    public Set<StockCategoryEntity> getChildern() {
        return childern;
    }
    
    
}
