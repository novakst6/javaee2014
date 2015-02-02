/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.model.entity.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Stenlik
 */
public class TestValidate {
    @NotEmpty
    String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
    
    
}
