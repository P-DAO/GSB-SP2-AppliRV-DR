package fr.gsb.rv.dr.vues;


import javafx.scene.Scene;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author developpeur
 */
public class VueConnexion extends Dialog<Pair<String, String>>{
    private  Dialog<Pair<String, String>> dialogue = new Dialog<>();
    
    public VueConnexion(){
        //Création d'une boîte verticale
        VBox vb=new VBox(20);
        //création de boîtes horizontales
        HBox hb1 = new HBox(40);
        HBox hb2 = new HBox(20);
        //titre de la fenêtre et en tête 
        setTitle("Authentification");
        setHeaderText("Saisir vos données de connexion");
        //noms des labels
        Label mat = new Label("Matricule: "); 
        Label pw = new Label("Mot de passe: ");
        //champs
        TextField matricule = new TextField();
        PasswordField mdp = new PasswordField();
        //butons
        ButtonType annuler = new ButtonType("Annuler", CANCEL_CLOSE);
        ButtonType seCo = new ButtonType("Se Connecter", OK_DONE);
        //ajout du label et du champ dans la boîte horizontale
        hb1.getChildren().addAll(mat, matricule);
        hb2.getChildren().addAll(pw, mdp);
        //ajout des boîtes horizontales dans la boîte verticale
        vb.getChildren().addAll(hb1, hb2);
        //ajout dans la boîte de dialog 
        getDialogPane().setContent(vb);
        //ajout du es boutons 
        this.getDialogPane().getButtonTypes().addAll(annuler, seCo);
   
        setResultConverter(
            new Callback<ButtonType, Pair<String, String>>(){
                @Override
                public Pair<String, String> call (ButtonType typeBouton){
                    if ( typeBouton == seCo){
                        return new Pair<String, String> (matricule.getText(), mdp.getText());
                    }
                    return null;
                }
            }
        );
    }
}
