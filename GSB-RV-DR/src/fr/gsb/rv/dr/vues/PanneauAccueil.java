/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */
public class PanneauAccueil extends Pane{
    public PanneauAccueil (){
        Class<?> clazz = this.getClass();
        this.setStyle("-fx-background-color: white;");
        InputStream input = clazz.getResourceAsStream("/image/logo-gsb.png");
        Image image = new Image(input, 568, 352, false, true);
        ImageView imageView = new ImageView(image);
        
        imageView.setFitHeight(255);
        imageView.setFitWidth(400);
        imageView.setX(250);
        imageView.setY(125);
        
        Label acLabel = new Label("Accueil- GSB-RV");
        acLabel.setStyle("-fx-font: 40 arial;");
        acLabel.setLayoutX(300);
        this.getChildren().add(imageView);
        this.getChildren().add(acLabel);
        
        
    }
    
}
