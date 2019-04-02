package de.kksystem.karteikarten.view.javafx.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.PictureImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Picture;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditPictureOfIndexCardController implements Initializable {
	private IndexCard passedIndexCard;
	private CreateWindowController createWindowController = new CreateWindowController();
	private static final double PIC_MAX_WIDTH = 1920D;
	private static final double PIC_MAX_HEIGHT = 1080D;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnAddPicture;
    @FXML
    private Button btnReplacePicture;
    @FXML
    private Button btnDeletePicture;
    @FXML
    private ImageView ivIndexCardPicture;
    @FXML
    private Label lblPictureMessage;
    @FXML
    private Button btnCloseWindow;
    @FXML
    private ScrollPane scrlpnPicture;
    
    public void setPassedIndexCard(IndexCard passedIndexCard) {
    	this.passedIndexCard = passedIndexCard;
    }
    
    @FXML
    private void addPictureToIndexCard(ActionEvent event) {
		createWindowController = UserData.getInstance().getC();
		createWindowController.resetFilePicture();

		if (passedIndexCard != null) {
			final FileChooser pictureChooser = new FileChooser();
			pictureChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"),
					new FileChooser.ExtensionFilter("GIF", "*.gif"));
			pictureChooser.setTitle("Wählen Sie Ihr Bild für Ihre Karteikarte");
			Stage currentStage = (Stage) anchorPane.getScene().getWindow();
			File selectedPicture = pictureChooser.showOpenDialog(currentStage);

			if (selectedPicture != null) {
				String pathOfPicture = selectedPicture.getAbsoluteFile().toString();
				String pictureFilename = selectedPicture.getName();
				Picture picture = new PictureImpl(pathOfPicture, null);
				int newPictureId = ServiceFacade.getInstance().addPicture(picture);

				if (newPictureId > 0) {
					ServiceFacade.getInstance().updatePictureId(passedIndexCard.getIndexCardId(), newPictureId);
					passedIndexCard.setPictureId(newPictureId);

					Alert successfulAlert = new Alert(AlertType.INFORMATION);
					successfulAlert.setTitle("Erfolgreich");
					successfulAlert.setHeaderText("Bild wurde erfolgreich hinzugefügt");
					successfulAlert.setContentText(
							"Ihr Bild " + pictureFilename + " wurde erfolgreich der Karteikarte beigefügt!");
					successfulAlert.showAndWait();
					loadPictureOfIndexCard();
					setDisableProperty();
					lblPictureMessage.setText("Bild: " + pictureFilename);
					createWindowController.loadIndexCards(passedIndexCard.getLectionId());
				} else {
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setTitle("Fehlgeschlagen");
					errorAlert.setHeaderText("Bild konnte nicht hinzugefügt werden");
					errorAlert.setContentText("Ihr Bild " + pictureFilename
							+ " konnte der Karteikarte nicht beigefügt werden. Kontaktieren Sie bitte den Support.");
					errorAlert.showAndWait();
				}
			}
		}
    }
    
    @FXML
    private void replacePictureOfIndexCard(ActionEvent event) {
		createWindowController = UserData.getInstance().getC();
		createWindowController.resetFilePicture();

		if (passedIndexCard != null) {
			final FileChooser pictureChooser = new FileChooser();
			pictureChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"),
					new FileChooser.ExtensionFilter("GIF", "*.gif"));
			pictureChooser.setTitle("Wählen Sie Ihr Bild für Ihre Karteikarte");
			Stage currentStage = (Stage) anchorPane.getScene().getWindow();
			File selectedPicture = pictureChooser.showOpenDialog(currentStage);

			if (selectedPicture != null) {
				String pathOfPicture = selectedPicture.getAbsoluteFile().toString();
				String pictureFilename = selectedPicture.getName();

				if (passedIndexCard.getPictureId() > 0) {
					Picture updatedPicture = new PictureImpl(passedIndexCard.getPictureId(), pathOfPicture, null);
					ServiceFacade.getInstance().updatePicture(updatedPicture);
					passedIndexCard.setPictureId(updatedPicture.getPictureId());

					Alert successfulAlert = new Alert(AlertType.INFORMATION);
					successfulAlert.setTitle("Erfolgreich");
					successfulAlert.setHeaderText("Bild wurde erfolgreich ersetzt");
					successfulAlert.setContentText(
							"Ihr Bild " + pictureFilename + " wurde erfolgreich eingefügt.");
					successfulAlert.showAndWait();
					loadPictureOfIndexCard();
					setDisableProperty();
					lblPictureMessage.setText("Bild: " + pictureFilename);
					createWindowController.loadIndexCards(passedIndexCard.getLectionId());
				} else {
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setTitle("Fehlgeschlagen");
					errorAlert.setHeaderText("Bild konnte nicht ersetzt werden");
					errorAlert.setContentText("Ihr Bild " + pictureFilename
							+ " konnte der Karteikarte nicht beigefügt werden. Kontaktieren Sie bitte den Support.");
					errorAlert.showAndWait();
				}
			}
		}
    }
 
    @FXML
    private void deletePicture(ActionEvent event) {
    	if(passedIndexCard.getPictureId() > 0) {
        	ServiceFacade.getInstance().deletePicture(passedIndexCard.getPictureId());
        	passedIndexCard.setPictureId(-1);
        	setDisableProperty();
        	loadPictureOfIndexCard();
    	}
    }
    
    @FXML
	public void loadPictureOfIndexCard() {
		if (passedIndexCard != null) {
			if (passedIndexCard.getPictureId() > 0) {
				int pictureId = passedIndexCard.getPictureId();
				
				Picture picture = ServiceFacade.getInstance().findPicture(pictureId);
				Image image = null;
				try {
					image = new Image(new FileInputStream(picture.getFileLocation()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				double imgHeight = image.getHeight();
				double imgWidth = image.getWidth();
				
				if(imgHeight > PIC_MAX_HEIGHT && imgWidth > PIC_MAX_WIDTH) {
					imgHeight = PIC_MAX_HEIGHT;
					imgWidth = PIC_MAX_WIDTH;
					
				} else if(imgHeight > PIC_MAX_HEIGHT && imgWidth < PIC_MAX_WIDTH) {
					imgHeight = PIC_MAX_HEIGHT;
					
				} else if(imgHeight < PIC_MAX_HEIGHT && imgWidth > PIC_MAX_WIDTH) {
					imgWidth = PIC_MAX_WIDTH;
				}
				
				scrlpnPicture.setContent(ivIndexCardPicture);
				
				ivIndexCardPicture.setFitWidth(imgWidth);
				ivIndexCardPicture.setFitHeight(imgHeight);

				ivIndexCardPicture.setImage(image);
				
				//optimizeWindowSize(imgWidth, imgHeight);
			} else {
				ivIndexCardPicture.setImage(null);
				lblPictureMessage.setText("Kein Bild vorhanden");
			}
		}
	}
    
    @FXML
    public void optimizeWindowSize(double width, double height) {
    	Stage currentStage = (Stage) anchorPane.getScene().getWindow();
    	currentStage.setWidth(width);
    	currentStage.setHeight(height);
    }
    
    @FXML
    public void setDisableProperty() {
    	IntegerProperty pictureIdOfIndexCard = new SimpleIntegerProperty();
        pictureIdOfIndexCard.set(passedIndexCard.getPictureId());
    	
        btnAddPicture.disableProperty().bind(pictureIdOfIndexCard.greaterThan(0));
        btnReplacePicture.disableProperty().bind(pictureIdOfIndexCard.lessThan(1));
        btnDeletePicture.disableProperty().bind(pictureIdOfIndexCard.lessThan(1));
    }
    
    @FXML
    public void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
        currentStage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	btnAddPicture.setOnAction(this::addPictureToIndexCard);
    	btnReplacePicture.setOnAction(this::replacePictureOfIndexCard);
    	btnDeletePicture.setOnAction(this::deletePicture);
    	btnCloseWindow.setOnAction(this::closeWindow);
    }

}
