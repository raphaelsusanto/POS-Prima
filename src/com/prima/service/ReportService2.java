/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;

import com.prima.util.HibernateUtil;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author raphael
 */
public class ReportService2 {

    String path = "";
    HashMap<String, Object> parameter = new HashMap<String, Object>();

    public ReportService2(String path, HashMap parameter) {
        this.path = path;
        this.parameter = parameter;
    }

    public JasperPrint getReport() {
        JasperPrint JPrint = null;
        try {
            Connection conn = HibernateUtil.getSessionFactory().openSession().connection();
            JasperReport jprt = JasperCompileManager.compileReport(path);
            JPrint = JasperFillManager.fillReport(jprt, parameter, conn);
        } catch (JRException ex) {
            Logger.getLogger(ReportService2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JPrint;
    }

    public void start() {
        try {
            Connection conn = HibernateUtil.getSessionFactory().openSession().connection();
            JasperReport jprt = JasperCompileManager.compileReport(path);
            JasperPrint JPrint = JasperFillManager.fillReport(jprt, parameter, conn);
            JasperViewer.viewReport(JPrint, false);

            //JasperPrintManager.printPage(JPrint, 0, false);
        } catch (JRException ex) {
            ex.printStackTrace();
            // Logger.getLogger(PanelLihatJadwalSiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
