/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.service.repository.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stenlik
 */

public abstract class BaseManager<E> implements IBaseCRUD<E> {
    
    @Autowired
    protected SessionFactory em;
    
    private Class<E> clazz;
    
    public void setClass(Class<E> clazz){
        this.clazz = clazz;
    }
 
    @Override
    public void add(E entity) {
        em.getCurrentSession().save(entity);
    }

    @Override
    public void add(List<E> entites) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(E entity) {
        em.getCurrentSession().update(entity);
    }

    @Override
    public void delete(E entity) {
        em.getCurrentSession().delete(entity);
    }

    @Override
    public void delete(List<E> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E findById(Long id) {
        return (E) em.getCurrentSession().load(clazz, id);
    }

    @Override
    public List<E> findById(List<Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> find(String query, String[]... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> findAll() {
        return em.getCurrentSession().createQuery("SELECT e FROM "+clazz.getSimpleName()+" AS e").list();
    }
    
    @Override
    public void refresh(E entity){
        em.getCurrentSession().refresh(entity);
    }

    @Override
    public E merge(E entity){
        return (E) em.getCurrentSession().merge(entity);
    }
    
    
    
    
    
}
