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
public class RapportVisite {
    private int rap_num;
    private LocalDate rap_date_visite;
    private LocalDate rap_date_saisie;
    private String rap_bilan;
    private int motif_num;
    private int rap_coeff_confiance;
    private boolean rap_lu;
    private Visiteur visiteur;
    private Praticien praticien;
    
    public RapportVisite(int rap_num, LocalDate rap_date_visite, LocalDate rap_date_saisie, String rap_bilan, int motif_num, int rap_coeff_confiance, boolean rap_lu, Visiteur visiteur, Praticien praticien){
        this.rap_num = rap_num;
        this.rap_date_visite = rap_date_visite;
        this.rap_date_saisie = rap_date_saisie;
        this.rap_bilan = rap_bilan;
        this.motif_num = motif_num;
        this.rap_coeff_confiance = rap_coeff_confiance;
        this.rap_lu = rap_lu;
        this.visiteur = visiteur;
        this.praticien = praticien;
    }
    
    public RapportVisite(){
        
    }

    public int getRap_num() {
        return rap_num;
    }

    public void setRap_num(int rap_num) {
        this.rap_num = rap_num;
    }

    public LocalDate getRap_date_visite() {
        return rap_date_visite;
    }

    public void setRap_date_visite(LocalDate rap_date_visite) {
        this.rap_date_visite = rap_date_visite;
    }

    public LocalDate getRap_date_saisie() {
        return rap_date_saisie;
    }

    public void setRap_date_saisie(LocalDate rap_date_saisie) {
        this.rap_date_saisie = rap_date_saisie;
    }

    public String getRap_bilan() {
        return rap_bilan;
    }

    public void setRap_bilan(String rap_bilan) {
        this.rap_bilan = rap_bilan;
    }

    public int getMotif_num() {
        return motif_num;
    }

    public void setMotif_num(int motif_num) {
        this.motif_num = motif_num;
    }

    public int getRap_coeff_confiance() {
        return rap_coeff_confiance;
    }

    public void setRap_coeff_confiance(int rap_coeff_confiance) {
        this.rap_coeff_confiance = rap_coeff_confiance;
    }

    public boolean isRap_lu() {
        return rap_lu;
    }

    public void setRap_lu(boolean rap_lu) {
        this.rap_lu = rap_lu;
    }

    public Visiteur getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public Praticien getPraticien() {
        return praticien;
    }

    public void setPraticien(Praticien praticien) {
        this.praticien = praticien;
    }  

    @Override
    public String toString() {
        return "RapportVisite{" + "rap_num=" + rap_num + ", rap_date_visite=" + rap_date_visite + ", rap_date_saisie=" + rap_date_saisie + ", rap_bilan=" + rap_bilan + ", motif_num=" + motif_num + ", rap_coeff_confiance=" + rap_coeff_confiance + ", rap_lu=" + rap_lu + ", visiteur=" + visiteur + ", praticien=" + praticien + '}';
    }
    
}
