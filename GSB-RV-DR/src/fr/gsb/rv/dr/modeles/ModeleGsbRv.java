package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entities.Motif_visite;
import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        catch( SQLException e ){
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
        catch( SQLException e ){
            System.out.println(e.getMessage());
            return null ;
        }  
    }
    
    //Table réactive
    public static List<Visiteur> getVisiteurs() throws ConnexionException, SQLException{
        Connection connexion = ConnexionBD.getConnexion();
        
        List<Visiteur> listVisiteurs = new ArrayList<>();
        
        String requete = "SELECT vis_matricule, vis_nom, vis_prenom "
                    + "FROM Visiteur";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            
            ResultSet resultat = requetePreparee.executeQuery();
            if(resultat.next()){
                do{
                    Visiteur visiteur = new Visiteur();
                    visiteur.setVis_matricule(resultat.getString("vis_matricule"));
                    visiteur.setVis_nom(resultat.getString("vis_nom"));
                    visiteur.setVis_prenom(resultat.getString("vis_prenom"));
                    
                    System.out.println(visiteur);
                    
                    listVisiteurs.add(visiteur);
                }while(resultat.next() == true );
                return listVisiteurs;
            }
            else{
                return listVisiteurs;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }      
    }
    
    public static List<RapportVisite> getRapportsVisite(String matricule, int mois, int annee) throws ConnexionException, SQLException {
        Connection connexion = ConnexionBD.getConnexion();
        
        List<RapportVisite> listRapVis = new ArrayList<>();
        
        String requete = "SELECT r.vis_matricule,rap_num, rap_date_visite, rap_bilan, rap_date_saisie, rap_coeff_confiance, rap_lu, pra_num, motif_libelle "
                + "FROM RapportVisite r "
                + "INNER JOIN Motif_visite mv "
                + "ON r.motif_num = mv.motif_num "
                + "INNER JOIN Visiteur v "
                + "ON v.vis_matricule = r.vis_matricule "
                + "WHERE r.vis_matricule = ? "
                + "AND MONTH(rap_date_visite) = ? "
                + "AND YEAR(rap_date_visite)=? ";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            
            requetePreparee.setString( 1 , matricule);
            requetePreparee.setInt(2, mois);
            requetePreparee.setInt(3, annee);

            ResultSet resultat = requetePreparee.executeQuery();
            
            if(resultat.next()){
                do{
                    LocalDate rapDateVisite = LocalDate.parse(resultat.getString("rap_date_visite"));
                    LocalDate rapDateSaisie = LocalDate.parse(resultat.getString("rap_date_saisie"));
                    Visiteur leVisiteur = getUnVisiteur(matricule);
                    RapportVisite rappVis = new RapportVisite();
                    rappVis.setRap_num(resultat.getInt("rap_num"));
                    rappVis.setRap_date_visite(rapDateVisite);
                    rappVis.setRap_bilan(resultat.getString("rap_bilan"));
                    rappVis.setRap_date_saisie(rapDateSaisie);
                    rappVis.setRap_coeff_confiance(resultat.getInt("rap_coeff_confiance"));
                    rappVis.setRap_lu(resultat.getBoolean("rap_lu"));
                    rappVis.setVisiteur(leVisiteur);
                    rappVis.setPraticien(getUnPraticien(resultat.getInt("pra_num")));
                    rappVis.setMotif(getUnMotif(resultat.getString("motif_libelle")));

                    System.out.println(rappVis);
                    
                    listRapVis.add(rappVis);
                    
                }while( resultat.next() == true );
                return listRapVis;
            }else{
                return listRapVis;
            }
        }catch(ConnexionException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }
    
    public static void setRapportVisite(String matricule, int numRapport) throws ConnexionException, SQLException{
        Connection connexion = ConnexionBD.getConnexion();
        
        String requete = "UPDATE RapportVisite r "
                + "SET rap_lu = 1 " 
                + "WHERE vis_matricule = ? "
                + "AND  rap_num = ? ";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setInt(2, numRapport);
            requetePreparee.executeUpdate();
         
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Visiteur getUnVisiteur(String matricule) throws ConnexionException, SQLException{
        Connection connexion = ConnexionBD.getConnexion();
        
        String requeteVis = "SELECT vis_matricule, vis_nom, vis_prenom "
                + "FROM Visiteur "
                + "WHERE vis_matricule = ?";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requeteVis);
            requetePreparee.setString( 1 , matricule);
            ResultSet resultat = requetePreparee.executeQuery();
            if(resultat.next()){
                Visiteur visiteur = new Visiteur();
                visiteur.setVis_matricule(matricule);
                visiteur.setVis_nom(resultat.getString("vis_nom"));
                visiteur.setVis_prenom( resultat.getString( "vis_prenom" ));
                
                requetePreparee.close();
                return visiteur;
            }
            else{
                return null;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }
    
    public static Praticien getUnPraticien(int pra_num) throws ConnexionException, SQLException{
        Connection connexion = ConnexionBD.getConnexion();
        
        String requetePra = "SELECT pra_num, pra_nom, pra_ville "
                + "FROM Praticien "
                + "WHERE pra_num = ?";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requetePra);
            requetePreparee.setInt(1, pra_num);
            ResultSet resultat = requetePreparee.executeQuery();
            if(resultat.next()){
                Praticien praticien = new Praticien();
                praticien.setPra_num(pra_num);
                praticien.setPra_nom(resultat.getString("pra_nom"));
                praticien.setPra_ville(resultat.getString("pra_ville"));
                
                requetePreparee.close();
                return praticien;
            }
            else{
                return null;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static Motif_visite getUnMotif(String motif_libelle)throws ConnexionException, SQLException{
        Connection connexion = ConnexionBD.getConnexion();
        
        String requeteMotif = "SELECT motif_num, motif_libelle "
                + "FROM Motif_visite "
                + "WHERE motif_libelle = ? ";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requeteMotif);
            requetePreparee.setString(1, motif_libelle);
            ResultSet resultat = requetePreparee.executeQuery();
            if(resultat.next()){
                Motif_visite motif = new Motif_visite();
                motif.setMotif_num(resultat.getInt("motif_num"));
                motif.setMotif_libelle(motif_libelle);
                
                requetePreparee.close();
                return motif;
            }
            else{
                return null;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}