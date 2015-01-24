/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Stenlik
 */
@Entity
public class BilledItemEntity extends BaseEntity {
    
    private ItemEntity parentItem;
    private Integer quantity;
    @ManyToOne
    private PriceEntity unitPrice;
    private Double totalPrice;

    public ItemEntity getParentItem() {
        return parentItem;
    }

    public void setParentItem(ItemEntity parentItem) {
        this.parentItem = parentItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PriceEntity getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(PriceEntity unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
