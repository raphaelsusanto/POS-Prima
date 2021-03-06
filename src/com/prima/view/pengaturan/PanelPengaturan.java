/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelPengaturan.java
 *
 * Created on Oct 5, 2011, 9:38:08 AM
 */
package com.prima.view.pengaturan;

import com.prima.dao.LoginDAO;
import com.prima.service.Kurs;
import com.prima.service.KursFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author raphael
 */
public class PanelPengaturan extends javax.swing.JPanel {

    private KursFactory factory = new KursFactory();

    /** Creates new form PanelPengaturan */
    public PanelPengaturan() {
        initComponents();
        init();
    }

    private void init() {
        try {
            factory.start();
            Thread.sleep(1000);
            Kurs k = KursFactory.getInstance();
            if (k.isAuto()) {
                jrbAuto.setSelected(true);
            } else {
                jrbManual.setSelected(true);
                jtfKurs.setText(k.getKurs() + "");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelPengaturan.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jrbAuto = new javax.swing.JRadioButton();
        jrbManual = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jtfKurs = new com.prima.view.model.NumberTextbox();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jpassLama = new javax.swing.JPasswordField();
        jpassBaru1 = new javax.swing.JPasswordField();
        jpassBaru2 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(jrbAuto);
        jrbAuto.setSelected(true);
        jrbAuto.setText("Nilai tukar rupiah terhadap dolar US akan otomastis ditentukan oleh aplikasi (membutuhkan koneksi internet)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(jrbAuto, gridBagConstraints);

        buttonGroup1.add(jrbManual);
        jrbManual.setText("Nilai tukar rupiah terhadap dolar US ditentukan secara langsung oleh pengguna");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(jrbManual, gridBagConstraints);

        jLabel1.setText("USD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(jtfKurs, gridBagConstraints);

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jButton1, gridBagConstraints);

        jLabel2.setText("IDR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel4, gridBagConstraints);

        jTabbedPane1.addTab("Nilai Tukar", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Masukan password lama");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Masukan password baru");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Masukan kembali password baru");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel7, gridBagConstraints);

        jpassLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpassLamaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jpassLama, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jpassBaru1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jpassBaru2, gridBagConstraints);

        jButton2.setText("Ubah Password");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel2.add(jButton2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jLabel8, gridBagConstraints);

        jTabbedPane1.addTab("Password", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    if (jrbManual.isSelected() && jtfKurs.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Kurs harus diisi!");
    } else {
        KursFactory.getInstance().setAuto(jrbAuto.isSelected());
        if (!jtfKurs.getText().trim().equals("")) {
            KursFactory.getInstance().setKurs(Double.parseDouble(jtfKurs.getText()));
        }
        KursFactory.writeConfig();

        JOptionPane.showMessageDialog(null, "Pengaturan berhasil disimpan");
    }


}//GEN-LAST:event_jButton1ActionPerformed

private void jpassLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpassLamaActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jpassLamaActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    LoginDAO loginDAO = new LoginDAO();
    if (jpassLama.getPassword().length == 0 || jpassBaru1.getPassword().length == 0 || jpassBaru2.getPassword().length == 0) {
        JOptionPane.showMessageDialog(null, "Password harus diisi!");
    } else if (!loginDAO.checkLogin(LoginDAO.username, new String(jpassLama.getPassword()))) {
        JOptionPane.showMessageDialog(null, "Password lama anda salah.");
    } else if (!new String(jpassBaru1.getPassword()).equals(new String(jpassBaru2.getPassword()))) {
        JOptionPane.showMessageDialog(null, "Password baru yang dimasukan salah.");
    } else {
        loginDAO.changePassword(new String(jpassBaru1.getPassword()));
        JOptionPane.showMessageDialog(null, "Password berhasil diubah");
    }
}//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPasswordField jpassBaru1;
    private javax.swing.JPasswordField jpassBaru2;
    private javax.swing.JPasswordField jpassLama;
    private javax.swing.JRadioButton jrbAuto;
    private javax.swing.JRadioButton jrbManual;
    private com.prima.view.model.NumberTextbox jtfKurs;
    // End of variables declaration//GEN-END:variables
}
