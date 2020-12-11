/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entities.Praticien;
import java.util.Comparator;

/**
 *
 * @author developpeur
 */
public class ComparateurCoefConfiance implements Comparator<Praticien>{
    
    public int compare( Praticien o1, Praticien o2){
        
        if( o1.getDernierCoefConfiance() == o2.getDernierCoefConfiance()){
            return 0;
        }
        else if( o1.getDernierCoefConfiance() > o2.getDernierCoefConfiance()){
            return 1;
        }
        else{
            return -1;
        }
    }
    
}
