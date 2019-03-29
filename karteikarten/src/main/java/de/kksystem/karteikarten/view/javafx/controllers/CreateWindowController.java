package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateWindowController implements Initializable {

    private WindowPresetSwitchStage switchStageHelper = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private MenuItem miAddCategory;

    @FXML
    private MenuItem miEditCategory;

    @FXML
    private MenuItem miDeleteCategory;

    @FXML
    private MenuItem miAddLection;

    @FXML
    private MenuItem miEditLection;

    @FXML
    private MenuItem miDeleteLection;

    @FXML
    private MenuItem miEditIndexCard;

    @FXML
    private MenuItem miDeleteIndexCard;

    @FXML
    private MenuItem miEditFavoritelist;

    @FXML
    private MenuItem miDeleteFavoritelist;

    public void switchToAddCategoryDialog(ActionEvent event){
        TextInputDialog tid = new TextInputDialog("Kategorienamen eingeben");
        tid.setTitle("Kategorie erstellen");
        tid.setHeaderText(null);
        tid.setContentText("Bitte Kategorienamen eingeben: ");

        Optional<String> enteredValue = tid.showAndWait();

        if(enteredValue.get().isBlank()) {
            System.out.println("Unknown");
        }else {
            System.out.println(enteredValue.get());
        }
    }

    public void switchToEditCategoryDialog(ActionEvent event){
        TextInputDialog tid = new TextInputDialog("Neuer Kategoriename");
        tid.setTitle("Kategorie bearbeiten");
        tid.setHeaderText(null);
        tid.setContentText("Bitten den neuen Kategorienamen eingeben");

        Optional<String> enteredValue = tid.showAndWait();

        if(enteredValue.get().isBlank()) {
            System.out.println("Unknown");
        }else {
            System.out.println(enteredValue.get());
        }
    }

    public void switchToDeleteCategoryDialog(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kategorie loeschen!");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Soll endgueltig geloescht werden?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK, hier wird gelöscht
        } else {

        }
    }

    public void switchtoAddLectionDialog(ActionEvent event){
        TextInputDialog tid = new TextInputDialog("Lektionnamen eingeben");
        tid.setTitle("Lektion erstellen");
        tid.setHeaderText(null);
        tid.setContentText("Bitte Lektionnamen eingeben:");

        Optional<String> enteredValue = tid.showAndWait();

        if(enteredValue.get().isBlank()) {
            System.out.println("Unknown");
        }else {
            System.out.println(enteredValue.get());
        }
    }

    public void switchToEditLectionDialog(ActionEvent event){
        TextInputDialog tid = new TextInputDialog("Neuer Lektionname");
        tid.setTitle("Lektion bearbeiten");
        tid.setHeaderText(null);
        tid.setContentText("Bitte neuen Lektionnamen eingeben:");

        Optional<String> enteredValue = tid.showAndWait();

        if(enteredValue.get().isBlank()) {
            System.out.println("Unknown");
        }else {
            System.out.println(enteredValue.get());
        }
    }

    public void switchToDeleteLectionDialog(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lektion loeschen!");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Soll endgueltig geloescht werden?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK, hier wird gelöscht
        } else {

        }
    }

    public void switchToEditIndexCardDialog(ActionEvent event){
        switchStageHelper.createWindowNewStage("/fxml/editIndexCardWindow.fxml", "Karteikarte bearbeiten!", new EditIndexCardController());
    }

    public void switchToDeleteIndexCardDialog(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Karteikarte loeschen!");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Soll endgueltig geloescht werden?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK, hier wird gelöscht
        } else {

        }
    }

    public void switchtoEditFavoritelistDialog(ActionEvent event){
        TextInputDialog tid = new TextInputDialog("Neuer Favoritenname");
        tid.setTitle("Favoritenliste bearbeiten");
        tid.setHeaderText(null);
        tid.setContentText("Bitte neuen Favoritennamen eingeben:");

        Optional<String> enteredValue = tid.showAndWait();

        if(enteredValue.get().isBlank()) {
            System.out.println("Unknown");
        }else {
            System.out.println(enteredValue.get());
        }
    }

    public void switchToDeleteFavoritelistDialog(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Favorit loeschen!");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Soll endgueltig geloescht werden?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK, hier wird gelöscht
        } else {

        }
    }

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeCreateWindow(ActionEvent event){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::closeCreateWindow);
        miAddCategory.setOnAction(this::switchToAddCategoryDialog);
        miEditCategory.setOnAction(this::switchToEditCategoryDialog);
        miDeleteCategory.setOnAction(this::switchToDeleteCategoryDialog);
        miAddLection.setOnAction(this::switchtoAddLectionDialog);
        miEditLection.setOnAction(this::switchToEditLectionDialog);
        miDeleteLection.setOnAction(this::switchToDeleteLectionDialog);
        miEditIndexCard.setOnAction(this::switchToEditIndexCardDialog);
        miDeleteIndexCard.setOnAction(this::switchToDeleteIndexCardDialog);
        miEditFavoritelist.setOnAction(this::switchtoEditFavoritelistDialog);
        miDeleteFavoritelist.setOnAction(this::switchToDeleteFavoritelistDialog);
    }
}
