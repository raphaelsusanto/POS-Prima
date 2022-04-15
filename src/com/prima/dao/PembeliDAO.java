/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Pembeli;
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
public class PembeliDAO {
 static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private Session beginSession() {
        return sessionFactory.openSession();
    }

    public void savePembeli(Pembeli pembeli) {
        Session s = beginSession();
        Transaction tran = s.beginTransaction();
        s.save(pembeli);
        tran.commit();
    }

    public void updatePembeli(Pembeli pembeli) {
        Session s = beginSession();
        Transaction tran = s.beginTransaction();
        s.update(pembeli);
        tran.commit();
    }

    public List<Pembeli> getAllPembeli() {
        Session s = beginSession();
        List<Pembeli> l = s.createCriteria(Pembeli.class).list();
        return l;
    }

    public Pembeli getPembeliById(int id) {
        Session s = beginSession();
        Pembeli pembeli =(Pembeli)s.load(Pembeli.class, id);
        return pembeli;
    }
    
    public List<Pembeli> searchPembeli(String key){
        Session s=beginSession();
        Criteria criteria=s.createCriteria(Pembeli.class);
        Disjunction disjunction=Restrictions.disjunction();
        disjunction.add(Restrictions.like("nama", "%"+key+"%"));
        disjunction.add(Restrictions.like("alamat", "%"+key+"%"));
        disjunction.add(Restrictions.like("telp", "%"+key+"%"));
        disjunction.add(Restrictions.like("email", "%"+key+"%"));
        criteria.add(disjunction);
        return criteria.list();
    }   
}
