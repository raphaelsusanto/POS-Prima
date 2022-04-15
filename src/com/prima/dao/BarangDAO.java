/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Barang;
import com.prima.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author raphael
 */
public class BarangDAO {

    static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private Session beginSession() {
        return sessionFactory.openSession();
    }

    public void saveBarang(Barang barang) {
        Session s = beginSession();
        Transaction tran = s.beginTransaction();
        s.save(barang);
        tran.commit();
        // s.close();
    }

    public void updateBarang(Barang barang) {
        Session s = beginSession();
        //s.merge("");
        Transaction tran = s.beginTransaction();
        s.update(barang);
        tran.commit();
        //s.close();
    }

    public List<Barang> getAllBarang() {
        Session s = beginSession();
        List<Barang> l = s.createCriteria(Barang.class).list();
        return l;
    }

    public Barang getBarangById(int id) {
        Session s = beginSession();
        String hql = "from Barang b where b.idBarang=" + id;
        Barang b = (Barang) s.createQuery(hql).uniqueResult();
        return b;
    }

    public List<Barang> searchBarang(String key) {
        Session s = beginSession();
        Criteria criteria = s.createCriteria(Barang.class);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("nama", "%" + key + "%"));
        //disjunction.add(Restrictions.like("", s))
        criteria.add(disjunction);
        return criteria.list();
    }

    public void refreshStockBarang(int idBarang) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        Query q = s.createSQLQuery("call sp_refreshStockBarang(:idBarang)");
        q.setInteger("idBarang", idBarang);
        q.executeUpdate();
        t.commit();
    }

    public Double getTotalBarang(Barang barang, Date date) {
        Long x = new PembelianDAO().getTotalBarang(barang, date);
        Double y = new TransaksiDAO().getTotalBarang(barang, date);
        return x - y;
    }

    public List getDetailStock(Barang b) {
        Session s = sessionFactory.openSession();
        SQLQuery query = s.createSQLQuery("select * from "
                + "("
                + "select b.nama as 'nama barang',p.tgl_beli,s.nama,pd.jumlah as 'jumlah beli',0 as 'jumlah jual' "
                + "from pembelian_detail pd "
                + "join barang b on "
                + "b.id_barang=pd.id_barang "
                + "join pembelian p on p.id_pembelian=pd.id_pembelian "
                + "join supplier s on s.id_supplier=p.id_supplier "
                + "where b.id_barang=:id and p.status=1 "
                + "union "
                + "select b.nama as 'nama barang',t.tgl_jual,p.nama,0 as 'jumlah beli',dt.jumlah as 'jumlah jual' "
                + "from detail_transaksi dt "
                + "join barang b on b.id_barang=dt.id_barang "
                + "join transaksi t on t.id_transaksi=dt.id_transaksi "
                + "join pembeli p on p.id_pembeli=t.id_pembeli "
                + "where b.id_barang=:id and t.status=1 "
                + ")as tbl "
                + "order by tgl_beli ");
        query.setInteger("id", b.getIdBarang());

        return query.list();
    }

   
}
