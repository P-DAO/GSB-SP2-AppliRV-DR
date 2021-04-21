/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.vues.VueConnexion;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisiteur;
import fr.gsb.rv.dr.vues.PanneauAccueil;
import fr.gsb.rv.dr.vues.PanneauPraticiens;
import fr.gsb.rv.dr.vues.PanneauRapports;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    
    // Déclaration et initialisation
    PanneauAccueil vueAccueil = new PanneauAccueil();    
    PanneauRapports vueRapports = new PanneauRapports();
    PanneauPraticiens vuePraticiens = new PanneauPraticiens() ; 
    
    @Override
    public void start(Stage primaryStage) throws ConnexionException {
        //Connexion
        Connection connexion = ConnexionBD.getConnexion();
        // Création de la barre de Menu
        MenuBar barreMenus = new MenuBar();
        //Création du menu Fichier
        Menu menuFichier = new Menu("Fichier");
        //Création des items
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
               
        //Création du menu Rapport
        Menu menuRapport = new Menu("Rapport");
        //Création d'items
        MenuItem itemConsulter = new MenuItem("Consulter");
        
        //Création du menu Praticiens
        Menu menuPraticiens = new Menu("Paticiens");
        //Création d'tems
        MenuItem itemHesitants = new MenuItem("Hésitants");
        
        //Ajout de des items dans le menu
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDeconnecter);
        itemSeDeconnecter.setDisable(true); //désactiver l'item
        menuFichier.getItems().add(itemQuitter);
        
        menuRapport.getItems().add(itemConsulter);
        
        menuPraticiens.getItems().add(itemHesitants);
        
        //Ajout des menus dans la barre de menu
        barreMenus.getMenus().add(menuFichier);
        barreMenus.getMenus().add(menuRapport);
        menuRapport.setDisable(true);// désactiver le menu
        barreMenus.getMenus().add(menuPraticiens);
        menuPraticiens.setDisable(true);// désactiver le menu
         
        //Création d'un root
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        
        //Création de pile de panneaux
        StackPane pileP = new StackPane();
        
        //Ajout des 3 panneaux à la pile
        pileP.getChildren().addAll(vueAccueil, vueRapports, vuePraticiens);
        vueAccueil.toFront();
        
        //Possition pile au centre
        root.setCenter(pileP);
        
        //Désactiver les panneaux
        vueAccueil.setVisible(false);
        vueRapports.setVisible(false);
        vuePraticiens.setVisible(false);
        
        //Action sur le bouton SeConnecter
        itemSeConnecter.setOnAction((ActionEvent event) ->{
        /*itemSeConnecter.setOnAction(
            new EventHandler<ActionEvent>(){
                public void handle( ActionEvent event){
                Visiteur visiteur1 = new Visiteur ();
                try {
                    visiteur1 = ModeleGsbRv.seConnecter("c14", "azerty");
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                });
            Session.ouvrir(visiteur1)}};*/
            try{
                VueConnexion vue = new VueConnexion();
                Optional<Pair<String, String>> reponse = vue.showAndWait();
                if(reponse.isPresent()){
                   Visiteur visiteur = ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
                   if(visiteur!=null){
                        Session.ouvrir(visiteur);
                        menuRapport.setDisable(false);
                        menuPraticiens.setDisable(false);
                        itemSeDeconnecter.setDisable(false);
                        itemSeConnecter.setDisable(true);
                        primaryStage.setTitle(Session.getSession().getLeVisiteur().getVis_nom() + " " + Session.getSession().getLeVisiteur().getVis_prenom());
                        vueAccueil.setVisible(true);
                        vuePraticiens.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);                        
                    }
                   else{
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Erreur");
                        alertError.setHeaderText("Erreur dans l'authentification");
                        alertError.setContentText("Cliquer Ok pour revenir sur l'acceuil");
                        alertError.showAndWait();
                    }
                }  
            }
            catch(ConnexionException ex){
                Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Action sur le bouton SeDeconnecter
        itemSeDeconnecter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event ){
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                alertQuitter.setTitle("Demande de confirmation");
                alertQuitter.setContentText("Voulez-vous vous déconnecter?");
                ButtonType btnOui = new ButtonType("Oui");
                ButtonType btnNon = new ButtonType("Non");
                alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                vuePraticiens.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);
                Optional<ButtonType>reponse = alertQuitter.showAndWait();
                if (reponse.get() == btnOui){   
                    Session.fermer();
                    primaryStage.setTitle("GSB-RV-DR");
                    menuRapport.setDisable(true);
                    menuPraticiens.setDisable(true);
                    itemSeDeconnecter.setDisable(true);
                    itemSeConnecter.setDisable(false);
                    vueAccueil.setVisible(false);
                    vueRapports.setVisible(false);
                    vuePraticiens.setVisible(false);
                }
            }
        });
        //Action sur le bouton Consulter
        itemConsulter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                primaryStage.setTitle("[Rapports]" + " " +Session.getSession().getLeVisiteur().getVis_nom() + " "+ Session.getSession().getLeVisiteur().getVis_prenom());
                vueRapports.toFront();
                vueAccueil.setVisible(false);
                vueRapports.setVisible(true);
                vuePraticiens.setVisible(false);
            }
        });
        //Action sur le bouton Hésitant
        itemHesitants.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                primaryStage.setTitle("[Praticiens]" + " " +Session.getSession().getLeVisiteur().getVis_nom() + " "+ Session.getSession().getLeVisiteur().getVis_prenom());
                vuePraticiens.toFront();
                try {
                    vuePraticiens.rafraichir();
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
                vueAccueil.setVisible(false);
                vueRapports.setVisible(false);
                vuePraticiens.setVisible(true);
                
                List<Praticien> praticiens = null;
                /*----(affiche dans le terminal netbeans les praticiens hesitant)----
                try {
                    praticiens = ModeleGsbRv.getPraticiensHesitants();
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for( Praticien unPraticien : praticiens){
                    System.out.println( unPraticien );
                }*/
                                
                try {
                    praticiens = ModeleGsbRv.getPraticiensHesitants();
                    //System.out.println(praticiens.size());
                    Collections.sort( praticiens, new ComparateurCoefConfiance() );
                    for (Praticien unPraticien : praticiens){
                        System.out.println(unPraticien.getDernierCoefConfiance());
                    }
                    Collections.sort( praticiens, new ComparateurCoefNotoriete() );
                    Collections.reverse(praticiens);
                    for (Praticien unPraticien : praticiens){
                        //System.out.println(unPraticien);
                        System.out.println(unPraticien.getPra_coefnotoriete());
                    }
                    Collections.sort( praticiens, new ComparateurDateVisiteur() );
                    Collections.reverse(praticiens);

                    for (Praticien unPraticien : praticiens){
                        System.out.println(unPraticien.getPra_dateDerniereVisite());
                    }
                    
                    
                } catch (ConnexionException | SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
            }
        });
         //Action sur le bouton Quitter
        /*itemQuitter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event ){
                Platform.exit();
            }
        });*/
        itemQuitter.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        itemQuitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
            alertQuitter.setTitle("Demande de confirmation");
            alertQuitter.setContentText("Voulez-vous quitter l'application?");
            ButtonType btnOui = new ButtonType("Oui");
            ButtonType btnNon = new ButtonType("Non");
            alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
            Optional<ButtonType>reponse = alertQuitter.showAndWait();
                if (reponse.get() == btnOui){
                    System.exit(0);
                }
            }
        });
        
        
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ConnexionException, SQLException {
        launch(args);
        /*System.out.println("/\nListe des practiciens hésitants-------------------------------------------------------/");
        List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants() ;
        for ( Praticien unPraticien : praticiens ){
            System.out.println( unPraticien );
        } 
        System.out.println("/\nComparer par ordre croissant coef de confiance----------------------------------------/");
        Collections.sort( praticiens , new ComparateurCoefConfiance() ) ;
        for ( Praticien unPraticien : praticiens ){
            System.out.println( unPraticien );
        }
        System.out.println("/\nComparer par ordre décroissant du coef de notoriété-----------------------------------/");
        Collections.sort( praticiens , new ComparateurCoefNotoriete() ) ;
        Collections.reverse( praticiens ) ;
        for ( Praticien unPraticien : praticiens ){
            System.out.println( unPraticien );
        }
        System.out.println("/\nComparer par ordre chronologique inverse de la dernière visite------------------------/");
        Collections.sort( praticiens , new ComparateurDateVisiteur() ) ;
        Collections.reverse(praticiens) ;
        for ( Praticien unPraticien : praticiens ){
            System.out.println( unPraticien );
        }
        System.out.println("/\n--------------------------------------------------------------------------------------/");*/
    }

}
 