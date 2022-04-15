/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;


import com.prima.dao.CicilanDAO;
import com.prima.dao.KreditDAO;
import com.prima.dao.TransaksiDAO;
import com.prima.entity.Cicilan;
import com.prima.entity.DetailTransaksi;
import com.prima.entity.Kredit;
import com.prima.entity.Transaksi;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author raphael
 */
public class TransaksiService {
    public String saveTransaksi(Transaksi transaksi,List<DetailTransaksi> dt,Boolean suratJalan,String kendaraan,String noKendaraan){
        TransaksiDAO transaksiDAO= new TransaksiDAO();
        String idTransaksi=transaksiDAO.save(transaksi,dt, suratJalan, kendaraan, noKendaraan);
      
        return idTransaksi;
    }
    public String saveTransaksi(Transaksi transaksi,List<DetailTransaksi> detailTransaksi,Kredit kredit,double dp,Boolean suratJalan,String kendaraan,String noKendaraan){
        TransaksiDAO transaksiDAO= new TransaksiDAO();
       
        KreditDAO kreditDAO= new KreditDAO();
        CicilanDAO cicilanDAO= new CicilanDAO();
        
        String idTransaksi=transaksiDAO.save(transaksi,detailTransaksi, suratJalan, kendaraan, noKendaraan);
        transaksi.setIdTransaksi(idTransaksi);
        kredit.setStatus(false);
        kredit.setTransaksi(transaksi);
        kreditDAO.save(kredit);
        if (dp!=0) {
            cicilanDAO.save(new Cicilan(kredit, dp, new Date(),"Cash","-"));
        }
        
        return idTransaksi;
    }
}
