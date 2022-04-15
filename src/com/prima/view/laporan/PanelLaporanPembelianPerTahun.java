/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelLaporanPenjualanGlobal.java
 *
 * Created on Oct 24, 2011, 2:12:57 PM
 */
package com.prima.view.laporan;

import com.prima.service.ReportService2;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author raphael
 */
public class PanelLaporanPembelianPerTahun extends javax.swing.JPanel {

    /** Creates new form PanelLaporanPenjualanGlobal */
    public PanelLaporanPembelianPerTahun() {
        initComponents();
    }
    
    public JasperPrint createReport(){
        HashMap<Object,Object> param= new HashMap<Object, Object>();
        param.put("tahun", jyTahun.getYear());
        //param.put("tglAkhir", jdcTglAkhir.getDate());
        ReportService2 reportService2= new ReportService2("report//reportPembelianPerTahun.jrxml", param);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jyTahun = new com.toedter.calendar.JYearChooser();

        setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Tanggal Awal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(jLabel4, gridBagConstraints);
        add(jyTahun, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.toedter.calendar.JYearChooser jyTahun;
    // End of variables declaration//GEN-END:variables
}