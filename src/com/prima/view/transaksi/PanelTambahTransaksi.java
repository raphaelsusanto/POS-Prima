/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelTambahTransaksi.java
 *
 * Created on Oct 14, 2011, 8:43:27 PM
 */
package com.prima.view.transaksi;

import com.prima.dao.BarangDAO;
import com.prima.dao.PembeliDAO;
import com.prima.entity.Barang;
import com.prima.entity.DetailTransaksi;
import com.prima.entity.Kredit;
import com.prima.entity.Pembeli;
import com.prima.entity.Transaksi;
import com.prima.service.KursFactory;
import com.prima.service.ReportService2;
import com.prima.service.TransaksiService;
import com.prima.view.barang.DialogPilihBarang;
import com.prima.view.model.ButtonDeleteEditor;
import com.prima.view.model.ButtonDeleteRenderer;
import com.prima.view.pembeli.DialogPilihPembeli;
import com.prima.view.pembeli.DialogTambahPembeli;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class PanelTambahTransaksi extends javax.swing.JPanel {

    /**
     * Creates new form PanelTambahTransaksi
     */
    private DefaultComboBoxModel modelPembeli = new DefaultComboBoxModel();
    private DefaultComboBoxModel modelBarang = new DefaultComboBoxModel();
    private DefaultTableModel modelTransaksi = new DefaultTableModel(null, new Object[]{"Barang", "harga", "jumlah", "total IDR", "command"});
    private ButtonDeleteRenderer deleteRenderer = new ButtonDeleteRenderer();
    private ButtonDeleteEditor deleteEditor = new ButtonDeleteEditor(new JCheckBox());

    public PanelTambahTransaksi() {
        initComponents();
        init();
    }

    private void init() {
        jcbPembeli.setModel(modelPembeli);
        jcbBarang.setModel(modelBarang);
        deleteEditor.getButton().addActionListener(new DeleteRowTableTransaksi());
        jtblTransaksi.setRowHeight(30);
        jtblTransaksi.setModel(modelTransaksi);
        jtblTransaksi.getColumn("command").setCellRenderer(deleteRenderer);
        jtblTransaksi.getColumn("command").setCellEditor(deleteEditor);
        SpinnerNumberModel spinnerModelJumlah = (SpinnerNumberModel) jspJumlah.getModel();
        spinnerModelJumlah.setMinimum(0.1);
        refreshCbPembeli();
        refreshCbBarang();
    }

    private void refreshCbPembeli() {
        PembeliDAO dao = new PembeliDAO();
        modelPembeli.removeAllElements();
        //ComboBoxModel model=jcbPembeli.getModel();
        for (Pembeli pembeli : dao.getAllPembeli()) {
            modelPembeli.addElement(pembeli);
        }
    }

    private void refreshCbBarang() {
        BarangDAO dao = new BarangDAO();
        modelBarang.removeAllElements();
        for (Barang barang : dao.getAllBarang()) {
            if (barang.getStock() > 0) {
                modelBarang.addElement(barang);
            }
        }
    }

    public void refresh() {
        refreshCbBarang();
        refreshCbPembeli();
    }

    private void getHargaBarang() {
        SpinnerNumberModel spinerModelHarga = (SpinnerNumberModel) jspHarga.getModel();
        SpinnerNumberModel spinnerModelJumlah = (SpinnerNumberModel) jspJumlah.getModel();
        Barang b = (Barang) jcbBarang.getSelectedItem();
        if (jrbIDR.isSelected()) {
            spinerModelHarga.setMinimum(b.getHargaIdr());
            spinerModelHarga.setStepSize(100);
            spinerModelHarga.setValue(b.getHargaIdr());
        } else {
            spinerModelHarga.setMinimum(b.getHargaUsd());
            spinerModelHarga.setStepSize(0.1);
            spinerModelHarga.setValue(b.getHargaUsd());
        }
//        if (b.getMataUang()) {
//            jrbIDR.setSelected(true);
//            spinerModelHarga.setStepSize(100);
//        } else {
//            jrbUSD.setSelected(true);
//            spinerModelHarga.setStepSize(0.1);
//        }
//        spinerModelHarga.setValue(b.getHarga());
//        spinerModelHarga.setMinimum(b.getHarga());
        // spinnerModelJumlah.setMinimum(0);
        spinnerModelJumlah.setValue(0.1);
        spinnerModelJumlah.setMaximum(b.getStock());
    }

    private double sumTotal() {
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);
        int row = modelTransaksi.getRowCount();
        double total = 0;
        for (int i = 0; i < row; i++) {
            try {
                if (jrbUSD.isSelected()) {
                    total += Double.parseDouble(formatUSD.parse(modelTransaksi.getValueAt(i, 3).toString()).toString());
                } else {
                    total += Double.parseDouble(formatIDR.parse(modelTransaksi.getValueAt(i, 3).toString()).toString());
                }

            } catch (ParseException ex) {
                Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            total = Double.parseDouble(df.parse(df.format(total)).toString());
        } catch (ParseException ex) {
            Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(total);
        return total;
    }
//
//    private double sumTotalUSD() {
//        DecimalFormat df = new DecimalFormat("#.##");
//        double row = modelTransaksi.getRowCount();
//        double total = 0;
//        double result = 0;
//        for (int i = 0; i < row; i++) {
//            try {
//                total += Double.parseDouble(modelTransaksi.getValueAt(i, 5).toString());
//            } catch (Exception ex) {
//                Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        try {
//            result = Double.parseDouble(df.parse(df.format(total)).toString());
//        } catch (ParseException ex) {
//            Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }

    private void removeTableModel(DefaultTableModel model) {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    private void createSuratJalan(String id) {
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("idTransaksi", id);
        if (jcbCetakKurs.isSelected()) {
            param.put("kurs", formatIDR.format(KursFactory.getInstance().getKurs()));
        } else {
            param.put("kurs", "");
        }

        ReportService2 reportService1 = new ReportService2("report//SuratJalan.jrxml", param);
        reportService1.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jrbTunai = new javax.swing.JRadioButton();
        jrbKredit = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jcbPembeli = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jcbBarang = new javax.swing.JComboBox();
        jbtnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblTransaksi = new javax.swing.JTable();
        jbtnSimpan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlbTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jdatePelunasan = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jspJumlah = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jrbIDR = new javax.swing.JRadioButton();
        jrbUSD = new javax.swing.JRadioButton();
        jspHarga = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtfDP = new com.prima.view.model.NumberTextbox();
        jcbCetakKurs = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jcbSuratJalan = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jtfKendaraan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfNoKendaraan = new javax.swing.JTextField();
        jbtnLihatTransaksi = new javax.swing.JButton();
        jchckPPN = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Jenis Transaksi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel1, gridBagConstraints);

        buttonGroup1.add(jrbTunai);
        jrbTunai.setSelected(true);
        jrbTunai.setText("Tunai");
        jrbTunai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbTunaiActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jrbTunai, gridBagConstraints);

        buttonGroup1.add(jrbKredit);
        jrbKredit.setText("Kredit");
        jrbKredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbKreditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jrbKredit, gridBagConstraints);

        jLabel2.setText("Pembeli");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel2, gridBagConstraints);

        jcbPembeli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jcbPembeli, gridBagConstraints);

        jLabel3.setText("Barang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel3, gridBagConstraints);

        jcbBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbBarangActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jcbBarang, gridBagConstraints);

        jbtnTambah.setText("Tambah");
        jbtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnTambahActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(jbtnTambah, gridBagConstraints);

        jtblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblTransaksi);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jbtnSimpan.setText("Simpan");
        jbtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSimpanActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 13;
        add(jbtnSimpan, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jLabel4, gridBagConstraints);

        jLabel5.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(jLabel5, gridBagConstraints);

        jlbTotal.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 12;
        add(jlbTotal, gridBagConstraints);

        jLabel7.setText("Tanggal Pelunasan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel7, gridBagConstraints);

        jdatePelunasan.setDateFormatString("dd-MM-yyyy");
        jdatePelunasan.setEnabled(false);
        jdatePelunasan.setMaximumSize(new java.awt.Dimension(30, 30));
        jdatePelunasan.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jdatePelunasan, gridBagConstraints);

        jLabel8.setText("Jumlah per Kg/Liter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel8, gridBagConstraints);

        jspJumlah.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.1d), null, null, Double.valueOf(0.1d)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jspJumlah, gridBagConstraints);

        jLabel9.setText("Harga per Kg/Liter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel9, gridBagConstraints);

        jLabel10.setText("Mata uang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel10, gridBagConstraints);

        buttonGroup2.add(jrbIDR);
        jrbIDR.setSelected(true);
        jrbIDR.setText("IDR");
        jrbIDR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbIDRActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jrbIDR, gridBagConstraints);

        buttonGroup2.add(jrbUSD);
        jrbUSD.setText("USD");
        jrbUSD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbUSDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jrbUSD, gridBagConstraints);

        jspHarga.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jspHarga, gridBagConstraints);

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jButton1, gridBagConstraints);

        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jButton2, gridBagConstraints);

        jLabel6.setText("Down Payment(DP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel6, gridBagConstraints);

        jtfDP.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jtfDP, gridBagConstraints);

        jcbCetakKurs.setText("Cetak Kurs");
        jcbCetakKurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCetakKursActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jcbCetakKurs, gridBagConstraints);

        jPanel1.setAlignmentX(2.0F);
        jPanel1.setLayout(new java.awt.GridLayout(4, 2));

        jcbSuratJalan.setText("Surat Jalan");
        jcbSuratJalan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jcbSuratJalanStateChanged(evt);
            }
        });
        jPanel1.add(jcbSuratJalan);
        jPanel1.add(jLabel11);

        jLabel13.setText("Kendaraan");
        jPanel1.add(jLabel13);

        jtfKendaraan.setEnabled(false);
        jPanel1.add(jtfKendaraan);

        jLabel12.setText("No Kendaraan");
        jPanel1.add(jLabel12);

        jtfNoKendaraan.setEnabled(false);
        jPanel1.add(jtfNoKendaraan);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        add(jPanel1, gridBagConstraints);

        jbtnLihatTransaksi.setText("Lihat Transaksi");
        jbtnLihatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLihatTransaksiActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jbtnLihatTransaksi, gridBagConstraints);

        jchckPPN.setText("PPN");
        jchckPPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchckPPNActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jchckPPN, gridBagConstraints);

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("USD --> IDR");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        add(jRadioButton1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void jrbKreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbKreditActionPerformed
    boolean kredit = jrbKredit.isSelected();
    jdatePelunasan.setEnabled(kredit);
    jtfDP.setEnabled(kredit);
}//GEN-LAST:event_jrbKreditActionPerformed

