/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.dao;

import com.prima.entity.Barang;
import com.prima.entity.DetailTransaksi;
import com.prima.entity.Pembeli;
import com.prima.entity.Transaksi;
import com.prima.util.HibernateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
public class TransaksiDAO {

    static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public String save(Transaksi transaksi, List<DetailTransaksi> dt, Boolean suratJalan, String kendaraan, String noKendaraan) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        String a = "";
        try {

            Query q = s.createSQLQuery("call sp_addTransaksi(:kurs,:harga,:hargaUSD,:mataUang,:idPembeli)");
            q.setInteger("kurs", transaksi.getKurs());
            q.setInteger("harga", transaksi.getHargaTotal());
            q.setDouble("hargaUSD", transaksi.getHargaTotalUsd());
            q.setBoolean("mataUang", transaksi.isMata());
            q.setInteger("idPembeli", transaksi.getPembeli().getIdPembeli());
            a = q.uniqueResult().toString();

            for (DetailTransaksi it : dt) {
                Query q2 = s.createSQLQuery("call sp_addDetailTransaksi(:jumlah,:harga,:mataUang,:idBarang,:idTransaksi)");
                q2.setDouble("jumlah", it.getJumlah());
                q2.setDouble("harga", it.getHarga());
                q2.setBoolean("mataUang", it.getMataUang());
                q2.setInteger("idBarang", it.getBarang().getIdBarang());
                q2.setString("idTransaksi", a);
                q2.executeUpdate();
            }
            if (suratJalan) {
                Query q3 = s.createSQLQuery("call sp_addSuratJalan(:kendaraan,:no,:idTransaksi)");
                q3.setString("kendaraan", kendaraan);
                q3.setString("no", noKendaraan);
                q3.setString("idTransaksi", a);
                q3.executeUpdate();
            }

            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }

        return a;
    }

    public List<Transaksi> getAllTransaksi() {
        Session s = sessionFactory.openSession();
        return s.createCriteria(Transaksi.class).addOrder(Order.desc("idTransaksi")).list();
    }

    public Transaksi getTransaksiById(String id) {
        Session s = sessionFactory.openSession();
        return (Transaksi) s.createCriteria(Transaksi.class).add(Restrictions.eq("idTransaksi", id)).uniqueResult();
    }

    public List<Transaksi> getTransaksiByPembeli(Pembeli pembeli) {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select t from Transaksi t where t.pembeli.idPembeli=:pembeli order by t.idTransaksi desc");
        q.setInteger("pembeli", pembeli.getIdPembeli());
        return q.list();
    }

    public List<DetailTransaksi> getDetailTransaksiByTransaksi(Transaksi transaksi) {
        Session s = sessionFactory.openSession();
        Criteria c = s.createCriteria(DetailTransaksi.class).add(Restrictions.eq("transaksi", transaksi));
        return c.list();
    }

    public List<Transaksi> getTransaksiKredit() {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select t from Transaksi t join t.kredits k where k.status=0 and t.status=1 order by t.idTransaksi desc");
        return q.list();
    }

    public List<Transaksi> getTransaksiKreditbyPembeli(Pembeli pembeli) {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select t from Transaksi t join t.kredits k where k.status=0 and t.status=1 and t.pembeli=:pembeli order by t.idTransaksi desc");
        q.setEntity("pembeli", pembeli);
        return q.list();
    }

    public List<Object> tes() {
        Session s = sessionFactory.openSession();
        Query q = s.createSQLQuery("select p.nama,sum(t.harga_total),sum(c.jumlah_bayar),sum(t.harga_total)-sum(c.jumlah_bayar)  from pembeli p "
                + "join transaksi t on p.id_pembeli=t.id_pembeli "
                + "join detail_transaksi dt on dt.id_transaksi=t.id_transaksi "
                + "join kredit k on k.id_transaksi=t.id_transaksi "
                + "join cicilan c on c.id_kredit=c.id_kredit "
                + "group by p.nama");
        return q.list();
    }

    public void cancelTransaksi(Transaksi transaksi) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        Transaksi tran = new Transaksi();
        tran.setIdTransaksi(transaksi.getIdTransaksi());
        tran.setStatus(false);
        tran.setHargaTotal(transaksi.getHargaTotal());
        tran.setKurs(transaksi.getKurs());
        tran.setTglJual(transaksi.getTglJual());
        s.update(tran);
        t.commit();

        Iterator<DetailTransaksi> it = transaksi.getDetailTransaksis().iterator();
        while (it.hasNext()) {
            Barang b = it.next().getBarang();
            new BarangDAO().refreshStockBarang(b.getIdBarang());
        }

    }

    public double getTotalBarang(Barang barang, Date date) {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select sum(dt.jumlah) from Transaksi t join t.detailTransaksis dt where dt.barang=:barang and t.tglJual<=:tgl and t.status=true");
        q.setEntity("barang", barang);
        q.setDate("tgl", date);
        if (q.uniqueResult() == null) {
            return 0;
        } else {
            return (Double) q.uniqueResult();
        }
    }

    public List<Object> getDetailTransaksi(int month, int year) {
        Session s = sessionFactory.openSession();
        SQLQuery q = s.createSQLQuery("select * from ( "
                + "select convert(p.id_pembelian,char(12)),p.tgl_beli,s.nama,harga_total as 'Debet',0 as 'Kredit' "
                + "from pembelian p "
                + "join supplier s on s.id_supplier=p.id_supplier "
                + "union "
                + "select convert(t.id_transaksi,char(12)),t.tgl_jual,p.nama,0 as 'Debet',harga_total as 'Kredit' "
                + "from transaksi t "
                + "join pembeli p on t.id_pembeli=p.id_pembeli "
                + ") as tbl "
                + "where month(tgl_beli)=:month and year(tgl_beli)=:year "
                + "order by tgl_beli");
        q.setInteger("month", month + 1);
        q.setInteger("year", year);

        return q.list();
    }

    public int getTotalTransaksi(int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        c.add(Calendar.DATE, -1);
        System.out.println(c.getTime());
        Date d = c.getTime();
        Session s = sessionFactory.openSession();
        Criteria crit = s.createCriteria(Transaksi.class).add(Restrictions.le("tglJual", d)).setProjection(Projections.sum("hargaTotal"));
        if (crit.uniqueResult() == null) {
            return 0;
        } else {
            return (Integer) crit.uniqueResult();
        }
    }

    public List<Transaksi> search(String key) {
        Session s = sessionFactory.openSession();
        Query q = s.createQuery("select t from Transaksi t join t.pembeli p where p.nama like :nama");
        q.setString("nama", "%" + key + "%");
        return q.list();
    }
}
