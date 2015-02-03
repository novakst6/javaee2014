/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Stenlik
 */
@Entity
public class ItemOrderEntity extends BaseEntity {
    //private AccountEntity onAccount;
    @ManyToOne
    private ItemEntity item;
    //private Integer quantity;
    private String note;
    private Integer status;
    private String account;
    /*@Temporal(TemporalType.TIMESTAMP)
    private Date timeOrdered;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDelivered;*/

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    
}
