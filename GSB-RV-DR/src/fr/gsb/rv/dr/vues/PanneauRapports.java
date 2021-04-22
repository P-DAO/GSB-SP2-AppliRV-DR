/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Mois;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */
public class PanneauRapports extends Pane{
    //Déclaration des attributs
    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<Visiteur>();
    private ComboBox<Mois> cbMois = new ComboBox<Mois>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();
    
    //Création de la tableview
    private TableView<RapportVisite> tabRapports = new TableView<RapportVisite>();
    
    public PanneauRapports () throws ConnexionException, SQLException{
        //Boîte Verticale (VBox) avec Label
        VBox rappVBox = new VBox();
        rappVBox.setStyle("-fx-background-color: white;");
        rappVBox.setSpacing(10);
        //Grille (GridPane) avec les 3 comboBox
        GridPane grilleCombo = new GridPane();
        grilleCombo.setHgap(10);
        grilleCombo.setVgap(10);
        //liste des visiteur dans le comboBox
        List<Visiteur> lesVisiteurs = ModeleGsbRv.getVisiteurs();
        for( Visiteur unVisiteur : lesVisiteurs){
            //Ajout du visiteur dans le comboBoxvisiteurs
            cbVisiteurs.getItems().add(unVisiteur);
        }
        cbVisiteurs.getSelectionModel().select(0); //position
        grilleCombo.getChildren().add(cbVisiteurs); //ajout dans le HBox
        
        //Liste Mois
        for(Mois unMois : Mois.values() ){
            cbMois.getItems().add(unMois);
        }
        cbMois.getSelectionModel().select(0);
        grilleCombo.getChildren().add(cbMois);
        
        //Liste des années
        LocalDate aujourdhui = LocalDate.now();
        int anneeCourante = aujourdhui.getYear();
        for(int i = 0; i < 6; i++){
            cbAnnee.getItems().add(anneeCourante - i);
        }
        cbAnnee.getSelectionModel().select(0);
        grilleCombo.getChildren().add(cbAnnee);
        
        rappVBox.getChildren().add(grilleCombo);
        this.getChildren().add(rappVBox) ;
        
        //Bouton valider
        Button valider = new Button("Valider");
        //ajout du bouton dans VBox
        rappVBox.getChildren().add(valider);
        //action de la validité
        
        
        //Création des Colonnes tabRapports
        
    }
    
    public void rafraichir() throws ConnexionException, SQLException{
        
    }
}
