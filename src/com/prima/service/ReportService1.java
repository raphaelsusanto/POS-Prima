/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;

import com.prima.util.HibernateUtil;
import java.sql.Connection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author raphael
 */
public class ReportService1 extends Thread {

    String path = "";
    HashMap<String, Object> parameter = new HashMap<String, Object>();

    public ReportService1(String path, HashMap parameter) {
        this.path = path;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        JasperPrint JPrint;
        try {
            Connection conn = HibernateUtil.getSessionFactory().openSession().connection();
            JasperReport jprt = JasperCompileManager.compileReport(path);
            JPrint = JasperFillManager.fillReport(jprt, parameter, conn);
            JasperViewer.viewReport(JPrint, false);
        } catch (JRException ex) {
            ex.printStackTrace();
            // Logger.getLogger(PanelLihatJadwalSiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
