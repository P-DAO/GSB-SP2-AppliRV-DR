package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        
        // Code de test à compléter
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = ("select v.vis_matricule, v.vis_nom, v.vis_prenom, t.tra_role "
        			+ "from Visiteur v "
        			+ "INNER JOIN Travailler t "
        			+ "on v.vis_matricule= t.vis_matricule "
        			+ "where tra_role='Délégué' and jjmmaa=("
        			+ "select max(jjmmaa) "
        			+ "from Travailler t1 "
        			+ "where t1.vis_matricule= ? AND v.vis_mdp= ? )");
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            //requetePreparee.setString( 1 , "c14" );
            //requetePreparee.setString(2, "azerty");
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setString(2, mdp);
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                Visiteur visiteur = new Visiteur() ;
                visiteur.setVis_matricule( matricule );
                visiteur.setVis_nom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setVis_prenom( resultat.getString( "vis_prenom" ));
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    public static List<Praticien> getPraticiensHesitants() throws ConnexionException, SQLException{
        
        Connection connexion = ConnexionBD.getConnexion();
        
        List<Praticien> praHesitant = new ArrayList<Praticien>();
        
        String requete = "SELECT p.pra_num, pra_nom, pra_prenom, pra_ville, pra_coefnotoriete, rap_date_visite, rap_coeff_confiance "
                + "FROM Praticien p "
                + "INNER JOIN RapportVisite r "
                + "ON p.pra_num = r.pra_num "
                + "WHERE rap_coeff_confiance <5 "
                + "AND rap_date_visite = ( "
                + "     SELECT MAX(rap_date_visite) "
                + "     FROM RapportVisite r2 "
                + "     WHERE r.pra_num = r2.pra_num "
                + ")";
        
        /*String requete = "SELECT pra_nom, p.pra_num, pra_ville, pra_coefnotoriete, rv.rap_date_visite, rv.rap_coeff_confiance " 
                + "FROM Praticien p " 
                + "INNER JOIN  RapportVisite rv ON p.pra_num = rv.pra_num " 
                + "WHERE rap_coeff_confiance < 5 " 
                + "AND rv.rap_date_visite = ( " 
                + "  SELECT MAX(rv2.rap_date_visite) " 
                + "  FROM RapportVisite rv2 " 
                + "  WHERE rv.pra_num = rv2.pra_num " 
                + ") "
                + "GROUP BY pra_prenom, pra_nom "
                + "ORDER BY pra_nom "; */   
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                do{
                    Praticien praticien = new Praticien();
                    praticien.setPra_num(resultat.getInt("pra_num"));
                    praticien.setPra_nom(resultat.getString("pra_nom"));
                    praticien.setPra_ville(resultat.getString("pra_ville"));
                    praticien.setPra_coefnotoriete(resultat.getDouble("pra_coefnotoriete"));
                    praticien.setDernierCoefConfiance(resultat.getInt("rap_coeff_confiance"));
                    //praticien.setPra_dateDerniereVisite(Date.valueOf(resultat.getString("rap_date_visite")).toLocalDate());
                    praticien.setPra_dateDerniereVisite(resultat.getDate("rap_date_visite").toLocalDate());
                    
                   System.out.println( praticien ) ;
                    
                    praHesitant.add(praticien);
                    
                }while( resultat.next() == true );
                return praHesitant;
            }
            else {
                return praHesitant ;
            }
        }
        catch( Exception e ){
            System.out.println(e.getMessage());
            return null ;
        }  
    }
}
