/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Cicilan;
import com.prima.entity.Kredit;
import com.prima.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author raphael
 */
public class CicilanDAO {

    static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(Cicilan cicilan) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        s.save(cicilan);
        t.commit();
    }
    
    public List<Cicilan> getCicilanByKredit(Kredit kredit){
        Session s=sessionFactory.openSession();
        return s.createCriteria(Cicilan.class).add(Restrictions.eq("kredit", kredit)).list();
    }

    public double getTotalBayarById(Kredit kredit) {
        Session s=sessionFactory.openSession();
        Query q=s.createQuery("select sum(c.jumlahBayar) from Cicilan c where c.kredit=:kredit");
        q.setEntity("kredit", kredit);
        if ((Double)q.uniqueResult()==null) {
            return 0;
        }else{
            return ((Double)q.uniqueResult());
        }
       
    }

    
}
