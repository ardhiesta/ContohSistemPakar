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
public class Rule {

    private String idRule;
    private ArrayList<Gejala> arrGejala;
    private TitikAkupuntur titikAkupuntur;
    private double cfValue;

    public String getIdRule() {
        return idRule;
    }

    public void setIdRule(String idRule) {
        this.idRule = idRule;
    }

    public ArrayList<Gejala> getArrGejala() {
        return arrGejala;
    }

    public void setArrGejala(ArrayList<Gejala> arrGejala) {
        this.arrGejala = arrGejala;
    }

    public TitikAkupuntur getTitikAkupuntur() {
        return titikAkupuntur;
    }

    public void setTitikAkupuntur(TitikAkupuntur titikAkupuntur) {
        this.titikAkupuntur = titikAkupuntur;
    }

    public double getCfValue() {
        return cfValue;
    }

    public void setCfValue(double cfValue) {
        this.cfValue = cfValue;
    }
    
    
}
