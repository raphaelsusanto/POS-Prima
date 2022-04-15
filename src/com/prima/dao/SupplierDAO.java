/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Supplier;
import com.prima.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author raphael
 */
public class SupplierDAO {
 static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private Session beginSession() {
        return sessionFactory.openSession();
    }

    public void saveSupplier(Supplier supplier) {
        Session s = beginSession();
        Transaction tran = s.beginTransaction();
        s.save(supplier);
        tran.commit();
    }

    public void updateSupplier(Supplier supplier) {
        Session s = beginSession();
        Transaction tran = s.beginTransaction();
        s.update(supplier);
        tran.commit();
    }

    public List<Supplier> getAllSupplier() {
        Session s = beginSession();
        List<Supplier> l = s.createCriteria(Supplier.class).list();
        return l;
    }

    public Supplier getSupplierById(int id) {
        Session s = beginSession();
        Supplier supplier =(Supplier)s.load(Supplier.class, id);
        return supplier;
    }
    
    public List<Supplier> searchSupplier(String key){
        Session s=beginSession();
        Criteria criteria=s.createCriteria(Supplier.class);
        Disjunction disjunction=Restrictions.disjunction();
        disjunction.add(Restrictions.like("nama", "%"+key+"%"));
        disjunction.add(Restrictions.like("alamat", "%"+key+"%"));
        disjunction.add(Restrictions.like("telp", "%"+key+"%"));
        disjunction.add(Restrictions.like("email", "%"+key+"%"));
        criteria.add(disjunction);
        return criteria.list();
    }   
}
