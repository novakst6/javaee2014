/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.repository.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Base CRUD Intarface
 * - create a update vrací entitu kvůli restu, 
 *   aby se hned dalo ověřit ID objektu
 * 
 * @author Stenlik & Toms
 * @param <E>
 */
public interface IBaseCRUD<E> extends Serializable{
    
    // create
    public E create(E entity);
    public void create(List<E> entites);
    
    // read
    public E findById(Long id);
    public List<E> findById(List<Long> ids);
    public List<E> find(String query, String[]... parameters);  
    public List<E> findAll();
    
    // update
    public E update(E entity);
    
    // delete
    public void delete(E entity);
    public void delete(Long id);
    public void delete(List<E> entities);

    // & the company..
    public void refresh(E entity);
    public E merge(E entity);
    
}
