/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import static javafx.scene.control.ButtonType.CLOSE;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author developpeur
 */
public class vueRapport extends Dialog {
    private Dialog diaRapp = new Dialog();
    
    public vueRapport(RapportVisite leRapportVis){
        //Création Boîte verticale
        VBox vbAll = new VBox();
        //Dimension
        vbAll.setPadding(new Insets(5, 350, 5, 5));
        //Titre de la fenêtre 
        setTitle("Rapport numéro: " + leRapportVis.getRap_num());
        setHeaderText(leRapportVis.getVisiteur().getVis_nom() + " " + leRapportVis.getVisiteur().getVis_prenom() + "\n" + "Informations du Rapport Visite");
        //Déclaration des variables et valorisation
        Text dateVis = new Text("La date de la Visite: " + leRapportVis.getRap_date_visite());
        Text dateRed = new Text("Date de rédaction: " + leRapportVis.getRap_date_saisie());
        Text visiteur = new Text("Visiteur: " + leRapportVis.getVisiteur().getVis_nom());
        Text praticien = new Text("Patraticien: " + leRapportVis.getPraticien().getPra_nom() + " de " + leRapportVis.getPraticien().getPra_ville());
        Text motif = new Text("Motif: " + leRapportVis.getMotif().getMotif_libelle());
        Text bilan = new Text("Bilan de la visite: " + leRapportVis.getRap_bilan());
        Text coefConf = new Text("Coefficient Confiance: " + leRapportVis.getRap_coeff_confiance());
        Text lu = new Text();
        if(leRapportVis.isRap_lu()){
            lu.setText("Lu: Oui");
        }else{
            lu.setText("Lu: Non");
        }
        //Boutons
        ButtonType btnLu = new ButtonType("Manquer comme LU", OK_DONE);
        if(leRapportVis.isRap_lu() == false){
            this.getDialogPane().getButtonTypes().add(btnLu);
        }else{
            ButtonType annuler = new ButtonType("Fermer", CANCEL_CLOSE);
            this.getDialogPane().getButtonTypes().add(annuler);
        }
        //Ajout des variable dans la VBox
        vbAll.getChildren().addAll( dateVis, dateRed, visiteur, praticien, motif, bilan, coefConf, lu);
        //Ajout dans la boîte de Dialog
        getDialogPane().setContent(vbAll);

        setResultConverter(
            dialogBtn ->{
                if(dialogBtn == btnLu){
                    return "valider";
                } 
                return null;
            });
    }
}
