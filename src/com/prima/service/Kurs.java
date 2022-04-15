/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;

import java.io.Serializable;

/**
 *
 * @author raphael
 */
public class Kurs implements Serializable{

    private double kurs = 0;
    private boolean auto = true;
//    private Kurs kursInstance;

    private Kurs() {
    }

//    public Kurs kursFactory() {
//        XStream xStream = new XStream();
//        kursInstance = ((Kurs) xStream.fromXML(new File("config.xml")));
//        if (kursInstance.isAuto()) {
//            kursInstance.setKurs(conversionRate(Currency.USD, Currency.IDR));
//        }
//        return kursInstance;
//    }

//    public void run() {
//        if (kurs == 0) {
//            if (isAuto) {
//                kurs = conversionRate(Currency.USD, Currency.IDR);
//            }
//        }
//    
   

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }


    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
}
