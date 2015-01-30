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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Entity Manager
 * 
 * @author Stenlik & Toms
 */

@Transactional
public abstract class BaseManager<E> implements IBaseCRUD<E> {
    
    @Autowired
    protected SessionFactory em;
    
    private Class<E> clazz;
    
    public void setClass(Class<E> clazz){
        this.clazz = clazz;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Create">
    
    @Override
    public E create(E entity) {
        em.getCurrentSession().save(entity);
        return entity;
    }

    @Override
    public void create(List<E> entites) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public E add(E entity) {
        return create(entity);
    }
    
    public void add(List<E> entites) {
        create(entites);
    }
    
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Read">
    
    @Override
    public E findById(Long id) {
        return (E) em.getCurrentSession().get(clazz, id);
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
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Update">
    
    @Override
    public E update(E entity) {
        em.getCurrentSession().update(entity);
        //em.getCurrentSession().flush();
        return entity;
    }
    
    public E edit (E entity) {
        return update(entity);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete">
    
    @Override
    public void delete(E entity) {
        em.getCurrentSession().delete(entity);
    }
    
    @Override
    public void delete(Long id) {
        em.getCurrentSession().delete(
                findById(id)
        );
    }

    @Override
    public void delete(List<E> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Misc..">
    
    @Override
    public void refresh(E entity){
        em.getCurrentSession().refresh(entity);
    }

    @Override
    public E merge(E entity){
        return (E) em.getCurrentSession().merge(entity);
    }
    
    //</editor-fold>
    
}
