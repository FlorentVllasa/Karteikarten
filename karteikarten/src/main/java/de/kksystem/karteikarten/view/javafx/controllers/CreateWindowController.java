package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateWindowController implements Initializable {

	private WindowPresetSwitchStage switchStageHelper = new WindowPresetSwitchStage();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnSave;

	@FXML
	private ComboBox<Category> cmbCategories;
	
	@FXML
	private ListView<Lection> lvFavorites;

	@FXML
	private ListView<Lection> lvLections;
	
	@FXML
	private TableView<IndexCard> tvIndexCards;
	
    @FXML
    public TableColumn<IndexCard, String> tcQuestion;
    
    @FXML
    public TableColumn<IndexCard, String> tcAnswer;

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

    @FXML
    private HTMLEditor htmlQuestion;

    @FXML
    private HTMLEditor htmlAnswer;

	@FXML
	private void loadCategories() {
		int userId = UserData.getInstance().getUserId();
		List<Category> allCategories = ServiceFacade.getInstance().findCategoriesByUserId(userId);

		for (int i = 0; i < allCategories.size(); i++) {
			cmbCategories.getItems().add(allCategories.get(i));
		}
	}
	
	@FXML
	private void loadFavoritelist() {
		int favoritelistId = UserData.getInstance().getFavoritelistId();
		List<Lection> allFavoriteLections = ServiceFacade.getInstance().findLectionByFavoritelistId(favoritelistId);

		for (int i = 0; i < allFavoriteLections.size(); i++) {
			lvFavorites.getItems().add(allFavoriteLections.get(i));
		}
	}

	@FXML
	private void showLoadedData() {
		loadCategories();
		loadFavoritelist();
		
		dynamicListLection();
		
		dynamicTableIndexCardByListLection();
		dynamicTableIndexCardByListFavorite();
	}
	
	@FXML
	private void dynamicListLection() {
		tcQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        tcAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        
		cmbCategories.setOnAction(e -> {
			lvLections.getItems().clear();
			tvIndexCards.getItems().clear();
			int selectedCategory = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
			List<Lection> allLections = ServiceFacade.getInstance().findLectionByCategoryId(selectedCategory);

			for (int i = 0; i < allLections.size(); i++) {
				lvLections.getItems().add(allLections.get(i));
			}
		});
	}
	
	@FXML
	private void dynamicTableIndexCardByListFavorite() {
		lvFavorites.setOnMouseClicked(e -> {
			tvIndexCards.getItems().clear();
			int selectedLection = lvFavorites.getSelectionModel().getSelectedItem().getLectionId();
			List<IndexCard> allIndexCards = ServiceFacade.getInstance().findAllIndexCardsByLectionId(selectedLection);
			
			for(int i = 0; i < allIndexCards.size(); i++) {
				tvIndexCards.getItems().add(allIndexCards.get(i));
			}
		});
	}
	
	@FXML
	private void dynamicTableIndexCardByListLection() {
		lvLections.setOnMouseClicked(e -> {
			tvIndexCards.getItems().clear();
			int selectedLection = lvLections.getSelectionModel().getSelectedItem().getLectionId();
			List<IndexCard> allIndexCards = ServiceFacade.getInstance().findAllIndexCardsByLectionId(selectedLection);
			
			for(int i = 0; i < allIndexCards.size(); i++) {
				tvIndexCards.getItems().add(allIndexCards.get(i));
			}
		});
	}

	@FXML
	public void switchToAddCategoryDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Kategorienamen eingeben");
		tid.setTitle("Kategorie erstellen");
		tid.setHeaderText(null);
		tid.setContentText("Bitte Kategorienamen eingeben: ");

		Optional<String> enteredValue = tid.showAndWait();

		if(enteredValue.get().isBlank()){
			System.out.println("Fehler");
		}else{
			int userId = UserData.getInstance().getUserId();
			CategoryImpl category = new CategoryImpl(enteredValue.get(), null, userId);
			ServiceFacade.getInstance().addCategory(category);

			cmbCategories.getItems().add(category);
		}
	}

	@FXML
	public void switchToEditCategoryDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Neuer Kategoriename");
		tid.setTitle("Kategorie bearbeiten");
		tid.setHeaderText(null);
		tid.setContentText("Bitten den neuen Kategorienamen eingeben");

		Optional<String> enteredValue = tid.showAndWait();

		if(enteredValue.get().isBlank()){
			System.out.println("Fehler");
		}else{
			int userId = UserData.getInstance().getUserId();
			Category category = cmbCategories.getSelectionModel().getSelectedItem();
			ServiceFacade.getInstance().updateCategoryName(category, enteredValue.get());
			List<Category> allCategories = ServiceFacade.getInstance().findCategoriesByUserId(userId);
			ObservableList<Category> obsList = FXCollections.observableList(allCategories);
			cmbCategories.setItems(obsList);
		}
	}

	@FXML
	public void switchToDeleteCategoryDialog(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Kategorie loeschen!");
		alert.setHeaderText("Achtung!");
		alert.setContentText("Soll endgueltig geloescht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK, hier wird gelöscht
		} else {

		}
	}

	@FXML
	public void switchtoAddLectionDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Lektionnamen eingeben");
		tid.setTitle("Lektion erstellen");
		tid.setHeaderText(null);
		tid.setContentText("Bitte Lektionnamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

        int favListId = UserData.getInstance().getFavoritelistId();
        int catId = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
        LectionImpl lection = new LectionImpl(enteredValue.get(), null, null, catId, favListId);
        ServiceFacade.getInstance().addLection(lection);
        lvLections.getItems().add(lection);
	}

	@FXML
	public void switchToEditLectionDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Neuer Lektionname");
		tid.setTitle("Lektion bearbeiten");
		tid.setHeaderText(null);
		tid.setContentText("Bitte neuen Lektionnamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

		if (enteredValue.get().isBlank()) {
			System.out.println("Unknown");
		} else {
			System.out.println(enteredValue.get());
		}
	}

	@FXML
	public void switchToDeleteLectionDialog(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Lektion loeschen!");
		alert.setHeaderText("Achtung!");
		alert.setContentText("Soll endgueltig geloescht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK, hier wird gelöscht
		} else {

		}
	}

	public void addIndexCardIntoTableView(ActionEvent event){
        int lectionId = lvLections.getSelectionModel().getSelectedItem().getLectionId();
        String question = getText(htmlQuestion.getHtmlText());
        String answer = getText(htmlAnswer.getHtmlText());

        if(question.isBlank() && answer.isBlank()){
            System.out.println("Frage und Antwort angeben!");
        }else if(question.isBlank()){
            System.out.println("Bitte Frage eingeben!");
        }else if(answer.isBlank()){
            System.out.println("Bitte Antwort angeben!");
        }else{
            IndexCardImpl indexCard = new IndexCardImpl(question, answer, null, lectionId, null);
            ServiceFacade.getInstance().addIndexCard(indexCard);
            tvIndexCards.getItems().add(indexCard);
        }

    }

    public static String getText(String htmlText) {
        String result = "";
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer text = new StringBuffer(htmlText.length());
        while (matcher.find()) {
            matcher.appendReplacement(
                    text,
                    " ");
        }
        matcher.appendTail(text);
        result = text.toString().trim();
        return result;
    }

	@FXML
	public void switchToEditIndexCardDialog(ActionEvent event) {
		switchStageHelper.createWindowNewStage("/fxml/editIndexCardWindow.fxml", "Karteikarte bearbeiten!",
				new EditIndexCardController());
	}

	@FXML
	public void switchToDeleteIndexCardDialog(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Karteikarte loeschen!");
		alert.setHeaderText("Achtung!");
		alert.setContentText("Soll endgueltig geloescht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK, hier wird gelöscht
		} else {

		}
	}

	@FXML
	public void switchtoEditFavoritelistDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Neuer Favoritenname");
		tid.setTitle("Favoritenliste bearbeiten");
		tid.setHeaderText(null);
		tid.setContentText("Bitte neuen Favoritennamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

		if (enteredValue.get().isBlank()) {
			System.out.println("Unknown");
		} else {
			System.out.println(enteredValue.get());
		}
	}

	@FXML
	public void switchToDeleteFavoritelistDialog(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Favorit loeschen!");
		alert.setHeaderText("Achtung!");
		alert.setContentText("Soll endgueltig geloescht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK, hier wird gelöscht
		} else {

		}
	}

	/*
	 * Diese Methode nimmt sich die Stage Information der Scene und schließt das
	 * Fenster daraufhin. Dies wird erreicht in dem die Stage Information
	 * irgendeiner Komponente der Scene ermittelt wird und dann in ein Stage Objekt
	 * umgewandelt wird.
	 */
	public void closeCreateWindow(ActionEvent event) {
		Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
		stageInfo.close();
	}

	/* Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		showLoadedData();
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
		btnSave.setOnAction(this::addIndexCardIntoTableView);
	}
}
