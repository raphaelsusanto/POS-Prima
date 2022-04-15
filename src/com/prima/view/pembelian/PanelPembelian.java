/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelPembelian.java
 *
 * Created on Oct 4, 2011, 12:38:49 PM
 */
package com.prima.view.pembelian;

import com.prima.dao.BarangDAO;
import com.prima.dao.PembelianDAO;
import com.prima.dao.SupplierDAO;
import com.prima.entity.Barang;
import com.prima.entity.Pembelian;
import com.prima.entity.PembelianDetail;
import com.prima.entity.Supplier;
import com.prima.service.Kurs;
import com.prima.service.KursFactory;
import com.prima.service.PembelianService;
import com.prima.view.DialogKonfirmasi;
import com.prima.view.barang.DialogPilihBarang;
import com.prima.view.barang.DialogTambahBarang;
import com.prima.view.model.ButtonBatalEditor;
import com.prima.view.model.ButtonBatalRenderer;
import com.prima.view.model.ButtonDeleteEditor;
import com.prima.view.model.ButtonDeleteRenderer;
import com.prima.view.supplier.DialogPilihSupplier;
import com.prima.view.supplier.DialogTambahSupplier;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class PanelPembelian extends javax.swing.JPanel {

    /** Creates new form PanelPembelian */
    private DefaultComboBoxModel cbBarangModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel cbSupplierModel = new DefaultComboBoxModel();
    private DefaultTableModel tableModel = new DefaultTableModel(null, new Object[]{"Barang", "Jumlah", "harga satuan", "harga", "command", "id"});
    private DefaultTableModel tableModelPembelian = new DefaultTableModel(null, new Object[]{"id", "tanggal", "kurs", "total", "Command", "Status"});
    private DefaultTableModel tableModelPembelianDetail = new DefaultTableModel(null, new Object[]{"id", "barang", "jumlah", "harga"});
    private ButtonDeleteRenderer renderer = new ButtonDeleteRenderer();
    private ButtonDeleteEditor editor = new ButtonDeleteEditor(new JCheckBox());
    private ButtonBatalEditor batalEditor = new ButtonBatalEditor(new JCheckBox());
    private ButtonBatalRenderer batalRenderer = new ButtonBatalRenderer();

    public PanelPembelian() {
        initComponents();
        init();
    }

    private void init() {
        jcbBarang.setModel(cbBarangModel);
        jcbSupplier.setModel(cbSupplierModel);
        jtblPembelian.setRowHeight(30);
        jtblPembelian.setModel(tableModel);
        jtblPembelian.removeColumn(jtblPembelian.getColumn("id"));
        jtblPembelian.getColumn("command").setCellRenderer(renderer);
        jtblPembelian.getColumn("command").setCellEditor(editor);
        editor.getButton().addActionListener(new DeleteRowListener());
        refreshCbBarang();
        refreshCbSupplier();

        jtblLIhatPembelian.setModel(tableModelPembelian);
        jtblLIhatPembelian.setRowHeight(30);
        jtblLIhatPembelian.getColumn("Command").setCellEditor(batalEditor);
        jtblLIhatPembelian.getColumn("Command").setCellRenderer(batalRenderer);
        jtblLIhatPembelian.getSelectionModel().addListSelectionListener(new PembelianListSelectionListener());
        batalEditor.getButton().addActionListener(new BatalPembelian());
        jtblLIhatPembelian.removeColumn(jtblLIhatPembelian.getColumn("Status"));
        jtblLIhatPembelian.setDefaultRenderer(Object.class, new TableTransaksiRenderer());
        refreshTbPembelian();

        jtblLIhatPembelianDetail.setModel(tableModelPembelianDetail);
        jtblLIhatPembelianDetail.setRowHeight(30);

        jtblLIhatPembelian.setAutoCreateRowSorter(true);
        jtblLIhatPembelianDetail.setAutoCreateRowSorter(true);
        jtblPembelian.setAutoCreateRowSorter(true);
    }

    private void clearTableModel(DefaultTableModel m) {
        int row = m.getRowCount();
        for (int i = 0; i < row; i++) {
            m.removeRow(0);
        }
    }

    private void refreshTbPembelian() {
        clearTableModel(tableModelPembelian);
        PembelianDAO dao = new PembelianDAO();
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        List<Pembelian> data = dao.getAllPembelian();
        for (Pembelian pembelian : data) {
            tableModelPembelian.addRow(new Object[]{pembelian.getIdPembelian(), pembelian.getTglBeli(), pembelian.getKurs(), format.format(pembelian.getHargaTotal()), pembelian.getIdPembelian(), pembelian.getStatus()});
        }
    }

    private void refreshCbSupplier() {
        cbSupplierModel.removeAllElements();
        for (Supplier object : new SupplierDAO().getAllSupplier()) {
            cbSupplierModel.addElement(object);
        }
    }

    private void refreshCbBarang() {
        cbBarangModel.removeAllElements();
        for (Object object : new BarangDAO().getAllBarang()) {
            cbBarangModel.addElement(object);
        }
    }

    public void refresh() {
        refreshCbBarang();
        refreshCbSupplier();
    }

    private double countTotal() {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        int row = tableModel.getRowCount();
        double total = 0;
        for (int i = 0; i < row; i++) {
            try {
                total += Double.parseDouble(format.parse(tableModel.getValueAt(i, 3).toString()).toString());
            } catch (Exception ex) {
                Logger.getLogger(PanelPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbSupplier = new javax.swing.JComboBox();
        jcbBarang = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPembelian = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jspJumlah = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jtfHarga = new com.prima.view.model.NumberTextbox();
        jbtnTambah = new javax.swing.JButton();
        jbtnSimpan = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        jlbTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jrbIDR = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblLIhatPembelian = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblLIhatPembelianDetail = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Supplier");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Barang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel2, gridBagConstraints);

        jcbSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jcbSupplier, gridBagConstraints);

        jcbBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jcbBarang, gridBagConstraints);

        jtblPembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblPembelian);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel3.setText("Jumlah");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel3, gridBagConstraints);

        jspJumlah.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jspJumlah, gridBagConstraints);

        jLabel6.setText("Harga per Kg/Liter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jtfHarga, gridBagConstraints);

        jbtnTambah.setText("Tambah");
        jbtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnTambahActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jbtnTambah, gridBagConstraints);

        jbtnSimpan.setText("Simpan");
        jbtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSimpanActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        jPanel1.add(jbtnSimpan, gridBagConstraints);

        jToolBar1.setRollover(true);
        jPanel1.add(jToolBar1, new java.awt.GridBagConstraints());

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 18));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Total :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabel4, gridBagConstraints);

        jlbTotal.setFont(new java.awt.Font("Ubuntu", 1, 18));
        jlbTotal.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(jlbTotal, gridBagConstraints);

        jLabel7.setText("Mata uang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel7, gridBagConstraints);

        buttonGroup1.add(jrbIDR);
        jrbIDR.setSelected(true);
        jrbIDR.setText("IDR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jrbIDR, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("USD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jRadioButton2, gridBagConstraints);

        jLabel8.setText("per Kg/Liter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel8, gridBagConstraints);

        jButton3.setText("Pilih");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jButton3, gridBagConstraints);

        jButton4.setText("Pilih");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jButton4, gridBagConstraints);

        jTabbedPane1.addTab("Tambah Pembelian", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembelian"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jtblLIhatPembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtblLIhatPembelian);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Pembelian"));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jtblLIhatPembelianDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jtblLIhatPembelianDetail);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel4, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel5.setBackground(new java.awt.Color(189, 201, 192));
        jLabel5.setText("               ");
        jLabel5.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel5, gridBagConstraints);

        jLabel9.setText(": Transaksi yang telah dibatalkan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jPanel5, gridBagConstraints);

        jTabbedPane1.addTab("Lihat Pembelian", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Tambah Pembelian");
    }// </editor-fold>//GEN-END:initComponents

private void jbtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnTambahActionPerformed
    if (((Integer) jspJumlah.getValue()).equals(new Integer(0)) || jtfHarga.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Data pembelian tidak valid");
    } else {
        Kurs k = KursFactory.getInstance();
        NumberFormat numberFormatEnglish = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat numberFormatInd = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        Barang barang = (Barang) jcbBarang.getSelectedItem();
        int jumlah = (Integer) jspJumlah.getValue();
        double hargaSatuan = Double.parseDouble(jtfHarga.getText());
        double hargaTotal = hargaSatuan * jumlah;
        if (!jrbIDR.isSelected()) {
            hargaTotal = hargaTotal * k.getKurs();
        }
        //"Barang", "Jumlah", "harga satuan", "harga", "command", "id"
        int i;
        for (i = 0; i < tableModel.getRowCount(); i++) {
            if (barang.equals(tableModel.getValueAt(i, 5))) {
                break;
            }

        }
        if (i != tableModel.getRowCount()) {
            int jmlh = (Integer) tableModel.getValueAt(i, 1);
            tableModel.setValueAt(jmlh + jumlah, i, 1);
            tableModel.setValueAt(numberFormatInd.format(hargaSatuan*(jmlh+jumlah)), i, 3);
        } else {
            tableModel.addRow(new Object[]{barang.getNama(), jumlah, (jrbIDR.isSelected()) ? numberFormatInd.format(hargaSatuan) : numberFormatEnglish.format(hargaSatuan), numberFormatInd.format(hargaTotal), jrbIDR.isSelected(), barang});
        }

        jlbTotal.setText(numberFormatInd.format(countTotal()));
    }
}//GEN-LAST:event_jbtnTambahActionPerformed

