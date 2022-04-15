package com.prima.entity;
// Generated Apr 29, 2012 9:01:21 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Transaksi generated by hbm2java
 */
public class Transaksi  implements java.io.Serializable {


     private String idTransaksi;
     private Pembeli pembeli;
     private Date tglJual;
     private Integer kurs;
     private Integer hargaTotal;
     private double hargaTotalUsd;
     private boolean mata;
     private Boolean status;
     private Set suratJalans = new HashSet(0);
     private Set detailTransaksis = new HashSet(0);
     private Set kredits = new HashSet(0);

    public Transaksi() {
    }

	
    public Transaksi(String idTransaksi, double hargaTotalUsd, boolean mata) {
        this.idTransaksi = idTransaksi;
        this.hargaTotalUsd = hargaTotalUsd;
        this.mata = mata;
    }
    public Transaksi(String idTransaksi, Pembeli pembeli, Date tglJual, Integer kurs, Integer hargaTotal, double hargaTotalUsd, boolean mata, Boolean status, Set suratJalans, Set detailTransaksis, Set kredits) {
       this.idTransaksi = idTransaksi;
       this.pembeli = pembeli;
       this.tglJual = tglJual;
       this.kurs = kurs;
       this.hargaTotal = hargaTotal;
       this.hargaTotalUsd = hargaTotalUsd;
       this.mata = mata;
       this.status = status;
       this.suratJalans = suratJalans;
       this.detailTransaksis = detailTransaksis;
       this.kredits = kredits;
    }
   
    public String getIdTransaksi() {
        return this.idTransaksi;
    }
    
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
    public Pembeli getPembeli() {
        return this.pembeli;
    }
    
    public void setPembeli(Pembeli pembeli) {
        this.pembeli = pembeli;
    }
    public Date getTglJual() {
        return this.tglJual;
    }
    
    public void setTglJual(Date tglJual) {
        this.tglJual = tglJual;
    }
    public Integer getKurs() {
        return this.kurs;
    }
    
    public void setKurs(Integer kurs) {
        this.kurs = kurs;
    }
    public Integer getHargaTotal() {
        return this.hargaTotal;
    }
    
    public void setHargaTotal(Integer hargaTotal) {
        this.hargaTotal = hargaTotal;
    }
    public double getHargaTotalUsd() {
        return this.hargaTotalUsd;
    }
    
    public void setHargaTotalUsd(double hargaTotalUsd) {
        this.hargaTotalUsd = hargaTotalUsd;
    }
    public boolean isMata() {
        return this.mata;
    }
    
    public void setMata(boolean mata) {
        this.mata = mata;
    }
    public Boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Set getSuratJalans() {
        return this.suratJalans;
    }
    
    public void setSuratJalans(Set suratJalans) {
        this.suratJalans = suratJalans;
    }
    public Set getDetailTransaksis() {
        return this.detailTransaksis;
    }
    
    public void setDetailTransaksis(Set detailTransaksis) {
        this.detailTransaksis = detailTransaksis;
    }
    public Set getKredits() {
        return this.kredits;
    }
    
    public void setKredits(Set kredits) {
        this.kredits = kredits;
    }




}


