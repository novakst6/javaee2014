/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.model.entity;

import cz.cvut.javaee2014.model.entity.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author Stenlik
 */
@Entity
public class ImageEntity extends BaseEntity {
    
    private String name;
    @OneToOne
    private FileEntity small;
    @OneToOne
    private FileEntity medium;
    @OneToOne
    private FileEntity large;
    @OneToOne
    private FileEntity original;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileEntity getSmall() {
        return small;
    }

    public FileEntity getMedium() {
        return medium;
    }

    public void setMedium(FileEntity medium) {
        this.medium = medium;
    }

    public FileEntity getLarge() {
        return large;
    }

    public void setLarge(FileEntity large) {
        this.large = large;
    }

    public FileEntity getOriginal() {
        return original;
    }

    public void setOriginal(FileEntity original) {
        this.original = original;
    }

    public void setSmall(FileEntity small) {
        this.small = small;
    }

}
