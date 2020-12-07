package fr.gsb.rv.dr.technique;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnexionBD {
    
    private static String dbURL = "jdbc:mysql://localhost:3306/gsbrv" ;
    private static String user = "developpeur" ;
    private static String password = "azerty" ;
    
    private static Connection connexion = null ;
    
    private ConnexionBD() throws ConnexionException {
        try {
            Class.forName( "com.mysql.jdbc.Driver" ) ;
            connexion = (Connection) DriverManager.getConnection(dbURL, user, password) ;
        }
        catch( Exception e ){
            throw new ConnexionException() ;
        }
        
    }
    
    public static Connection getConnexion() throws ConnexionException {
        if( connexion == null ){
            new ConnexionBD() ;
        }
        return connexion ;
    }
}
