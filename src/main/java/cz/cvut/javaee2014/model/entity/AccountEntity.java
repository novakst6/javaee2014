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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Stenlik
 */
@Entity
public class AccountEntity extends BaseEntity {
    private String name;
    @ManyToOne
    private UserEntity openedBy;
    @ManyToMany
    private Set<ItemOrderEntity> orderedItems;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOpened;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(UserEntity openedBy) {
        this.openedBy = openedBy;
    }

    public Set<ItemOrderEntity> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Set<ItemOrderEntity> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Date getTimeOpened() {
        return timeOpened;
    }

    public void setTimeOpened(Date timeOpened) {
        this.timeOpened = timeOpened;
    }
    
}
