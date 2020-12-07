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
public class PanneauRapports extends Pane{
    public PanneauRapports (){
        Label rappLabel = new Label("Rapports");
        VBox rappVBox = new VBox();
        rappVBox.getChildren().add(rappLabel);
        rappVBox.setStyle("-fx-background-color: white;");
        this.getChildren().add(rappVBox) ;
    }
}
