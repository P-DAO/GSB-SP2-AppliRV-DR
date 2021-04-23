/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.entities;

/**
 *
 * @author developpeur
 */
public class Motif_visite {
    private int motif_num;
    private String motif_libelle;

    public Motif_visite() {
    }

    public Motif_visite(int motif_num, String motif_libelle) {
        this.motif_num = motif_num;
        this.motif_libelle = motif_libelle;
    }

    public int getMotif_num() {
        return motif_num;
    }

    public void setMotif_num(int motif_num) {
        this.motif_num = motif_num;
    }

    public String getMotif_libelle() {
        return motif_libelle;
    }

    public void setMotif_libelle(String motif_libelle) {
        this.motif_libelle = motif_libelle;
    }

    @Override
    public String toString() {
        return "Motif_visite{" + "motif_num=" + motif_num + ", motif_libelle=" + motif_libelle + '}';
    }
    
    
}
