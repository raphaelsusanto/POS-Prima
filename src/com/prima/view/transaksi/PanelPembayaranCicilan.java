/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelPembayaranCicilan.java
 *
 * Created on Oct 18, 2011, 1:34:32 PM
 */
package com.prima.view.transaksi;

import com.prima.dao.CicilanDAO;
import com.prima.dao.KreditDAO;
import com.prima.dao.LoginDAO;
import com.prima.dao.PembeliDAO;
import com.prima.dao.TransaksiDAO;
import com.prima.entity.Cicilan;
import com.prima.entity.Kredit;
import com.prima.entity.Pembeli;
import com.prima.entity.Transaksi;
import com.prima.view.model.ButtonBayarEditor;
import com.prima.view.model.ButtonBayarRenderer;
import com.prima.view.pembeli.DialogPilihPembeli;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class PanelPembayaranCicilan extends javax.swing.JPanel {

    /**
     * Creates new form PanelPembayaranCicilan
     */
    private DefaultTableModel modelTransaksiKredit = new DefaultTableModel(null, new Object[]{"Nama Pembeli", "Tanggal Jual", "Tanggal Pelunasan", "Kurs", "Harga Total", "Sisa Bayar", "Jenis Pembayaran", "Status Pembayaran", "Command"});
    private DefaultTableModel modelCicilan = new DefaultTableModel(null, new Object[]{"ID Cicilan", "Jumlah Bayar", "Tanggal Bayar", "Jenis Pembayaran", "No Giro"});
    private DefaultComboBoxModel modelPembeli = new DefaultComboBoxModel();
    private NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    private NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);
    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private ButtonBayarEditor bayarEditor = new ButtonBayarEditor(new JCheckBox());
    private ButtonBayarRenderer bayarRenderer = new ButtonBayarRenderer();

    public PanelPembayaranCicilan() {
        initComponents();
        init();
        cek();
    }

    private void init() {
        jcbPembeli.setModel(modelPembeli);
        jtblKredit.setModel(modelTransaksiKredit);
        jtblKredit.setDefaultRenderer(Object.class, new TableCicilanRenderer());
        jtblKredit.setRowHeight(30);
        jtblKredit.getColumn("Command").setCellEditor(bayarEditor);
        jtblKredit.getColumn("Command").setCellRenderer(bayarRenderer);
        jtblKredit.getSelectionModel().addListSelectionListener(new KreditListSelectionListener());
        bayarEditor.getButton().addActionListener(new BayarActionListener());
        refreshDataKredit(transaksiDAO.getTransaksiKredit());
        refreshCBPembeli();

        jtblPembayaran.setModel(modelCicilan);
        jtblPembayaran.setRowHeight(30);

        jtblKredit.setAutoCreateRowSorter(true);
        jtblPembayaran.setAutoCreateRowSorter(true);
    }

    private void cek() {
        if (LoginDAO.role == 0) {
            jtblKredit.removeColumn(jtblKredit.getColumn("Command"));
        }
    }

    public void refresh() {
        refreshCBPembeli();
        refreshDataKredit(transaksiDAO.getTransaksiKredit());
    }

    public void refreshCBPembeli() {
        modelPembeli.removeAllElements();
        modelPembeli.addElement("All");
        for (Pembeli p : new PembeliDAO().getAllPembeli()) {
            modelPembeli.addElement(p);
        }
    }

    private void removeTableModel(DefaultTableModel model) {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    private void refreshDataKredit(List<Transaksi> list) {
        removeTableModel(modelTransaksiKredit);
        int totalSisaBayar = 0;
        double totalSisaBayarUSD = 0;
        for (Transaksi transaksi : list) {
            Kredit k = null;
            String jenisTransaksi = "Tunai";
            String statusPembayaran = "Lunas";
            if (!transaksi.getKredits().isEmpty()) {
                jenisTransaksi = "Kredit";
                Iterator<Kredit> it = transaksi.getKredits().iterator();
                while (it.hasNext()) {
                    k = it.next();
                    if (!k.getStatus()) {
                        statusPembayaran = "Belum Lunas";
                    }
                }
            }

            if (transaksi.isMata()) {
                double sisaBayar = transaksi.getHargaTotal() - new CicilanDAO().getTotalBayarById(k);
                totalSisaBayar += sisaBayar;
                modelTransaksiKredit.addRow(new Object[]{transaksi.getPembeli().getNama(), transaksi.getTglJual(), k.getTglAkhirPelunasan(), transaksi.getKurs(), formatIDR.format(transaksi.getHargaTotal()), formatIDR.format(sisaBayar), jenisTransaksi, statusPembayaran, k.getIdKredit()});
            } else {
                double sisaBayar = transaksi.getHargaTotalUsd() - new CicilanDAO().getTotalBayarById(k);
                totalSisaBayarUSD += sisaBayar;
                modelTransaksiKredit.addRow(new Object[]{transaksi.getPembeli().getNama(), transaksi.getTglJual(), k.getTglAkhirPelunasan(), transaksi.getKurs(), formatUSD.format(transaksi.getHargaTotalUsd()), formatUSD.format(sisaBayar), jenisTransaksi, statusPembayaran, k.getIdKredit()});
            }

        }
        jlblSisaBayar.setText("Total Sisa Bayar : " + formatIDR.format(totalSisaBayar) + " " + formatUSD.format(totalSisaBayarUSD));
    }

    private void refreshDataCicilan(List<Cicilan> list) {
        removeTableModel(modelCicilan);
        for (Cicilan cicilan : list) {
            modelCicilan.addRow(new Object[]{cicilan.getIdCicilan(), formatIDR.format(cicilan.getJumlahBayar()), cicilan.getTglCicilan(), cicilan.getJenis(), cicilan.getNoGiro()});
        }
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblKredit = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblPembayaran = new javax.swing.JTable();
        jcbPembeli = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jlblSisaBayar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaksi Kredit"));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jtblKredit.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblKredit);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Histori Pembayaran"));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jtblPembayaran.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtblPembayaran);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        jcbPembeli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbPembeli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbPembeliItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jcbPembeli, gridBagConstraints);

        jLabel1.setText("Pembeli");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel1, gridBagConstraints);

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jButton1, gridBagConstraints);

        jlblSisaBayar.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(jlblSisaBayar, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setBackground(java.awt.Color.yellow);
        jLabel2.setText("               ");
        jLabel2.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel3.setText(": Transaksi kredit yang telah melewati tanggal pelunasan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jPanel3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    new DialogPilihPembeli(null, true).setVisible(true);
    modelPembeli.setSelectedItem(DialogPilihPembeli.getSelectedPembeli());
}//GEN-LAST:event_jButton1ActionPerformed

private void jcbPembeliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbPembeliItemStateChanged

    if (modelPembeli.getSize() != 0) {
        if (modelPembeli.getSelectedItem().toString().equals("All")) {
            refreshDataKredit(transaksiDAO.getTransaksiKredit());
        } else {
            Pembeli p = (Pembeli) modelPembeli.getSelectedItem();
            refreshDataKredit(transaksiDAO.getTransaksiKreditbyPembeli(p));
        }
    }
}//GEN-LAST:event_jcbPembeliItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox jcbPembeli;
    private javax.swing.JLabel jlblSisaBayar;
    private javax.swing.JTable jtblKredit;
    private javax.swing.JTable jtblPembayaran;
    // End of variables declaration//GEN-END:variables

    class BayarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new DialogBayarCicilan(null, true, Integer.parseInt(bayarEditor.getCellEditorValue() + "")).setVisible(true);
            refreshDataKredit(transaksiDAO.getTransaksiKredit());
        }
    }

    class KreditListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                CicilanDAO dao = new CicilanDAO();

                int idKredit = (Integer) jtblKredit.getModel().getValueAt(jtblKredit.getSelectedRow(), 8);
                refreshDataCicilan(dao.getCicilanByKredit(new KreditDAO().getKreditById(idKredit)));
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }
    }

    class TableCicilanRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Date date = (Date) table.getValueAt(row, 2);
            if (row % 2 == 0) {
                component.setBackground(new Color(242, 242, 242));
            } else {
                component.setBackground(Color.white);
            }
            if (!date.after(new Date())) {
                component.setBackground(Color.yellow);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