private void jbtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSimpanActionPerformed
    if (tableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Masukan barang yang akan dibeli!");
    } else {
        Kurs k = KursFactory.getInstance();
        NumberFormat formatID = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        NumberFormat formatUS = NumberFormat.getCurrencyInstance(Locale.US);
        Pembelian pembelian = new Pembelian();
        List<PembelianDetail> list = new ArrayList<PembelianDetail>();
        pembelian.setSupplier((Supplier) jcbSupplier.getSelectedItem());
        pembelian.setKurs((int) k.getKurs());
        pembelian.setHargaTotal((int) countTotal());
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                Boolean IDR = (Boolean) tableModel.getValueAt(i, 4);
                Number hargaSatuan;
                if (IDR) {
                    hargaSatuan = formatID.parse(tableModel.getValueAt(i, 2).toString());
                } else {
                    hargaSatuan = formatUS.parse(tableModel.getValueAt(i, 2).toString());
                }
                list.add(new PembelianDetail("", (Barang) tableModel.getValueAt(i, 5), pembelian, (Integer) tableModel.getValueAt(i, 1), Double.parseDouble(hargaSatuan.toString()), IDR));
            } catch (ParseException ex) {
                Logger.getLogger(PanelPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        new PembelianService().savePembelian(pembelian, list);
        clearTableModel(tableModel);
        jlbTotal.setText(formatID.format(countTotal()));
    }

}//GEN-LAST:event_jbtnSimpanActionPerformed

