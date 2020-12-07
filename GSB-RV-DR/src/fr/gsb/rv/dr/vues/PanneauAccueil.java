/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */
public class PanneauAccueil extends Pane{
    public PanneauAccueil (){
        Label acLabel = new Label("Accueil");
        VBox acVBox = new VBox();
        acVBox.getChildren().add(acLabel);
        acVBox.setStyle("-fx-background-color: white;");
        this.getChildren().add(acVBox) ;
        
    }
    
}
