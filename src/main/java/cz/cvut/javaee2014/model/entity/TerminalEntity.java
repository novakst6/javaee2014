/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Stenlik
 */
@Entity
public class TerminalEntity extends BaseEntity{
    private String name;
    @ManyToOne
    private TerminalTypeEntity type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TerminalTypeEntity getType() {
        return type;
    }

    public void setType(TerminalTypeEntity type) {
        this.type = type;
    }
    
    
}
