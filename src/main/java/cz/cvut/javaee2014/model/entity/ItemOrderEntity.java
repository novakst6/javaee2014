/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Stenlik
 */
@Entity
public class ItemOrderEntity extends BaseEntity {
    private AccountEntity onAccount;
    private ItemEntity item;
    private Integer quantity;
    private String note;
    private Integer status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOrdered;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDelivered;

    public AccountEntity getOnAccount() {
        return onAccount;
    }

    public void setOnAccount(AccountEntity onAccount) {
        this.onAccount = onAccount;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(Date timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public Date getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(Date timeDelivered) {
        this.timeDelivered = timeDelivered;
    }
    
    
}
