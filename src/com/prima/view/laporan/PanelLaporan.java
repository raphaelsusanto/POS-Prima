/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelLaporan.java
 *
 * Created on Oct 23, 2011, 7:50:37 PM
 */
package com.prima.view.laporan;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author raphael
 */
public class PanelLaporan extends javax.swing.JPanel {

    /** Creates new form PanelLaporan */
    private CardLayout cardLayout = new CardLayout();
    private PanelLaporanStockBarang laporanStockBarang = new PanelLaporanStockBarang();
    private PanelLaporanPenjualanGlobal laporanPenjualanGlobal = new PanelLaporanPenjualanGlobal();
    private PanelLaporanPenjualanDetail laporanPenjualanDetail = new PanelLaporanPenjualanDetail();
    private PanelLaporanPenjualanPerTahun laporanPenjualanPerTahun = new PanelLaporanPenjualanPerTahun();
    private PanelLaporanPembelianGlobal laporanPembelianGlobal = new PanelLaporanPembelianGlobal();
    private PanelLaporanPembelianDetail laporanPembelianDetail = new PanelLaporanPembelianDetail();
    private PanelLaporanPembelianUnitBarang laporanPembelianUnitBarang = new PanelLaporanPembelianUnitBarang();
    private PanelLaporanPenjualanUnitBarang laporanPenjualanUnitBarang = new PanelLaporanPenjualanUnitBarang();
    private PanelLaporanPembelianPerTahun laporanPembelianPerTahun = new PanelLaporanPembelianPerTahun();
    private PanelLaporanPenjualanKredit laporanPenjualanKredit = new PanelLaporanPenjualanKredit();
    private PanelLaporanDetailStockBarang laporanDetailStockBarang = new PanelLaporanDetailStockBarang();
    private PanelLaporanLabaRugi laporanLabaRugi = new PanelLaporanLabaRugi();
    private PanelLaporanPembeli laporanPembeli= new PanelLaporanPembeli();
    private PanelLaporanKreditDetail laporanKreditDetail= new PanelLaporanKreditDetail();
    private PanelLaporanGiro laporanGiro= new PanelLaporanGiro();
    
    public PanelLaporan() {
        initComponents();
        init();
    }

    public void init() {
        jpnlLaporan.setLayout(cardLayout);
        jpnlLaporan.add("laporanStockBarang", laporanStockBarang);
        jpnlLaporan.add("laporanPenjualanGlobal", laporanPenjualanGlobal);
        jpnlLaporan.add("laporanPenjualanDetail", laporanPenjualanDetail);
        jpnlLaporan.add("laporanPenjualanPerTahun", laporanPenjualanPerTahun);
        jpnlLaporan.add("laporanPembelianGlobal", laporanPembelianGlobal);
        jpnlLaporan.add("laporanPembelianDetail", laporanPembelianDetail);
        jpnlLaporan.add("laporanPembelianUnitBarang", laporanPembelianUnitBarang);
        jpnlLaporan.add("laporanPenjualanUnitBarang", laporanPenjualanUnitBarang);
        jpnlLaporan.add("laporanPembelianPerTahun", laporanPembelianPerTahun);
        jpnlLaporan.add("laporanPenjualanKredit", laporanPenjualanKredit);
        jpnlLaporan.add("laporanDetailStockBarang", laporanDetailStockBarang);
        jpnlLaporan.add("laporanLabaRugi", laporanLabaRugi);
        jpnlLaporan.add("laporanPembeli",laporanPembeli);
        jpnlLaporan.add("laporanKreditDetail",laporanKreditDetail);
        jpnlLaporan.add("laporanGiro",laporanGiro);
    }

