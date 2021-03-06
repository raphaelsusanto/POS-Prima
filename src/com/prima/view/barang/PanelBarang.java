/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelBarang.java
 *
 * Created on Sep 23, 2011, 4:50:15 PM
 */
package com.prima.view.barang;

import com.prima.dao.BarangDAO;
import com.prima.dao.LoginDAO;
import com.prima.entity.Barang;
import com.prima.view.model.ButtonEditor;
import com.prima.view.model.ButtonRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class PanelBarang extends javax.swing.JPanel {

    private String[] header = {"ID Barang", "nama", "Harga IDR", "Harga USD","stock","Satuan jual", "command"};
    private DefaultTableModel tableModel = new DefaultTableModel(null, header);
    private DialogUbahBarang dialog;
    private ButtonEditor btnEditor = new ButtonEditor(new JCheckBox());

    /** Creates new form PanelBarang */
    public PanelBarang() {
        initComponents();
        init();
        refreshData(new BarangDAO().getAllBarang());
    }

    private void init() {
        jtfSearch.getDocument().addDocumentListener(new DocumentListenerSearchBarang());
        jtblBarang.setAutoCreateRowSorter(true);
        jtblBarang.setRowHeight(25);
        jtblBarang.setModel(tableModel);
        jtblBarang.getColumn("command").setCellRenderer(new ButtonRenderer());
        btnEditor.getButton().addActionListener(new ButtonEditorListener());
        jtblBarang.getColumn("command").setCellEditor(btnEditor);
        jtblBarang.setDefaultRenderer(Object.class, new TableBarangRenderer());
        jtblBarang.setAutoCreateRowSorter(true);
        
//        if (LoginDAO.role==0) {
//            jtblBarang.removeColumn(jtblBarang.getColumn("command"));
//            jButton2.setEnabled(false);
//        }
    }

    private void refreshData(List<Barang> listBarang) {
        NumberFormat formatID = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        NumberFormat formatUS = NumberFormat.getCurrencyInstance(Locale.US);
        int row = tableModel.getRowCount();
        for (int i = 0; i < row; i++) {
            tableModel.removeRow(0);
        }
        String satuanJual = "";

        for (Barang b : listBarang) {
            if (b.getSatuanJual()) {
                satuanJual = "Kg";
            } else {
                satuanJual = "Liter";
            }
            tableModel.addRow(new Object[]{b.getIdBarang(), b.getNama(),formatID.format( b.getHargaIdr()),formatUS.format(b.getHargaUsd()), b.getStock(), satuanJual, b.getIdBarang()});
        }
    }

    public void refresh() {
        BarangDAO dao = new BarangDAO();
        refreshData(dao.getAllBarang());
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

        jLabel1 = new javax.swing.JLabel();
        jtfSearch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblBarang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        jLabel1.setText("Cari");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 18);
        add(jtfSearch, gridBagConstraints);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus.png"))); // NOI18N
        jButton2.setText("Tambah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(jButton2, gridBagConstraints);

        jtblBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblBarang);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel3.setBackground(java.awt.Color.yellow);
        jLabel3.setForeground(java.awt.Color.yellow);
        jLabel3.setText("                ");
        jLabel3.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText(": Barang yang memiliki stock kurang dari 5Kg/Liter");
        jPanel1.add(jLabel4, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    DialogTambahBarang d = new DialogTambahBarang(null, true);
    d.setVisible(true);
    refreshData(new BarangDAO().getAllBarang());
}//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtblBarang;
    private javax.swing.JTextField jtfSearch;
    // End of variables declaration//GEN-END:variables

    public class ButtonEditorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog = new DialogUbahBarang(null, true, Integer.parseInt(btnEditor.getCellEditorValue().toString()));
            dialog.setVisible(true);
            refreshData(new BarangDAO().getAllBarang());
        }
    }

    public class DocumentListenerSearchBarang implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            refreshData(new BarangDAO().searchBarang(jtfSearch.getText()));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            refreshData(new BarangDAO().searchBarang(jtfSearch.getText()));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            refreshData(new BarangDAO().searchBarang(jtfSearch.getText()));
        }
    }

    class TableBarangRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Double statusTransaksi = (Double) tableModel.getValueAt(row, 4);
            if (row%2==0) {
                component.setBackground(new Color(242, 242, 242));
            }else{
                 component.setBackground(Color.white);
            }
            if (statusTransaksi < 5) {
                component.setBackground(Color.yellow);
            } 
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
