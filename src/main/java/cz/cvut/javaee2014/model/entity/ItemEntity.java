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
import javax.persistence.ManyToOne;

/**
 *
 * @author Stenlik
 */
@Entity
public class ItemEntity extends BaseEntity {
    private String name;
    private String shortText;
    private String longText;
    @ManyToOne
    ItemCategoryEntity category;
    private Double price;
    @ManyToMany
    private Set<StockItemEntity> madeOf;
    @ManyToMany
    private Set<TerminalTypeEntity> terminalTypes;
    @ManyToOne
    private FileEntity image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public ItemCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(ItemCategoryEntity category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<StockItemEntity> getMadeOf() {
        return madeOf;
    }

    public void setMadeOf(Set<StockItemEntity> madeOf) {
        this.madeOf = madeOf;
    }

    public Set<TerminalTypeEntity> getTerminalTypes() {
        return terminalTypes;
    }

    public void setTerminalTypes(Set<TerminalTypeEntity> terminalTypes) {
        this.terminalTypes = terminalTypes;
    }

    public FileEntity getImage() {
        return image;
    }

    public void setImage(FileEntity image) {
        this.image = image;
    }
    
    
}
