/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelPenjualanKredit.java
 *
 * Created on Oct 30, 2011, 9:13:24 PM
 */
package com.prima.view.laporan;

import com.prima.service.ReportService2;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author raphael
 */
public class PanelLaporanPenjualanKredit extends javax.swing.JPanel {

    /** Creates new form PanelPenjualanKredit */
    public PanelLaporanPenjualanKredit() {
        initComponents();
    }
    public JasperPrint createReport(){
        HashMap<Object,Object> param= new HashMap<Object, Object>();
        //param.put("tahun", jyTahun.getYear());
        //param.put("tglAkhir", jdcTglAkhir.getDate());
        ReportService2 reportService2= new ReportService2("report//reportTransaksiKredit.jrxml", param);
        return reportService2.getReport();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
