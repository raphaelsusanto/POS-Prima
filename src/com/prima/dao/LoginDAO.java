/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Pengguna;
import com.prima.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author raphael
 */
public class LoginDAO {

    static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static String username;
    public static short role;

    public Boolean checkLogin(String nama, String password) {
        Session s = sessionFactory.openSession();
        Query q = s.createSQLQuery("select * from pengguna where nama_pengguna=:username and password=md5(:password)");
        q.setString("username", nama);
        q.setString("password", password);
        if (q.uniqueResult() != null) {
            username = nama;
            role = ((Pengguna) s.load(Pengguna.class, username)).getRole();
        }

        return q.uniqueResult() != null;
    }

    public void changePassword(String pass) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        Query q = s.createSQLQuery("update pengguna set password=md5(:pass) where nama_pengguna=:username");
        q.setString("username", username);
        q.setString("pass", pass);
        q.executeUpdate();
        t.commit();
    }
}