private void jrbTunaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbTunaiActionPerformed
    boolean kredit = jrbKredit.isSelected();
    jdatePelunasan.setEnabled(kredit);
    jtfDP.setEnabled(kredit);
}//GEN-LAST:event_jrbTunaiActionPerformed

private void jcbBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbBarangActionPerformed
    try {
        getHargaBarang();
    } catch (Exception e) {
    }
}//GEN-LAST:event_jcbBarangActionPerformed

private void jbtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnTambahActionPerformed
    jRadioButton1.setEnabled(false);
    jrbIDR.setEnabled(false);
    jrbUSD.setEnabled(false);
    NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);
    DecimalFormat df = new DecimalFormat("#.#");
    Barang barang = (Barang) jcbBarang.getSelectedItem();
    double kurs = KursFactory.getInstance().getKurs();
    double harga = Double.parseDouble(jspHarga.getValue().toString());
    double jumlah = Double.parseDouble(jspJumlah.getValue().toString());
    try {
        jumlah = Double.parseDouble(df.parse(df.format(jumlah)).toString());
    } catch (ParseException ex) {
        //  Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
    }
    double total = harga * jumlah;
    String stringHarga;
    String stringTotal;
    if (jrbIDR.isSelected()) {
        stringHarga = formatIDR.format(harga);
        stringTotal = formatIDR.format(total);
    } else if (jRadioButton1.isSelected()) {
        total *= kurs;
        stringHarga = formatUSD.format(harga);
        stringTotal = formatIDR.format(total);
    } else {
        stringHarga = formatUSD.format(harga);
        stringTotal = formatUSD.format(total);
    }

