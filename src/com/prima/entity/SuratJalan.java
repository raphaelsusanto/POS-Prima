package com.prima.entity;
// Generated Apr 16, 2012 3:04:35 PM by Hibernate Tools 3.2.1.GA



/**
 * SuratJalan generated by hbm2java
 */
public class SuratJalan  implements java.io.Serializable {


     private String idSuratJalan;
     private Transaksi transaksi;
     private String kendaraan;
     private String noKendaraan;

    public SuratJalan() {
    }

	
    public SuratJalan(String idSuratJalan) {
        this.idSuratJalan = idSuratJalan;
    }
    public SuratJalan(String idSuratJalan, Transaksi transaksi, String kendaraan, String noKendaraan) {
       this.idSuratJalan = idSuratJalan;
       this.transaksi = transaksi;
       this.kendaraan = kendaraan;
       this.noKendaraan = noKendaraan;
    }
   
    public String getIdSuratJalan() {
        return this.idSuratJalan;
    }
    
    public void setIdSuratJalan(String idSuratJalan) {
        this.idSuratJalan = idSuratJalan;
    }
    public Transaksi getTransaksi() {
        return this.transaksi;
    }
    
    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }
    public String getKendaraan() {
        return this.kendaraan;
    }
    
    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }
    public String getNoKendaraan() {
        return this.noKendaraan;
    }
    
    public void setNoKendaraan(String noKendaraan) {
        this.noKendaraan = noKendaraan;
    }




}


