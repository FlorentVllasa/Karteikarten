package de.kksystem.karteikarten.view.javafx.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LearnWindowController implements Initializable {

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField textFieldFrage;

    @FXML
    private TextField textFieldAntwort;

    @FXML
    private Button buttonZuruck;

    @FXML
    private Button buttonAntwort;

    @FXML
    private Button buttonWeiter;

    @FXML
    private Button schlieseFester;

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }
    
//   public void start(Stage stage) {
   	
//    	Combobox<Category> comboBox = new ComboBox<Category>();
    	
//    	ObservableList<Category> list = CategoryDaoJdbcImpl.getCategoryList();
    	
//    	comboBox.setItems(list);
//    	comboBox.getSelectionModel().select(1);
    	
//    	splitPane root = new splitPane();
//    	root.setPadding(new Insets(5));
//    	root.setHgap(5);
    	
//    	stage.setTitle("ComboBox (de.kksystem.karteikarten.view.javafx.controllers)");
//    	Scene scene = new Scene(root, 350, 300);
//    	stage.setScene(scene);
//    	stage.show();    	
//    }
    
//    public static void main(String[] args) {
//    	Application.launch(args);
//    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schlieseFester.setOnAction(this::closeLearnWindow);
    }
}
