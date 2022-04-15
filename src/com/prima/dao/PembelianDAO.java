/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Barang;
import com.prima.entity.Pembelian;
import com.prima.entity.PembelianDetail;
import com.prima.util.HibernateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author raphael
 */
public class PembelianDAO {

    static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public String save(Pembelian pembelian, List<PembelianDetail> pd) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        Query q = s.createSQLQuery("call sp_addPembelian(:kurs,:id,:hargaTotal)");
        q.setInteger("kurs", pembelian.getKurs());
        q.setInteger("id", pembelian.getSupplier().getIdSupplier());
        q.setInteger("hargaTotal", pembelian.getHargaTotal());
        String i = q.uniqueResult().toString();
        for (PembelianDetail detail : pd) {
            Query q2 = s.createSQLQuery("call sp_addPembelianDetail (:jumlah,:harga,:mataUang,:idBarang,:idPembelian)");
            q2.setInteger("jumlah", detail.getJumlah());
            q2.setDouble("harga", detail.getHarga());
            q2.setBoolean("mataUang", detail.getMataUang());
            q2.setInteger("idBarang", detail.getBarang().getIdBarang());
            q2.setString("idPembelian", i);
            q2.executeUpdate();
        }
        t.commit();
        return i;
    }

    public Pembelian getPembelianById(String id) {
        Session s = sessionFactory.openSession();
        Pembelian p = (Pembelian) s.load(Pembelian.class, id);
        return p;
    }

    public void cancelPembelian(Pembelian pembelian) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        Pembelian p = new Pembelian();
        p.setHargaTotal(pembelian.getHargaTotal());
        p.setIdPembelian(pembelian.getIdPembelian());
        p.setKurs(pembelian.getKurs());
        p.setStatus(false);
        p.setSupplier(pembelian.getSupplier());
        p.setTglBeli(pembelian.getTglBeli());
        s.update(p);
        t.commit();

        Iterator<PembelianDetail> it = pembelian.getPembelianDetails().iterator();
        while (it.hasNext()) {
            new BarangDAO().refreshStockBarang(it.next().getBarang().getIdBarang());
        }
    }

    public Long getTotalBarang(Barang b, Date date) {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select sum(pd.jumlah) from Pembelian p join p.pembelianDetails pd where pd.barang=:barang and p.tglBeli<:tgl and p.status=true");
        q.setEntity("barang", b);
        q.setDate("tgl", date);
        if (q.uniqueResult() == null) {
            return new Long(0);
        } else {
            return (Long) q.uniqueResult();
        }
    }

    public List<Pembelian> getAllPembelian() {
        Session s = sessionFactory.openSession();
        return s.createCriteria(Pembelian.class).addOrder(Order.desc("idPembelian")).list();
    }

    public int getTotalPembelian(int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        c.add(Calendar.DATE, -1);
        System.out.println(c.getTime());
        Date d = c.getTime();
        Session s = sessionFactory.openSession();
        Criteria crit = s.createCriteria(Pembelian.class).add(Restrictions.le("tglBeli", d)).setProjection(Projections.sum("hargaTotal"));
        if (crit.uniqueResult() == null) {
            return 0;
        } else {
            return (Integer) crit.uniqueResult();
        }
    }

  
}
