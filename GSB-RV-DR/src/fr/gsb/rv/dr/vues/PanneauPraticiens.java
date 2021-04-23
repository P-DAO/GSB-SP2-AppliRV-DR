
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisiteur;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 *
 * @author developpeur
 */
public class PanneauPraticiens extends Pane{
    /*public PanneauPraticiens () {
        Label praLabel = new Label("Praticiens");
        VBox praVBox = new VBox();
        praVBox.getChildren().add(praLabel);
        praVBox.setStyle("-fx-background-color:white;");
        this.getChildren().add(praVBox);
    }*/
    
    public static int CRITERE_COEF_CONFIANCE = 1;
    public static int CRITERE_COEF_NOTORIETE = 2;
    public static int CRITERE_DATE_VISITE = 3;
    
    private int critereTri = CRITERE_COEF_CONFIANCE;
    
    //Création des boutons radio
    private RadioButton rbCoefConfiance = new RadioButton("Confiance");
    private RadioButton rbCoefNotoriete = new RadioButton("Notoriété");
    private RadioButton rbDateVisite = new RadioButton("Date Visite");
    
    //Création de la tableView
    private TableView<Praticien> tabPraticiens = new TableView<Praticien>();
    
    public PanneauPraticiens (){
        //Boîte verticale (Vbox) avec le Label
        VBox praVBox = new VBox();
        praVBox.setStyle("-fx-background-color: white;");
        praVBox.setSpacing(10);
        praVBox.setPadding(new Insets(10, 450, 10, 10));
        Label selectionTri = new Label("Sélectionner un critère de tri");
        selectionTri.setStyle("-fx-font-weight: bold");
        praVBox.getChildren().add(selectionTri);
        
        //Grille (GridPane) avec les boutons radios
        GridPane grilleRadio = new GridPane();
        grilleRadio.setHgap(10);
        grilleRadio.setVgap(10);
        
        //Groupe de boutons radio
        ToggleGroup grpTri = new ToggleGroup();
        
        //Boutons radios dans le groupe
        rbCoefConfiance.setToggleGroup(grpTri);
        rbCoefNotoriete.setToggleGroup(grpTri);
        rbDateVisite.setToggleGroup(grpTri);
        
        //Forcer la sélection
        rbCoefConfiance.setSelected(true);
        
        //Ajouter les boutons à la vue
        grilleRadio.add(rbCoefConfiance, 0, 0);
        grilleRadio.add(rbCoefNotoriete, 1, 0);
        grilleRadio.add(rbDateVisite, 2, 0);
        praVBox.getChildren().add(grilleRadio);
        /*HBox hBox = new HBox(20, rbCoefConfiance, rbCoefNotoriete, rbDateVisite);
        grilleRadio.add(hBox, 10, 10);
        praVBox.getChildren().add(grilleRadio);*/
        
        //Création des Colonnes tabPraticien
        TableColumn<Praticien, Integer> colNumero = new TableColumn<Praticien, Integer>( "Numéro" );
        TableColumn<Praticien, String> colNom = new TableColumn<Praticien, String>( "Nom" );
        TableColumn<Praticien, String> colVille = new TableColumn<Praticien, String>( "Ville" );
        
        //Taille des colonnes
        colNumero.setMinWidth(150);
        colNom.setMinWidth(150);
        colVille.setMinWidth(150);

        //Observation pour MAJ
        colNumero.setCellValueFactory( new PropertyValueFactory<>( "pra_num" ) );
        colNom.setCellValueFactory( new PropertyValueFactory<>( "pra_nom" ) );
        colVille.setCellValueFactory( new PropertyValueFactory<>( "pra_ville" ) );

        //Ajouter colonne à la tabPraticien
        tabPraticiens.getColumns().addAll(colNumero, colNom, colVille);
        
        tabPraticiens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        praVBox.getChildren().add(tabPraticiens);
        
        //Ecouteurs d'évènements
        rbCoefConfiance.setOnAction((ActionEvent)->{
                //Mémorisation du critère de tri sélectionné
                //setCritereTri(CRITERE_COEF_CONFIANCE);
                critereTri = CRITERE_COEF_CONFIANCE;
            try {
                //Rafraîchissement de la liste
                rafraichir();
            } catch (ConnexionException ex) {
                Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rbCoefNotoriete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //Mémorisation du critère de tri sélectionné
                    //setCritereTri(CRITERE_COEF_NOTORIETE);
                    critereTri = CRITERE_COEF_NOTORIETE;
                    //Rafraîchissement de la liste
                    rafraichir();
                } catch (ConnexionException ex) {
                    Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        rbDateVisite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Mémorisation du critère de tri sélectionné
                //setCritereTri(CRITERE_DATE_VISITE);
                critereTri = CRITERE_DATE_VISITE;
                try {
                    //Rafraîchissement de la liste
                    rafraichir();
                } catch (ConnexionException ex) {
                    Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.getChildren().add(praVBox) ;
        setCritereTri(CRITERE_DATE_VISITE);
        try {
            this.rafraichir() ;
        } catch(Exception e){
            System.out.println( "Pb" ) ;
        }
        
    }
    
    public void rafraichir() throws ConnexionException, SQLException{
        try{
            //System.out.println( "PanPrat::raf" ) ; ---->test pour trouver erreur d'affichage
            
            //Obtenir la liste des Praticiens
            List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants();
            
            /*for( Praticien p : praticiens ){      ---->test pour trouver erreur d'affichage renvoie la liste des praHesitants
                System.out.println( "> " + p  ) ;
            }*/
            
            //Convertir la listepraticiens hesitantd en liste observable
            ObservableList<Praticien> obListPra = FXCollections.observableArrayList(praticiens);
            
            /*System.out.println( "T> " + obListPra.size() ) ;---->test pour trouver erreur d'affichage, taille des praHesitants
            
            tabPraticiens.setItems(obListPra);
            tabPraticiens.refresh();*/
            
            //Traitements spécifiques au 3 critères de tri
            if(critereTri == CRITERE_COEF_CONFIANCE){
                Collections.sort(obListPra, new ComparateurCoefConfiance() );
                //tabPraticiens.setItems(obListPra);
            }
            else if(critereTri == CRITERE_COEF_NOTORIETE){
                Collections.sort(obListPra, new ComparateurCoefNotoriete() );
                Collections.reverse(obListPra);
                //tabPraticiens.setItems(obListPra);

            }
            else{
                Collections.sort(obListPra, new ComparateurDateVisiteur() );
                Collections.reverse(obListPra);
                //tabPraticiens.setItems(obListPra);

            }
            tabPraticiens.setItems(obListPra);
            tabPraticiens.refresh();
           
            
            //Ajouter listObservable à la tabPraticiens
            /*tabPraticiens.getItems().clear();
            tabPraticiens.getItems().addAll(obListPra);*/
            
        }catch(ConnexionException ex){
            Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
                    Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int getCritereTri(){
        return critereTri;
    }
    
    public void setCritereTri(int critereTri){
        this.critereTri = critereTri;
        /*if(critereTri == CRITERE_COEF_CONFIANCE){
            rbCoefConfiance.setSelected(true);
        }
        else if(critereTri == CRITERE_COEF_NOTORIETE){
            rbCoefNotoriete.setSelected(true);
        }
        else{
            rbDateVisite.setSelected(true);
        }*/
    }   
}