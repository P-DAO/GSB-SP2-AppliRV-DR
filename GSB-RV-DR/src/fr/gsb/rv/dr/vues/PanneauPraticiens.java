/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 *
 * @author developpeur
 */
public class PanneauPraticiens extends Pane{
    public PanneauPraticiens (){
        Label praLabel = new Label("Praticiens");
        VBox praVBox = new VBox();
        praVBox.getChildren().add(praLabel);
        praVBox.setStyle("-fx-background-color: white;");
        this.getChildren().add(praVBox) ;
    }
    
}
