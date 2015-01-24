/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Stenlik
 */
@Entity
public class StockItemEntity extends BaseEntity {
    String name;
    @ManyToMany
    Set<StockCategoryEntity> category;
    @ManyToMany
    Set<PriceEntity> buyPrice;
    Double onStockQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StockCategoryEntity> getCategory() {
        return category;
    }

    public void setCategory(Set<StockCategoryEntity> category) {
        this.category = category;
    }

    public Set<PriceEntity> getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Set<PriceEntity> buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getOnStockQuantity() {
        return onStockQuantity;
    }

    public void setOnStockQuantity(Double onStockQuantity) {
        this.onStockQuantity = onStockQuantity;
    }
    
    
}
