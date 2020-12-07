package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
