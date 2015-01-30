/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config.hibernate;

import org.hibernate.dialect.MySQL5Dialect;

/**
 *
 * @author Toms
 */
public class MyMySQLDialect extends MySQL5Dialect{
    
    @Override
    public String getTableTypeString() {
        return " DEFAULT CHARSET=utf8";
    }
}