//    int j;
//    for (j = 0; j < modelTransaksi.getRowCount(); j++) {
//        if (barang.equals(modelTransaksi.getValueAt(j, 0))) {
//            break;
//        }
//    }
//
//    if (j != modelTransaksi.getRowCount()) {
//        double jmlh = (Double) modelTransaksi.getValueAt(j, 2);
//        jmlh = jmlh + jumlah;
//        try {
//            jmlh = Double.parseDouble(df.parse(df.format(jmlh)).toString());
//        } catch (ParseException ex) {
//            //  Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        modelTransaksi.setValueAt(jmlh, j, 2);
//        modelTransaksi.setValueAt(formatIDR.format((jmlh) * harga), j, 3);
//    } else {
//        modelTransaksi.addRow(new Object[]{barang, stringHarga, jumlah, formatIDR.format((int) (total)), jrbIDR.isSelected(), jumlah * barang.getHargaUsd()});
//    }
    modelTransaksi.addRow(new Object[]{barang, stringHarga, jumlah, stringTotal, jrbIDR.isSelected()});
    if (jrbIDR.isSelected() || jRadioButton1.isSelected()) {
        jlbTotal.setText(formatIDR.format(sumTotal()));
    } else {
        jlbTotal.setText(formatUSD.format(sumTotal()));
    }

}//GEN-LAST:event_jbtnTambahActionPerformed

