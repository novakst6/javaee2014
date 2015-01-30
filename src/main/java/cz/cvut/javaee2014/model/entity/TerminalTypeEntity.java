/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import javax.persistence.Entity;

/**
 *
 * @author Stenlik
 */
@Entity
public class TerminalTypeEntity extends BaseEntity{
    private String name;
    private Integer screenType;
    private Boolean isStacionary;
    private Boolean isVendingPost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScreenType() {
        return screenType;
    }

    public void setScreenType(Integer screenType) {
        this.screenType = screenType;
    }

    public Boolean getIsStacionary() {
        return isStacionary;
    }

    public void setIsStacionary(Boolean isStacionary) {
        this.isStacionary = isStacionary;
    }

    public Boolean getIsVendingPost() {
        return isVendingPost;
    }

    public void setIsVendingPost(Boolean isVendingPost) {
        this.isVendingPost = isVendingPost;
    } 
    
}
