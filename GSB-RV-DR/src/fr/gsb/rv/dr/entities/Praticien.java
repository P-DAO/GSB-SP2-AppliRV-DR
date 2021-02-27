/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.entities;

import java.time.LocalDate;

/**
 *
 * @author developpeur
 */
public class Praticien {
    private int pra_num;
    private String pra_nom;
    private String pra_ville;
    private double pra_coefnotoriete;
    private LocalDate pra_dateDernierVisite;
    private int dernierCoefConfiance;
    private int dernierCoefNotoriete;

    
    public Praticien(int pra_num, String pra_nom, String pra_ville, double pra_coefnotoriete, LocalDate pra_dateDernierVisite, int dernierCoefConfiance) {
        this.pra_num = pra_num;
        this.pra_nom = pra_nom;
        this.pra_ville = pra_ville;
        this.pra_coefnotoriete = pra_coefnotoriete;
        this.pra_dateDernierVisite = pra_dateDernierVisite;
        this.dernierCoefConfiance = dernierCoefConfiance;
        
    }

    public Praticien(){
        
    }
    
    public int getPra_num() {
        return pra_num;
    }

    public void setPra_num(int pra_num) {
        this.pra_num = pra_num;
    }

    public String getPra_nom() {
        return pra_nom;
    }

    public void setPra_nom(String pra_nom) {
        this.pra_nom = pra_nom;
    }

    public String getPra_ville() {
        return pra_ville;
    }

    public void setPra_ville(String pra_ville) {
        this.pra_ville = pra_ville;
    }

    public double getPra_coefnotoriete() {
        return pra_coefnotoriete;
    }

    public void setPra_coefnotoriete(double pra_coefnotoriete) {
        this.pra_coefnotoriete = pra_coefnotoriete;
    }

    public LocalDate getPra_dateDernierVisite() {
        return pra_dateDernierVisite;
    }

    public void setPra_dateDernierVisite(LocalDate pra_dateDernierVisite) {
        this.pra_dateDernierVisite = pra_dateDernierVisite;
    }

    public int getDernierCoefConfiance() {
        return dernierCoefConfiance;
    }

    public void setDernierCoefConfiance(int dernierCoefConfiance) {
        this.dernierCoefConfiance = dernierCoefConfiance;
    }
    
    public int getDernierCoefNotoriete() {
        return dernierCoefNotoriete;
    }

    public void setDernierCoefNotoriete(int dernierCoefNotoriete) {
        this.dernierCoefNotoriete = dernierCoefNotoriete;
    }


    @Override
    public String toString() {
        return "Praticien{" + "pra_num=" + pra_num + ", pra_nom=" + pra_nom + ", pra_ville=" + pra_ville + ", pra_coefnotoriete=" + pra_coefnotoriete + ", pra_dateDernierVisite=" + pra_dateDernierVisite + ", dernierCoefConfiance=" + dernierCoefConfiance + '}';
    }

    
}