private void jbtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSimpanActionPerformed
    //System.out.println(sumTotalUSD());
    NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);


    if (modelTransaksi.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Masukkan data transaksi!");
    } else {
        double total = 0;
        Transaksi transaksi = new Transaksi();
        List<DetailTransaksi> dataDetailTransaksi = new ArrayList<DetailTransaksi>();
        TransaksiService service = new TransaksiService();
        transaksi.setKurs((int) KursFactory.getInstance().getKurs());
        if (jchckPPN.isSelected()) {
            total = sumTotal() * 1.1;
        } else {
            total = sumTotal();
        }
        if (jrbUSD.isSelected()) {
            transaksi.setMata(false);
            transaksi.setHargaTotalUsd(total);
            transaksi.setHargaTotal((int) (transaksi.getKurs() * transaksi.getHargaTotalUsd()));
        } else {
            transaksi.setMata(true);
            transaksi.setHargaTotal((int) total);
        }

        for (int i = 0; i < modelTransaksi.getRowCount(); i++) {
            DetailTransaksi detailTransaksi = new DetailTransaksi();
            boolean mataUang = (Boolean) modelTransaksi.getValueAt(i, 4);
            detailTransaksi.setBarang((Barang) modelTransaksi.getValueAt(i, 0));
            detailTransaksi.setJumlah((Double) modelTransaksi.getValueAt(i, 2));
            double harga = 0;
            try {
                if (mataUang) {
                    harga = Double.parseDouble(formatIDR.parse(modelTransaksi.getValueAt(i, 1) + "") + "");
                } else {
                    harga = Double.parseDouble(formatUSD.parse(modelTransaksi.getValueAt(i, 1) + "") + "");
                }
            } catch (ParseException ex) {
                Logger.getLogger(PanelTambahTransaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
            detailTransaksi.setHarga(harga);
            detailTransaksi.setMataUang(mataUang);
            dataDetailTransaksi.add(detailTransaksi);
        }



        String id = "";
        if (!jrbKredit.isSelected()) {
            transaksi.setPembeli((Pembeli) jcbPembeli.getSelectedItem());
            if (jcbSuratJalan.isSelected()) {
                id = service.saveTransaksi(transaksi, dataDetailTransaksi, true, jtfKendaraan.getText(), jtfNoKendaraan.getText());
                createSuratJalan(id);
            } else {
                id = service.saveTransaksi(transaksi, dataDetailTransaksi, false, "", "");
            }

            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idTransaksi", id);
            param.put("terbilang", convert2((int) total));
            if (jcbCetakKurs.isSelected()) {
                param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
            } else {
                param.put("kurs", "");
            }

            ReportService2 reportService1;
            if (jrbIDR.isSelected()) {
                if (jchckPPN.isSelected()) {
                    reportService1 = new ReportService2("report//NotaTransaksiPPN.jrxml", param);
                } else {
                    reportService1 = new ReportService2("report//NotaTransaksi.jrxml", param);
                }
            } else if (jRadioButton1.isSelected()) {
                param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                if (jchckPPN.isSelected()) {
                    reportService1 = new ReportService2("report//NotaTransaksiUSDPPN.jrxml", param);
                } else {
                    reportService1 = new ReportService2("report//NotaTransaksiUSD.jrxml", param);
                }
            } else {
                param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                String usd = total + "00000";
                String[] temp = usd.split("\\.");
                int[] temp2 = new int[2];
                temp2[0] = Integer.parseInt(temp[0]);
                temp2[1] = Integer.parseInt(temp[1].substring(0, 2));
                param.put("terbilang", convert2(temp2[0]) + " US DOLLAR " + convert2(temp2[1]) + " SEN");
                if (jchckPPN.isSelected()) {
                    reportService1 = new ReportService2("report//NotaTransaksiUSD2PPN.jrxml", param);
                } else {
                    reportService1 = new ReportService2("report//NotaTransaksiUSD2.jrxml", param);
                }
            }
            reportService1.start();
        } else {
            if (jdatePelunasan.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Masukkan tanggal pelunasan!");
            } else if (jdatePelunasan.getDate().before(new Date())) {
                JOptionPane.showMessageDialog(null, "Tanggal pelunasan tidak valid!");
            } else {
                transaksi.setPembeli((Pembeli) jcbPembeli.getSelectedItem());
                Kredit kredit = new Kredit();
                HashMap<String, Object> param = new HashMap<String, Object>();
                kredit.setTglAkhirPelunasan(jdatePelunasan.getDate());
                if (jtfDP.getText().trim().equals("") || jtfDP.getText().trim().equals("0")) {
                    if (jcbSuratJalan.isSelected()) {
                        id = service.saveTransaksi(transaksi, dataDetailTransaksi, kredit, 0, true, jtfKendaraan.getText(), jtfNoKendaraan.getText());
                        createSuratJalan(id);
                    } else {
                        id = service.saveTransaksi(transaksi, dataDetailTransaksi, kredit, 0, false, "", "");
                    }

                    param.put("terbilang", convert2((int) total));
                    param.put("idTransaksi", id);
                    if (jcbCetakKurs.isSelected()) {
                        param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                    } else {
                        param.put("kurs", "");
                    }

                    ReportService2 reportService1;
                    if (jrbIDR.isSelected()) {
                        if (jchckPPN.isSelected()) {
                            reportService1 = new ReportService2("report//NotaTransaksiPPN.jrxml", param);
                        } else {
                            reportService1 = new ReportService2("report//NotaTransaksi.jrxml", param);
                        }
                    } else if (jRadioButton1.isSelected()) {
                        param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                        if (jchckPPN.isSelected()) {
                            reportService1 = new ReportService2("report//NotaTransaksiUSDPPN.jrxml", param);
                        } else {
                            reportService1 = new ReportService2("report//NotaTransaksiUSD.jrxml", param);
                        }
                    } else {
                        param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                        String usd = total + "";
                        String[] temp = usd.split("\\.");
                        int[] temp2 = new int[2];
                        temp2[0] = Integer.parseInt(temp[0]);
                        temp2[1] = Integer.parseInt(temp[1].substring(0, 2));
                        param.put("terbilang", convert2(temp2[0]) + " US DOLLAR " + convert2(temp2[1]) + " SEN");
                        if (jchckPPN.isSelected()) {
                            reportService1 = new ReportService2("report//NotaTransaksiUSD2PPN.jrxml", param);
                        } else {
                            reportService1 = new ReportService2("report//NotaTransaksiUSD2.jrxml", param);
                        }
                    }
                    reportService1.start();
                } else {
                    if (Double.parseDouble(jtfDP.getText()) > total) {
                        JOptionPane.showMessageDialog(null, "Jumlah DP salah");
                    } else {
                        if (jcbSuratJalan.isSelected()) {
                            id = service.saveTransaksi(transaksi, dataDetailTransaksi, kredit, Double.parseDouble(jtfDP.getText()), true, jtfKendaraan.getText(), jtfNoKendaraan.getText());
                            createSuratJalan(id);
                        } else {
                            id = service.saveTransaksi(transaksi, dataDetailTransaksi, kredit, Double.parseDouble(jtfDP.getText()), false, "", "");
                        }

                        param.put("idTransaksi", id);
                        //param.put("terbilang", convert2(sumTotal()));
                        if (jcbCetakKurs.isSelected()) {
                            param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                        } else {
                            param.put("kurs", "");
                        }

                        ReportService2 reportService1;
                        if (jrbIDR.isSelected()) {
                            if (jchckPPN.isSelected()) {
                                reportService1 = new ReportService2("report//NotaTransaksiPPN.jrxml", param);
                            } else {
                                reportService1 = new ReportService2("report//NotaTransaksi.jrxml", param);
                            }
                        } else if (jRadioButton1.isSelected()) {
                            param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                            if (jchckPPN.isSelected()) {
                                reportService1 = new ReportService2("report//NotaTransaksiUSDPPN.jrxml", param);
                            } else {
                                reportService1 = new ReportService2("report//NotaTransaksiUSD.jrxml", param);
                            }
                        } else {
                            param.put("kurs", "Kurs :" + formatIDR.format(KursFactory.getInstance().getKurs()));
                            String usd = total + "00000";
                            String[] temp = usd.split("\\.");
                            int[] temp2 = new int[2];
                            temp2[0] = Integer.parseInt(temp[0]);
                            temp2[1] = Integer.parseInt(temp[1].substring(0, 2));
                            param.put("terbilang", convert2(temp2[0]) + " US DOLLAR " + convert2(temp2[1]) + " SEN");
                            if (jchckPPN.isSelected()) {
                                reportService1 = new ReportService2("report//NotaTransaksiUSD2PPN.jrxml", param);
                            } else {
                                reportService1 = new ReportService2("report//NotaTransaksiUSD2.jrxml", param);
                            }
                        }

                        reportService1.start();
                    }
                }
            }
        }
        removeTableModel(modelTransaksi);
        jlbTotal.setText(formatIDR.format(sumTotal()));
        refresh();

        jRadioButton1.setEnabled(true);
        jrbIDR.setEnabled(true);
        jrbUSD.setEnabled(true);
        jchckPPN.setSelected(false);
    }

}//GEN-LAST:event_jbtnSimpanActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    new DialogPilihPembeli(null, true).setVisible(true);
    jcbPembeli.getModel().setSelectedItem(DialogPilihPembeli.getSelectedPembeli());
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    new DialogPilihBarang(null, true).setVisible(true);
    jcbBarang.getModel().setSelectedItem(DialogPilihBarang.getSelectedBarang());
}//GEN-LAST:event_jButton2ActionPerformed