    public void refresh() {
        laporanDetailStockBarang.refreshCBBarang();
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

        jpnlViewLaporan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbLaporan = new javax.swing.JComboBox();
        jpnlLaporan = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jpnlViewLaporan.setLayout(new java.awt.BorderLayout());
        add(jpnlViewLaporan, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Jenis Laporan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jcbLaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Stock Barang", "Detail Stock Barang", "Laba Rugi", "Penjualan Global", "Penjualan Detail", "Pembelian Global", "Pembelian Detail", "Penjualan PerTahun", "Pembelian PerTahun", "Pembelian Unit Barang", "Penjualan Unit Barang", "Penjualan Kredit", "Penjualan Kredit Detail", "Penjualan PerPembeli", "Giro" }));
        jcbLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbLaporanActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jcbLaporan, gridBagConstraints);

        javax.swing.GroupLayout jpnlLaporanLayout = new javax.swing.GroupLayout(jpnlLaporan);
        jpnlLaporan.setLayout(jpnlLaporanLayout);
        jpnlLaporanLayout.setHorizontalGroup(
            jpnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jpnlLaporanLayout.setVerticalGroup(
            jpnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jpnlLaporan, gridBagConstraints);

        jButton1.setText("LIhat Laporan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jButton1, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void jcbLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLaporanActionPerformed
    if (jcbLaporan.getSelectedItem().toString().equals("Stock Barang")) {
        cardLayout.show(jpnlLaporan, "laporanStockBarang");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Detail Stock Barang")) {
        cardLayout.show(jpnlLaporan, "laporanDetailStockBarang");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Global")) {
        cardLayout.show(jpnlLaporan, "laporanPenjualanGlobal");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Detail")) {
        cardLayout.show(jpnlLaporan, "laporanPenjualanDetail");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan PerTahun")) {
        cardLayout.show(jpnlLaporan, "laporanPenjualanPerTahun");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian PerTahun")) {
        cardLayout.show(jpnlLaporan, "laporanPembelianPerTahun");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Global")) {
        cardLayout.show(jpnlLaporan, "laporanPembelianGlobal");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Detail")) {
        cardLayout.show(jpnlLaporan, "laporanPembelianDetail");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Unit Barang")) {
        cardLayout.show(jpnlLaporan, "laporanPembelianUnitBarang");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Unit Barang")) {
        cardLayout.show(jpnlLaporan, "laporanPenjualanUnitBarang");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Kredit")) {
        cardLayout.show(jpnlLaporan, "laporanPenjualanKredit");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Laba Rugi")) {
        cardLayout.show(jpnlLaporan, "laporanLabaRugi");
    } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan PerPembeli")) {
        cardLayout.show(jpnlLaporan, "laporanPembeli");
    }else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Kredit Detail")) {
        cardLayout.show(jpnlLaporan, "laporanKreditDetail");
    }else if (jcbLaporan.getSelectedItem().toString().equals("Giro")) {
        cardLayout.show(jpnlLaporan, "laporanGiro");
    }
}//GEN-LAST:event_jcbLaporanActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    jpnlViewLaporan.removeAll();
    JRViewer viewer;
    try {
        if (jcbLaporan.getSelectedItem().toString().equals("Stock Barang")) {
            viewer = new JRViewer(laporanStockBarang.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Detail Stock Barang")) {
            viewer = new JRViewer(laporanDetailStockBarang.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Global")) {
            viewer = new JRViewer(laporanPenjualanGlobal.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Detail")) {

            viewer = new JRViewer(laporanPenjualanDetail.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan PerTahun")) {
            viewer = new JRViewer(laporanPenjualanPerTahun.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian PerTahun")) {
            viewer = new JRViewer(laporanPembelianPerTahun.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Global")) {
            viewer = new JRViewer(laporanPembelianGlobal.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Detail")) {
            viewer = new JRViewer(laporanPembelianDetail.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);
        } else if (jcbLaporan.getSelectedItem().toString().equals("Pembelian Unit Barang")) {
            viewer = new JRViewer(laporanPembelianUnitBarang.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Unit Barang")) {
            viewer = new JRViewer(laporanPenjualanUnitBarang.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Kredit")) {
            viewer = new JRViewer(laporanPenjualanKredit.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        } else if (jcbLaporan.getSelectedItem().toString().equals("Laba Rugi")) {
            viewer = new JRViewer(laporanLabaRugi.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        }
         else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan PerPembeli")) {
            viewer = new JRViewer(laporanPembeli.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        } else if (jcbLaporan.getSelectedItem().toString().equals("Penjualan Kredit Detail")) {
            viewer = new JRViewer(laporanKreditDetail.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        }else if (jcbLaporan.getSelectedItem().toString().equals("Giro")) {
            viewer = new JRViewer(laporanGiro.createReport());
            jpnlViewLaporan.add(viewer, BorderLayout.CENTER);

        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

}//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox jcbLaporan;
    private javax.swing.JPanel jpnlLaporan;
    private javax.swing.JPanel jpnlViewLaporan;
    // End of variables declaration//GEN-END:variables
}