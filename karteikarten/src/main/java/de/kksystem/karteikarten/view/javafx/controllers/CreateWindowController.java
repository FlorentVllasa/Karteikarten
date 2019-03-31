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
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
	private Button btnAddPicture;
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
    private void test() {
    }

	@FXML
	private void showLoadedData() {
		tcQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
		tcAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
		loadCategories();
		loadLections(cmbCategories.getItems().get(0).getCategoryId());
		loadFavoritelist();

		dynamicTableIndexCardByListLection();
		dynamicTableIndexCardByListFavorite();
	}

	@FXML
	private void loadCategories() {
		cmbCategories.getItems().clear();
		int userId = UserData.getInstance().getUserId();
		List<Category> allCategories = ServiceFacade.getInstance().findCategoriesByUserId(userId);

		for (int i = 0; i < allCategories.size(); i++) {
			cmbCategories.getItems().add(allCategories.get(i));
		}

		if (cmbCategories.getItems().size() > 0) {
			cmbCategories.setValue(allCategories.get(0));
		}
	}

	@FXML
	private void loadFavoritelist() {
		lvFavorites.getItems().clear();
		int favoritelistId = UserData.getInstance().getFavoritelistId();
		List<Lection> allFavoriteLections = ServiceFacade.getInstance().findLectionByFavoritelistId(favoritelistId);

		for (int i = 0; i < allFavoriteLections.size(); i++) {
			lvFavorites.getItems().add(allFavoriteLections.get(i));
		}
	}

	@FXML
	public void loadLections(int categoryId) {
		lvLections.getItems().clear();
		if (cmbCategories.getItems().size() > 0) {
			List<Lection> allLections = ServiceFacade.getInstance().findLectionByCategoryId(categoryId);

			for (int i = 0; i < allLections.size(); i++) {
				lvLections.getItems().add(allLections.get(i));
			}
		}
	}

	@FXML
	public void loadIndexCards(int lectionId) {
		tvIndexCards.getItems().clear();
		if (lvLections.getItems().size() > 0) {
			List<IndexCard> allIndexCards = ServiceFacade.getInstance().findAllIndexCardsByLectionId(lectionId);

			for (int i = 0; i < allIndexCards.size(); i++) {
				tvIndexCards.getItems().add(allIndexCards.get(i));
			}
		}
	}

	@FXML
	private void dynamicListLection(ActionEvent event) {
		lvLections.getItems().clear();
		tvIndexCards.getItems().clear();

		if (cmbCategories.getSelectionModel().getSelectedItem() != null) {
			int selectedCategory = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
			List<Lection> allLections = ServiceFacade.getInstance().findLectionByCategoryId(selectedCategory);

			for (int i = 0; i < allLections.size(); i++) {
				lvLections.getItems().add(allLections.get(i));
			}
		}
	}

	@FXML
	private void dynamicTableIndexCardByListFavorite() {
		lvFavorites.setOnMouseClicked(e -> {
			lvLections.getSelectionModel().select(null);
			
			if (lvFavorites.getItems().size() > 0) {
				tvIndexCards.getItems().clear();
				
				if(lvFavorites.getSelectionModel().getSelectedItem() != null) {
					int selectedLection = lvFavorites.getSelectionModel().getSelectedItem().getLectionId();
					
					List<IndexCard> allIndexCards = ServiceFacade.getInstance()
							.findAllIndexCardsByLectionId(selectedLection);

					for (int i = 0; i < allIndexCards.size(); i++) {
						tvIndexCards.getItems().add(allIndexCards.get(i));
					}
				}
			}
		});
	}

	@FXML
	private void dynamicTableIndexCardByListLection() {
		lvLections.setOnMouseClicked(e -> {
			lvFavorites.getSelectionModel().select(null);
			
			if (lvLections.getItems().size() > 0) {

				tvIndexCards.getItems().clear();
				
				if(lvLections.getSelectionModel().getSelectedItem() != null) {
					int selectedLection = lvLections.getSelectionModel().getSelectedItem().getLectionId();
					
					List<IndexCard> allIndexCards = ServiceFacade.getInstance()
							.findAllIndexCardsByLectionId(selectedLection);

					for (int i = 0; i < allIndexCards.size(); i++) {
						tvIndexCards.getItems().add(allIndexCards.get(i));
					}
				}
			}
		});
	}
	
	@FXML
	private void setDisableProperty() {
		miEditCategory.disableProperty().bind(cmbCategories.getSelectionModel().selectedItemProperty().isNull());
		miDeleteCategory.disableProperty().bind(cmbCategories.getSelectionModel().selectedItemProperty().isNull());
		miAddLection.disableProperty().bind(cmbCategories.getSelectionModel().selectedItemProperty().isNull());
		
		miEditLection.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		miDeleteLection.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		htmlQuestion.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		htmlAnswer.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		btnSave.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		btnAddPicture.disableProperty().bind(
				Bindings.and(lvLections.getSelectionModel().selectedItemProperty().isNull(),
						lvFavorites.getSelectionModel().selectedItemProperty().isNull()));
		
		miEditIndexCard.disableProperty().bind(tvIndexCards.getSelectionModel().selectedItemProperty().isNull());
		miDeleteIndexCard.disableProperty().bind(tvIndexCards.getSelectionModel().selectedItemProperty().isNull());
		
		// miEditFavo und miDeleteFavo noch nicht implementiert
		miEditFavoritelist.setDisable(true);
		miDeleteFavoritelist.setDisable(true);
	}

	@FXML
	public void switchToAddCategoryDialog(ActionEvent event) {
		TextInputDialog tid = new TextInputDialog("Kategorienamen eingeben");
		tid.setTitle("Kategorie erstellen");
		tid.setHeaderText(null);
		tid.setContentText("Bitte Kategorienamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

		if (enteredValue.get().isBlank()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Name wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie einen Kategorienamen ein.");
			alert.showAndWait();
		} else {
			int userId = UserData.getInstance().getUserId();
			Category category = new CategoryImpl(enteredValue.get(), null, userId);
			ServiceFacade.getInstance().addCategory(category);

			loadCategories();
			cmbCategories.getSelectionModel().selectLast();
		}
	}

	@FXML
	public void switchToEditCategoryDialog(ActionEvent event) {
		Category category = cmbCategories.getSelectionModel().getSelectedItem();

		TextInputDialog tid = new TextInputDialog("Kategoriename bearbeiten");
		tid.setTitle("Kategorie bearbeiten");
		tid.setHeaderText(null);
		tid.setContentText("Bitten den neuen Kategorienamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

		if (enteredValue.get().isBlank()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Kategoriename wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie den neuen Kategorienamen ein.");
			alert.showAndWait();
		} else {
			int selectedCategoryIndex = cmbCategories.getSelectionModel().getSelectedIndex();
			ServiceFacade.getInstance().updateCategoryName(category, enteredValue.get());

			loadCategories();
			cmbCategories.getSelectionModel().select(selectedCategoryIndex);
		}
	}

	@FXML
	public void switchToDeleteCategoryDialog(ActionEvent event) {
		Category selectedCategory = cmbCategories.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Kategorie löschen");
		alert.setHeaderText("Achtung");
		alert.setContentText("Soll die Kategorie '" + selectedCategory.getName() + "' wirklich gelöscht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ServiceFacade.getInstance().deleteCategory(selectedCategory);
			loadCategories();
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

		if (enteredValue.get().isBlank()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Lektionsname wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie einen Lektionsnamen ein.");
			alert.showAndWait();
		} else {
			int catId = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
			int selectedCategoryIndex = cmbCategories.getSelectionModel().getSelectedIndex();
			LectionImpl lection = new LectionImpl(enteredValue.get(), null, null, catId);
			ServiceFacade.getInstance().addLection(lection);
			cmbCategories.getSelectionModel().clearSelection();
			cmbCategories.getSelectionModel().select(selectedCategoryIndex);
		}
	}

	@FXML
	public void switchToEditLectionDialog(ActionEvent event) {
		Lection selectedLection = null;
		
		if(!lvLections.getSelectionModel().isEmpty()) {
			selectedLection = lvLections.getSelectionModel().getSelectedItem();
		}else if(!lvFavorites.getSelectionModel().isEmpty()) {
			selectedLection = lvFavorites.getSelectionModel().getSelectedItem();
		}

		TextInputDialog tid = new TextInputDialog("Neuer Lektionname");
		tid.setTitle("Lektion bearbeiten");
		tid.setHeaderText(null);
		tid.setContentText("Bitte neuen Lektionnamen eingeben:");

		Optional<String> enteredValue = tid.showAndWait();

		if (enteredValue.get().isBlank()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Name wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie den neuen Namen ein.");
			alert.showAndWait();
		} else {
			if(selectedLection != null) {
				ServiceFacade.getInstance().updateName(enteredValue.get(), selectedLection.getLectionId());
	
				int lastSelectedCategory = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
				loadLections(lastSelectedCategory);
				loadFavoritelist();
			}
		}
	}

	@FXML
	public void switchToDeleteLectionDialog(ActionEvent event) {
		Lection selectedLection = null;
		
		if(!lvLections.getSelectionModel().isEmpty()) {
			selectedLection = lvLections.getSelectionModel().getSelectedItem();
		}else if(!lvFavorites.getSelectionModel().isEmpty()) {
			selectedLection = lvFavorites.getSelectionModel().getSelectedItem();
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Lektion löschen");
		alert.setHeaderText("Achtung");
		alert.setContentText("Soll die Lektion '" + selectedLection.getName() + "' wirklich gelöscht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
				if(selectedLection != null) {
				ServiceFacade.getInstance().deleteLection(selectedLection);
	
				int lastSelectedCategory = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
				loadLections(lastSelectedCategory);
				loadFavoritelist();
			}
		} else {

		}
	}

	public void addIndexCardIntoTableView(ActionEvent event) {
		String question = getText(htmlQuestion.getHtmlText());
		String answer = getText(htmlAnswer.getHtmlText());

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Fehler");

		if (question.isBlank() && answer.isBlank()) {
			alert.setHeaderText("Frage und Antwort wurden nicht eingetragen");
			alert.setContentText("Bitte tragen Sie unten Ihre Frage und die dazugehörige Antwort ein.");

			alert.showAndWait();
		} else if (question.isBlank()) {
			alert.setHeaderText("Frage wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie unten Ihre Frage ein.");

			alert.showAndWait();
		} else if (answer.isBlank()) {
			alert.setHeaderText("Antwort wurde nicht eingetragen");
			alert.setContentText("Bitte tragen Sie unten Ihre Antwort ein.");

			alert.showAndWait();
		} else {
			Lection selectedLection = null;
			
			if(!lvLections.getSelectionModel().isEmpty()) {
				selectedLection = lvLections.getSelectionModel().getSelectedItem();
			}else if(!lvFavorites.getSelectionModel().isEmpty()) {
				selectedLection = lvFavorites.getSelectionModel().getSelectedItem();
			}
			
			if (selectedLection != null) {
				int lectionId = lvLections.getSelectionModel().getSelectedItem().getLectionId();
				IndexCard indexCard = new IndexCardImpl(question, answer, null, lectionId, null);
				ServiceFacade.getInstance().addIndexCard(indexCard);
				loadIndexCards(lectionId);
			}
		}
	}

	public static String getText(String htmlText) {
		String result = "";
		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer text = new StringBuffer(htmlText.length());
		while (matcher.find()) {
			matcher.appendReplacement(text, " ");
		}
		matcher.appendTail(text);
		result = text.toString().trim();
		return result;
	}

	@FXML
	public void switchToEditIndexCardDialog(ActionEvent event) {
		//switchStageHelper.createWindowNewStage("/fxml/editIndexCardWindow.fxml", "Karteikarte bearbeiten!", new EditIndexCardController());
        try{
            final URL fxmlUrl = getClass().getResource("/fxml/EditIndexCardWindow.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            fxmlLoader.setController(new EditIndexCardController());
            Parent root = fxmlLoader.load();

			//load IndexCard into the two HTML-Editors of EditIndexCardWindow.fxml
			IndexCard indexCard = tvIndexCards.getSelectionModel().getSelectedItem();
			int lectionId = indexCard.getLectionId();
			String question = indexCard.getQuestion();
			String answer = indexCard.getAnswer();
			EditIndexCardController controller = fxmlLoader.getController();
			controller.loadClickedIndexCard(question, answer);
			controller.setIndexcard(indexCard);

            Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }

	}

	@FXML
	public void switchToDeleteIndexCardDialog(ActionEvent event) {
		IndexCard selectedIndexCard = tvIndexCards.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Karteikarte löschen");
		alert.setHeaderText("Achtung");
		alert.setContentText("Soll die ausgewählte Karteikarte wirklich gelöscht werden?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ServiceFacade.getInstance().deleteIndexCard(selectedIndexCard);
			loadIndexCards(selectedIndexCard.getLectionId());
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


	}

	@FXML
	public void switchToDeleteFavoritelistDialog(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Favorit löschen");
		alert.setHeaderText("Achtung");
		alert.setContentText("Soll endgültig gelöscht werden?");

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
		setDisableProperty();
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
		cmbCategories.setOnAction(this::dynamicListLection);
	}
}
