/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelLaporanStockBarang.java
 *
 * Created on Oct 23, 2011, 8:00:47 PM
 */
package com.prima.view.laporan;

import com.prima.dao.BarangDAO;
import com.prima.entity.Barang;
import com.prima.view.barang.DialogPilihBarang;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author raphael
 */
public class PanelLaporanDetailStockBarang extends javax.swing.JPanel {

    /** Creates new form PanelLaporanStockBarang */
    private DefaultComboBoxModel cbModel = new DefaultComboBoxModel();

    public PanelLaporanDetailStockBarang() {
        initComponents();
        initData();
    }

    public void initData() {
        refreshCBBarang();
        jcbBarang.setModel(cbModel);

    }

    public void refreshCBBarang() {
        cbModel.removeAllElements();
        for (Barang barang : new BarangDAO().getAllBarang()) {
            cbModel.addElement(barang);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    public JasperPrint createReport() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("in", "ID"));
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder TitleStyle = stl.style(boldStyle).setFontSize(20);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

        //                                                           title,     field name     data type
        TextColumnBuilder<String> nameColumn = col.column("Nama Pembeli/Supplier", "nama", type.stringType());
        TextColumnBuilder<Date> dateColumn = col.column("Tanggal", "tanggal", type.dateYearToMinuteType());
        TextColumnBuilder<String> buyColumn = col.column("Jumlah Beli", "beli", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
        TextColumnBuilder<Double> sellColumn = col.column("Jumlah Jual", "jual", type.doubleType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
        TextColumnBuilder<Double> saldoColumn = col.column("Saldo", "saldo", type.doubleType()).setHorizontalAlignment(HorizontalAlignment.CENTER);

        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.") //sets the fixed width of a column, width = 2 * character width
                .setFixedColumns(2).setHorizontalAlignment(HorizontalAlignment.CENTER);
        //ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.smallerOrEquals(buyColumn, 5)).setBackgroundColor(Color.yellow);
        JasperPrint jPrint = null;
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        try {
            jPrint = report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle).highlightDetailEvenRows().columns(//add columns
                    rowNumberColumn, nameColumn, dateColumn,buyColumn,sellColumn,saldoColumn).title(cmp.verticalList().add(
                    cmp.text("PD.Prima").setStyle(TitleStyle),
                    cmp.text("Detail Stock "+jcbBarang.getSelectedItem().toString()).setStyle(stl.style().setFontSize(18)).setHorizontalAlignment(HorizontalAlignment.LEFT),
                   
                    cmp.text("tanggal cetak "+dateFormat.format(new Date()))))
    
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    .setDataSource(createDataSource())//set datasource
                    .toJasperPrint();
        } catch (DRException e) {
            e.printStackTrace();
        }
        return jPrint;
    }
//
    private JRDataSource createDataSource() {
        BarangDAO barangDAO = new BarangDAO();
        List<Barang> list = barangDAO.getAllBarang();
        DataSource dataSource = new DataSource("nama", "tanggal", "beli","jual","saldo");
        double saldo=0;
        for (Object o : barangDAO.getDetailStock((Barang)jcbBarang.getSelectedItem())) {
            Object temp[]=(Object[])o;
            saldo+=Integer.parseInt(temp[3].toString());
            saldo-=(Double)temp[4];
            dataSource.add(temp[2],temp[1],temp[3].toString(),temp[4],saldo);
        }

        return dataSource;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbBarang = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Barang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabel3, gridBagConstraints);

        jcbBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jcbBarang, gridBagConstraints);

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jButton1, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    new DialogPilihBarang(null, true).setVisible(true);
    cbModel.setSelectedItem(DialogPilihBarang.getSelectedBarang());
}//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox jcbBarang;
    // End of variables declaration//GEN-END:variables

}
