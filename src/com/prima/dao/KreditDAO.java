/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Kredit;
import com.prima.entity.Pembeli;
import com.prima.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author raphael
 */
public class KreditDAO {
    static final SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
    public void save(Kredit kredit){
        Session s= sessionFactory.openSession();
        Transaction transaction= s.beginTransaction();
        s.save(kredit);
        transaction.commit();
    }
    
    public void update(Kredit kredit){
        Session s=sessionFactory.openSession();
        Transaction t=s.beginTransaction();
        s.update(kredit);
        t.commit();
    }
    public List<Kredit> getAllKreditByPembeli(Pembeli pembeli){
        Session s=sessionFactory.openSession();
        Criteria c=s.createCriteria(Kredit.class).add(Restrictions.eq("pembeli", pembeli));
        return c.list();
    }
    public Kredit getKreditById(int id){
        Session s=sessionFactory.openSession();
        return(Kredit) s.load(Kredit.class, id);
    }
  
}
