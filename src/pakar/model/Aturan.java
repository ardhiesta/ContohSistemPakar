/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakar.model;

import java.util.ArrayList;

/**
 *
 * @author linuxluv
 */
public class Aturan {

    private String idAturan;
    private ArrayList<Gejala> arrGejala;
    private KelompokTitikAkupuntur titikAkupuntur;
    private double cfValue;

    public String getIdAturan() {
        return idAturan;
    }

    public void setIdAturan(String idAturan) {
        this.idAturan = idAturan;
    }

    public ArrayList<Gejala> getArrGejala() {
        return arrGejala;
    }

    public void setArrGejala(ArrayList<Gejala> arrGejala) {
        this.arrGejala = arrGejala;
    }

    public KelompokTitikAkupuntur getTitikAkupuntur() {
        return titikAkupuntur;
    }

    public void setTitikAkupuntur(KelompokTitikAkupuntur titikAkupuntur) {
        this.titikAkupuntur = titikAkupuntur;
    }

    public double getCfValue() {
        return cfValue;
    }

    public void setCfValue(double cfValue) {
        this.cfValue = cfValue;
    }
    
    
}
