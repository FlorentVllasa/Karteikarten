package de.kksystem.karteikarten.view.javafx.helperclasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowPresetSwitchStage {
    /*Diese Methode dient dazu neue Fenter erstellen zu können mit 3 Parametern
     * Path ist die fxml Url, title ist der Titel des Fensters, controller ist eine Referenz auf die ControllerKlasse
     * der jeweiligen fxml*/

    public void createWindowNewStage(String path, String title, Object controller){
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            fxmlLoader.setController(controller);
            final Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        }catch(IOException io){
            io.printStackTrace();
        }
    }

}
