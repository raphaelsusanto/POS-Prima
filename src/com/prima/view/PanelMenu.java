/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelMenu.java
 *
 * Created on Oct 3, 2011, 9:55:52 AM
 */
package com.prima.view;

import com.prima.dao.LoginDAO;
import javax.swing.JButton;

/**
 *
 * @author raphael
 */
public class PanelMenu extends javax.swing.JPanel {

    /** Creates new form PanelMenu */
    public PanelMenu() {
        initComponents();
        cek();
    }

    private void cek() {
        if (LoginDAO.role == 1) {
            jbtnLaporan.setEnabled(true);
        } else {
            jbtnLaporan.setEnabled(false);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnBarang = new javax.swing.JButton();
        jbtnPembeli = new javax.swing.JButton();
        jbtnSupplier = new javax.swing.JButton();
        jbtnPembelian = new javax.swing.JButton();
        jbtnPenjualan = new javax.swing.JButton();
        jbtnLaporan = new javax.swing.JButton();
        jbtnPengaturan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jbtnKeluar = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(14, 1, 5, 5));

        jbtnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/product.png"))); // NOI18N
        jbtnBarang.setText("Barang");
        add(jbtnBarang);

        jbtnPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customers.png"))); // NOI18N
        jbtnPembeli.setText("Pembeli");
        add(jbtnPembeli);

        jbtnSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        jbtnSupplier.setText("Supplier");
        add(jbtnSupplier);

        jbtnPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/basket.png"))); // NOI18N
        jbtnPembelian.setText("Pembelian");
        add(jbtnPembelian);

        jbtnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bank.png"))); // NOI18N
        jbtnPenjualan.setText("Penjualan");
        add(jbtnPenjualan);

        jbtnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/invoice.png"))); // NOI18N
        jbtnLaporan.setText("Laporan");
        add(jbtnLaporan);

        jbtnPengaturan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/config.png"))); // NOI18N
        jbtnPengaturan.setText("Pengaturan");
        add(jbtnPengaturan);
        add(jLabel5);
        add(jLabel2);
        add(jLabel4);
        add(jLabel3);
        add(jLabel1);

        jbtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        jbtnKeluar.setText("Keluar");
        jbtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnKeluarActionPerformed(evt);
            }
        });
        add(jbtnKeluar);
    }// </editor-fold>//GEN-END:initComponents

private void jbtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnKeluarActionPerformed
}//GEN-LAST:event_jbtnKeluarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton jbtnBarang;
    private javax.swing.JButton jbtnKeluar;
    private javax.swing.JButton jbtnLaporan;
    private javax.swing.JButton jbtnPembeli;
    private javax.swing.JButton jbtnPembelian;
    private javax.swing.JButton jbtnPengaturan;
    private javax.swing.JButton jbtnPenjualan;
    private javax.swing.JButton jbtnSupplier;
    // End of variables declaration//GEN-END:variables

    public JButton getJbtnBarang() {
        return jbtnBarang;
    }

    public JButton getJbtnKeluar() {
        return jbtnKeluar;
    }

    public JButton getJbtnLaporan() {
        return jbtnLaporan;
    }

    public JButton getJbtnPembeli() {
        return jbtnPembeli;
    }

    public JButton getJbtnPembelian() {
        return jbtnPembelian;
    }

    public JButton getJbtnPengaturan() {
        return jbtnPengaturan;
    }

    public JButton getJbtnPenjualan() {
        return jbtnPenjualan;
    }

    public JButton getJbtnSupplier() {
        return jbtnSupplier;
    }
}