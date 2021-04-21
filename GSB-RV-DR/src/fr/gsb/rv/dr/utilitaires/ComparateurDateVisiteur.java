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
public class ComparateurDateVisiteur implements Comparator<Praticien>{
    
    public int compare( Praticien o1, Praticien o2){
        
        if( o1.getPra_dateDerniereVisite().isEqual( o2.getPra_dateDerniereVisite() )){
            return 0;
        }
        else if( o1.getPra_dateDerniereVisite().isAfter( o2.getPra_dateDerniereVisite() ) ){
            return 1;
        }
        else{
            return -1;
        }
    }
    
}
