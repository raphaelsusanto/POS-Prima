/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prima.service;

import com.prima.dao.PembelianDAO;
import com.prima.entity.Pembelian;
import com.prima.entity.PembelianDetail;
import java.util.List;

/**
 *
 * @author raphael
 */
public class PembelianService {

    public void savePembelian(Pembelian pembelian, List<PembelianDetail> pembelianDetails) {
        PembelianDAO pembelianDAO = new PembelianDAO();
        String id=pembelianDAO.save(pembelian,pembelianDetails);
        
    }
}
