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
    @ManyToOne
    private TerminalTypeEntity terminalType;
    @ManyToOne
    private ImageEntity image;

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

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    public TerminalTypeEntity getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalTypeEntity terminalType) {
        this.terminalType = terminalType;
    }
    
    
}
