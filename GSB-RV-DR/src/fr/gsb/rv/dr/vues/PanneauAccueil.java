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
        InputStream input = clazz.getResourceAsStream("/image/logo-gsb.png");
        Image image = new Image(input, 568, 352, false, true);
        ImageView imageView = new ImageView(image);
        VBox vbImage = new VBox();
        vbImage.getChildren().add(imageView);
        vbImage.setAlignment(Pos.CENTER);
        Label acLabel = new Label("Accueil- GSB-RV");
        acLabel.setStyle("-fx-font: 40 arial;");
        VBox acVBox = new VBox();
        acVBox.getChildren().add(acLabel);
        acVBox.setStyle("-fx-background-color: white;");
        acVBox.getChildren().add(vbImage);
        this.getChildren().add(acVBox) ;
        
    }
    
}
