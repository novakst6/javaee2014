/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.repository.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stenlik
 * @param <E>
 */
public interface IBaseCRUD<E> extends Serializable{
    public void add(E entity);
    public void add(List<E> entites);
    public void edit(E entity);
    public void delete(E entity);
    public void delete(List<E> entities);
    public E findById(Long id);
    public List<E> findById(List<Long> ids);
    public List<E> find(String query, String[]... parameters);  
    public List<E> findAll();
    public void refresh(E entity);
    public E merge(E entity);
    
}
