/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelLihatTransaksi.java
 *
 * Created on Oct 18, 2011, 9:24:45 AM
 */
package com.prima.view.transaksi;

import com.prima.dao.TransaksiDAO;
import com.prima.entity.DetailTransaksi;
import com.prima.entity.Kredit;
import com.prima.entity.Pembeli;
import com.prima.entity.Transaksi;
import com.prima.view.DialogKonfirmasi;
import com.prima.view.model.ButtonBatalEditor;
import com.prima.view.model.ButtonBatalRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class DialogLihatTransaksiPembeli extends JDialog{

    /** Creates new form PanelLihatTransaksi */
    private DefaultTableModel modelTransaksi = new DefaultTableModel(null, new Object[]{"ID Transaksi", "Tanggal Jual", "Kurs", "Harga Total", "Jenis Pembayaran", "Status Pembayaran", "Command", "Status"});
    private DefaultTableModel modelDetailTransaksi = new DefaultTableModel(null, new Object[]{"ID Detail Transaksi", "Barang", "Jumlah", "Harga","SubTotal"});
    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    private NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);
    private ButtonBatalEditor batalEditor = new ButtonBatalEditor(new JCheckBox());
    private ButtonBatalRenderer batalRenderer = new ButtonBatalRenderer();

    public DialogLihatTransaksiPembeli(java.awt.Frame parent, boolean modal,Pembeli p) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);
        init(p);
    }

    private void init(Pembeli p) {
        jtblTransaksi.setModel(modelTransaksi);
        jtblTransaksi.setRowHeight(30);
        jtblDetailTransaksi.setModel(modelDetailTransaksi);
        jtblDetailTransaksi.setRowHeight(30);
        jtblTransaksi.getSelectionModel().addListSelectionListener(new TransaksiListSelectionListener());
        jtblTransaksi.removeColumn(jtblTransaksi.getColumn("Status"));
        jtblTransaksi.getColumn("Command").setCellEditor(batalEditor);
        jtblTransaksi.getColumn("Command").setCellRenderer(batalRenderer);
        jtblTransaksi.setDefaultRenderer(Object.class, new TableTransaksiRenderer());
        batalEditor.getButton().addActionListener(new BatalTransaksi());
        refreshTableTransaksi(transaksiDAO.getTransaksiByPembeli(p));
        
        jtblDetailTransaksi.setAutoCreateRowSorter(true);
        jtblTransaksi.setAutoCreateRowSorter(true);
        
         jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaksi "+p.getNama()));
    }

    public void refresh() {
        refreshTableTransaksi(transaksiDAO.getAllTransaksi());
    }

    private void removeTableModel(DefaultTableModel model) {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    private void refreshTableDetailTransaksi(List<DetailTransaksi> detailTransaksi, int kurs) {
        removeTableModel(modelDetailTransaksi);
        for (DetailTransaksi dt : detailTransaksi) {
            String harga;
            String total="";
            double subTotal=0;
            if (dt.getTransaksi().isMata()) {
                harga = formatIDR.format(dt.getHarga());
                subTotal=((double)(dt.getHarga()*dt.getJumlah()));
                total=formatIDR.format(subTotal);
            }else{
                harga= formatUSD.format(dt.getHarga());
                subTotal=((double)(dt.getHarga()*dt.getJumlah()));
                total=formatUSD.format(subTotal);
            }
            modelDetailTransaksi.addRow(new Object[]{dt.getIdDetailTransaksi(), dt.getBarang(), dt.getJumlah(), harga,total});
        }
    }

    private void refreshTableTransaksi(List<Transaksi> dataTransaksi) {
        removeTableModel(modelTransaksi);
        for (Transaksi transaksi : dataTransaksi) {
            String jenisTransaksi = "Tunai";
            String statusPembayaran = "Lunas";
            if (!transaksi.getKredits().isEmpty()) {
                jenisTransaksi = "Kredit";
                Iterator<Kredit> it = transaksi.getKredits().iterator();
                while (it.hasNext()) {
                    Kredit k = it.next();
                    if (!k.getStatus()) {
                        statusPembayaran = "Belum Lunas";
                    }
                }
            }
            if (transaksi.isMata()) {
                modelTransaksi.addRow(new Object[]{transaksi.getIdTransaksi(), transaksi.getTglJual(), transaksi.getKurs(), formatIDR.format(transaksi.getHargaTotal()), jenisTransaksi, statusPembayaran, transaksi.getIdTransaksi(), transaksi.getStatus()});
            }else{
                modelTransaksi.addRow(new Object[]{transaksi.getIdTransaksi(), transaksi.getTglJual(), transaksi.getKurs(), formatUSD.format(transaksi.getHargaTotalUsd()), jenisTransaksi, statusPembayaran, transaksi.getIdTransaksi(), transaksi.getStatus()});
            }
            
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblTransaksi = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblDetailTransaksi = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaksi"));
        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Transaksi"));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jtblDetailTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtblDetailTransaksi);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(189, 201, 192));
        jLabel1.setText("               ");
        jLabel1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel1, gridBagConstraints);

        jLabel2.setText(": Transaksi yang telah dibatalkan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(jPanel3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtblDetailTransaksi;
    private javax.swing.JTable jtblTransaksi;
    // End of variables declaration//GEN-END:variables

    class TransaksiListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                Transaksi t = transaksiDAO.getTransaksiById(modelTransaksi.getValueAt(jtblTransaksi.getSelectedRow(), 0) + "");
                refreshTableDetailTransaksi(transaksiDAO.getDetailTransaksiByTransaksi(t),t.getKurs());
            } catch (Exception ex) {
               // ex.printStackTrace();
            }

        }
    }

    class BatalTransaksi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Transaksi transaksi = transaksiDAO.getTransaksiById(batalEditor.getCellEditorValue() + "");
            new DialogKonfirmasi(null, true, transaksi.getIdTransaksi()).setVisible(true);
            //int option = JOptionPane.showConfirmDialog(null, "Apakah anda yakin membatalkan transaksi " + transaksi.getIdTransaksi() + "?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (DialogKonfirmasi.cek) {
                transaksiDAO.cancelTransaksi(transaksi);
                refreshTableTransaksi(transaksiDAO.getAllTransaksi());
            } 
        }
    }

    class TableTransaksiRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Boolean statusTransaksi = (Boolean) modelTransaksi.getValueAt(row, 7);
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
