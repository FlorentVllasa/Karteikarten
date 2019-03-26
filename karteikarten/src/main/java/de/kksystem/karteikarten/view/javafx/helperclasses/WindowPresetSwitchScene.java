package de.kksystem.karteikarten.view.javafx.helperclasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowPresetSwitchScene {
    /* Diese Methode dient dazu neue Scenen erstellen zu können, welche innerhalb der selben Stage
     * ausgewechselt werden mit 3 Parametern:
     * Path ist die fxml Url, controller ist eine Referenz auf die ControllerKlasse
     * der jeweiligen fxml, stageInfo ist diejenige Stage in welcher die Scene ausgetauscht werden soll.*/

    public void createWindowSwitchScene(String path, Object controller, Stage stageInfo){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            stageInfo.setScene(scene);

        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
