/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;

import com.prima.view.FrameUtama;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.webservicex.Currency;

/**
 *
 * @author raphael
 */
public class KursFactory extends Thread {

    private static Kurs kursInstance;

    public static Kurs getInstance() {

        return kursInstance;
    }

    @Override
    public void run() {
        super.run();
        //  if (kursInstance == null) {
        XStream xStream = new XStream();
        kursInstance = ((Kurs) xStream.fromXML(new File("config.xml")));
        while (kursInstance.isAuto()) {
            try {
                kursInstance.setKurs(conversionRate(Currency.USD, Currency.IDR));
                Thread.sleep(100000);
            } catch (InterruptedException ex) {
                Logger.getLogger(KursFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                kursInstance.setAuto(false);
                JOptionPane.showMessageDialog(null,"Tidak dapat terhubung ke server. \n nilai kurs akan ditentukan oleh nilai sebelumnya.\n pengaturan nilai tukar akan berubah ke manual");
                
            }
        }

        //}
    }

    public static void writeConfig() {
        File file = new File("config.xml");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            XStream xStream = new XStream();
            String a = xStream.toXML(kursInstance);
            bufferedWriter.write(a);
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Kurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static double conversionRate(net.webservicex.Currency fromCurrency, net.webservicex.Currency toCurrency) throws Exception {
        net.webservicex.CurrencyConvertor service = new net.webservicex.CurrencyConvertor();
        net.webservicex.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }
//    public static void main(String[] args) {
//        System.out.println(KursFactory.getInstance().getKurs() + "");
//    }
}