private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
    refreshTbPembelian();
    // TODO add your handling code here:
}//GEN-LAST:event_jTabbedPane1StateChanged

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    new DialogPilihSupplier(null, true).setVisible(true);
    jcbSupplier.getModel().setSelectedItem(DialogPilihSupplier.getSelectedSupplier());
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    new DialogPilihBarang(null, true).setVisible(true);
    jcbBarang.getModel().setSelectedItem(DialogPilihBarang.getSelectedBarang());
}//GEN-LAST:event_jButton4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbtnSimpan;
    private javax.swing.JButton jbtnTambah;
    private javax.swing.JComboBox jcbBarang;
    private javax.swing.JComboBox jcbSupplier;
    private javax.swing.JLabel jlbTotal;
    private javax.swing.JRadioButton jrbIDR;
    private javax.swing.JSpinner jspJumlah;
    private javax.swing.JTable jtblLIhatPembelian;
    private javax.swing.JTable jtblLIhatPembelianDetail;
    private javax.swing.JTable jtblPembelian;
    private com.prima.view.model.NumberTextbox jtfHarga;
    // End of variables declaration//GEN-END:variables

    class DeleteRowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            NumberFormat numberFormatInd = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            tableModel.removeRow((Integer) editor.getCellEditorValue());
            jlbTotal.setText(numberFormatInd.format(countTotal()));
        }
    }

    class PembelianListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                PembelianDAO dao = new PembelianDAO();
                NumberFormat format;
                String idPembelian = tableModelPembelian.getValueAt(jtblLIhatPembelian.getSelectedRow(), 0).toString();
                Pembelian pembelian = dao.getPembelianById(idPembelian);
                clearTableModel(tableModelPembelianDetail);
                Iterator<PembelianDetail> i = pembelian.getPembelianDetails().iterator();
                while (i.hasNext()) {
                    PembelianDetail p = i.next();
                    if (p.getMataUang()) {
                        format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
                    } else {
                        format = NumberFormat.getCurrencyInstance(Locale.US);
                    }
                    tableModelPembelianDetail.addRow(new Object[]{p.getIdPembelianDetail(), p.getBarang().getNama(), p.getJumlah(), format.format(p.getHarga())});
                }
            } catch (Exception ex) {
            }
        }
    }

    class BatalPembelian implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PembelianDAO pembelianDAO = new PembelianDAO();
            Pembelian p = pembelianDAO.getPembelianById(batalEditor.getCellEditorValue() + "");
            //   int option = JOptionPane.showConfirmDialog(null, "Apakah anda yakin membatalkan pembelian " + p.getIdPembelian() + " ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            new DialogKonfirmasi(null, true, p.getIdPembelian()).setVisible(true);
            if (DialogKonfirmasi.cek) {
                pembelianDAO.cancelPembelian(p);
                refreshTbPembelian();
            }
        }
    }

    class TableTransaksiRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Boolean statusTransaksi = (Boolean) tableModelPembelian.getValueAt(row, 5);

            if (row % 2 == 0) {
                component.setBackground(new Color(242, 242, 242));
            } else {
                component.setBackground(Color.white);
            }

            if (!statusTransaksi) {
                component.setBackground(new Color(189, 201, 192));

            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