private void jcbCetakKursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCetakKursActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jcbCetakKursActionPerformed

private void jcbSuratJalanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jcbSuratJalanStateChanged

    boolean a = jcbSuratJalan.isSelected();
    jtfKendaraan.setEnabled(a);
    jtfNoKendaraan.setEnabled(a);
}//GEN-LAST:event_jcbSuratJalanStateChanged

    private void jrbIDRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbIDRActionPerformed
        getHargaBarang();
    }//GEN-LAST:event_jrbIDRActionPerformed

    private void jrbUSDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbUSDActionPerformed
        getHargaBarang();
    }//GEN-LAST:event_jrbUSDActionPerformed

    private void jbtnLihatTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLihatTransaksiActionPerformed
        DialogLihatTransaksiPembeli dialog = new DialogLihatTransaksiPembeli(null, false, (Pembeli) jcbPembeli.getSelectedItem());
        dialog.setVisible(true);
    }//GEN-LAST:event_jbtnLihatTransaksiActionPerformed

    private void jchckPPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchckPPNActionPerformed
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);
        double total = sumTotal();
        if (jchckPPN.isSelected()) {
            total *= 1.1;
        }
        if (jrbUSD.isSelected()) {
            jlbTotal.setText(formatUSD.format(total));
        } else {
            jlbTotal.setText(formatIDR.format(total));
        }

    }//GEN-LAST:event_jchckPPNActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        getHargaBarang();
    }//GEN-LAST:event_jRadioButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnLihatTransaksi;
    private javax.swing.JButton jbtnSimpan;
    private javax.swing.JButton jbtnTambah;
    private javax.swing.JComboBox jcbBarang;
    private javax.swing.JCheckBox jcbCetakKurs;
    private javax.swing.JComboBox jcbPembeli;
    private javax.swing.JCheckBox jcbSuratJalan;
    private javax.swing.JCheckBox jchckPPN;
    private com.toedter.calendar.JDateChooser jdatePelunasan;
    private javax.swing.JLabel jlbTotal;
    private javax.swing.JRadioButton jrbIDR;
    private javax.swing.JRadioButton jrbKredit;
    private javax.swing.JRadioButton jrbTunai;
    private javax.swing.JRadioButton jrbUSD;
    private javax.swing.JSpinner jspHarga;
    private javax.swing.JSpinner jspJumlah;
    private javax.swing.JTable jtblTransaksi;
    private com.prima.view.model.NumberTextbox jtfDP;
    private javax.swing.JTextField jtfKendaraan;
    private javax.swing.JTextField jtfNoKendaraan;
    // End of variables declaration//GEN-END:variables

    public class DeleteRowTableTransaksi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            int row = (Integer) deleteEditor.getCellEditorValue();
            modelTransaksi.removeRow(row);
            jlbTotal.setText(formatIDR.format(sumTotal()));
            if (modelTransaksi.getRowCount() == 0) {
                jrbIDR.setEnabled(true);
                jrbUSD.setEnabled(true);
                jRadioButton1.setEnabled(true);
            }
        }
    }
    static String[] angka = {"SATU ", "DUA ", "TIGA ", "EMPAT ", "LIMA ", "ENAM ", "TUJUH ", "DELAPAN ", "SEMBILAN "};
    static String[] angka2 = {"", "RIBU ", "JUTA ", "MILIAR ", "TRILIUN "};

    public static String convert(int number) {
        String result = "";
        int d1 = number / 100;
        int d2 = number % 100 / 10;
        int d3 = number % 100 % 10;
        if (d1 != 0) {
            if (d1 == 1) {
                result = "SERATUS ";
            } else {
                result = angka[d1 - 1] + "RATUS ";
            }
        }
        if (d2 != 0) {
            if (d2 == 1 && d3 == 0) {
                result += "SEPULUH ";
            } else if (d2 == 1) {
                if (d3 == 1) {
                    result += "SEBELAS ";
                } else {
                    result += angka[d3 - 1] + "BELAS ";
                }
            } else {
                result += angka[d2 - 1] + "PULUH ";
            }
        }
        if (d3 != 0 && d2 != 1) {
            result += angka[d3 - 1];
        }
        return result;
    }

    public static String convert2(int number) {
        String result = "";
        String temp = "";
        int count = 0;
        int count2 = 0;
        char[] cNumber = (number + "").toCharArray();
        for (int i = cNumber.length - 1; i >= 0; i--) {
            temp = cNumber[i] + temp;
            count++;
            if (count == 3 || i == 0) {
                result = convert(Integer.parseInt(temp)) + angka2[count2] + result;
                count2++;
                count = 0;
                temp = "";
            }
        }
        return result;
    }
}
