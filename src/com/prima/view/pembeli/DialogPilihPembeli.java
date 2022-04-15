/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogPilihPembeli.java
 *
 * Created on Nov 12, 2011, 1:13:30 PM
 */
package com.prima.view.pembeli;

import com.prima.dao.PembeliDAO;
import com.prima.entity.Pembeli;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raphael
 */
public class DialogPilihPembeli extends javax.swing.JDialog {

    private String[] header = {"ID Pembeli", "Nama", "Alamat", "Telp", "Fax"};
    private DefaultTableModel modelPembeli = new DefaultTableModel(null, header);
    private static Pembeli selectedPembeli = null;

    /** Creates new form DialogPilihPembeli */
    public DialogPilihPembeli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        selectedPembeli = null;
    }

    private void init() {
        jtfCari.getDocument().addDocumentListener(new PembeliSearchListener());
        jtblPembeli.setModel(modelPembeli);
        jtblPembeli.setRowHeight(30);
        jtblPembeli.setAutoCreateRowSorter(true);
        refreshData(new PembeliDAO().getAllPembeli());
        
        this.setLocationRelativeTo(null);
    }

    private void refreshData(List<Pembeli> listBarang) {
        removeTableModel(modelPembeli);
        for (Pembeli b : listBarang) {
            modelPembeli.addRow(new Object[]{b.getIdPembeli(), b.getNama(), b.getAlamat(), b.getTelp(), b.getEmail()});
        }
    }

    private void removeTableModel(DefaultTableModel model) {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public static Pembeli getSelectedPembeli() {
        return selectedPembeli;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPembeli = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jtfCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pilih Pembeli");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jtblPembeli.setModel(new javax.swing.table.DefaultTableModel(
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
        jtblPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblPembeliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblPembeli);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jButton1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jtfCari, gridBagConstraints);

        jLabel1.setText("Cari");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    try {
        int idPembeli = (Integer) jtblPembeli.getValueAt(jtblPembeli.getSelectedRow(), 0);
        selectedPembeli = new PembeliDAO().getPembeliById(idPembeli);
        this.setVisible(false);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Pilih pembeli terlebih dahulu!");
    }

}//GEN-LAST:event_jButton1ActionPerformed

private void jtblPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblPembeliMouseClicked
    try {
        if (evt.getClickCount() == 2) {
            System.out.println(evt.getClickCount());
            int idPembeli = (Integer) jtblPembeli.getValueAt(jtblPembeli.getSelectedRow(), 0);
            selectedPembeli = new PembeliDAO().getPembeliById(idPembeli);
            this.setVisible(false);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Pilih pembeli terlebih dahulu!");
    }

}//GEN-LAST:event_jtblPembeliMouseClicked

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtblPembeli;
    private javax.swing.JTextField jtfCari;
    // End of variables declaration//GEN-END:variables

    class PembeliSearchListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            refreshData(new PembeliDAO().searchPembeli(jtfCari.getText()));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            refreshData(new PembeliDAO().searchPembeli(jtfCari.getText()));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
             refreshData(new PembeliDAO().searchPembeli(jtfCari.getText()));
        }
    
    }
}