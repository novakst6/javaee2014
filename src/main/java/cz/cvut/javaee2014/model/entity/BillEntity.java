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
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Stenlik
 */
@Entity
public class BillEntity extends BaseEntity {
    private UserEntity createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @ManyToMany
    private Set<BilledItemEntity> items;
    private Double totalPriceWithoutVAT; //TODO ?? Configure VAT ... might own entity
    private Double totalEndPrice;

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<BilledItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<BilledItemEntity> items) {
        this.items = items;
    }

    public Double getTotalPriceWithoutVAT() {
        return totalPriceWithoutVAT;
    }

    public void setTotalPriceWithoutVAT(Double totalPriceWithoutVAT) {
        this.totalPriceWithoutVAT = totalPriceWithoutVAT;
    }

    public Double getTotalEndPrice() {
        return totalEndPrice;
    }

    public void setTotalEndPrice(Double totalEndPrice) {
        this.totalEndPrice = totalEndPrice;
    }

    
}
