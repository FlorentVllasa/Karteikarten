package de.kksystem.karteikarten.view.javafx.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.IndexCardStat;
import de.kksystem.karteikarten.model.interfaces.Lection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatisticsController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private BarChart<String, Number> bcIndexCard;

	@FXML
	private CategoryAxis cAxisDate;

	@FXML
	private NumberAxis nAxisTotalNumber;

	@FXML
	private BarChart<String, Number> bcLection;

	@FXML
	private CategoryAxis cAxisIndexCard;

	@FXML
	private NumberAxis nAxisTotalNumberLection;

	@FXML
	private TableView<IndexCard> tvIndexCards;

	@FXML
	public TableColumn<IndexCard, String> tcQuestion;

	@FXML
	private ComboBox<Category> cmbCategories;

	@FXML
	private ListView<Lection> lvLections;

	@FXML
	private Label lblIndexCardRight;

	@FXML
	private Label lblIndexCardWrong;

	@FXML
	private Label lblIndexCardNumberOfTrails;

	@FXML
	private Label lblLectionTotalRight;

	@FXML
	private Label lblLectionTotalWrong;

	@FXML
	private Label lblLectionTotalNumberOfTrials;

	@FXML
	private Button btnBack;

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
	private void showLoadedData() {
		tcQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
		loadCategories();

		if (cmbCategories.getItems().size() > 0) {
			loadLections(cmbCategories.getItems().get(0).getCategoryId());
		}

		dynamicTableIndexCardByListLection();
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
			List<IndexCard> allIndexCards = ServiceFacade.getInstance()
					.findAllIndexCardsByLectionIdWithoutHTML(lectionId);

			for (int i = 0; i < allIndexCards.size(); i++) {
				tvIndexCards.getItems().add(allIndexCards.get(i));
			}
		}
	}

	@FXML
	private void dynamicListLection(ActionEvent event) {
		lvLections.getItems().clear();
		tvIndexCards.getItems().clear();
		bcLection.getData().clear();
		bcIndexCard.getData().clear();
		resetLectionLabels();
		resetIndexCardLabels();
		
		if (cmbCategories.getSelectionModel().getSelectedItem() != null) {
			int selectedCategory = cmbCategories.getSelectionModel().getSelectedItem().getCategoryId();
			List<Lection> allLections = ServiceFacade.getInstance().findLectionByCategoryId(selectedCategory);

			for (int i = 0; i < allLections.size(); i++) {
				lvLections.getItems().add(allLections.get(i));
			}
		}
	}

	@FXML
	private void dynamicTableIndexCardByListLection() {
		lvLections.setOnMouseClicked(e -> {

			if (lvLections.getItems().size() > 0) {

				tvIndexCards.getItems().clear();
				resetIndexCardLabels();
				bcIndexCard.getData().clear();

				if (lvLections.getSelectionModel().getSelectedItem() != null) {
					int selectedLectionId = lvLections.getSelectionModel().getSelectedItem().getLectionId();
					Lection selectedLection = lvLections.getSelectionModel().getSelectedItem();

					List<IndexCard> allIndexCards = ServiceFacade.getInstance()
							.findAllIndexCardsByLectionIdWithoutHTML(selectedLectionId);

					for (int i = 0; i < allIndexCards.size(); i++) {
						tvIndexCards.getItems().add(allIndexCards.get(i));
					}

					loadLectionBarChart(selectedLection);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void loadLectionBarChart(Lection lection) {
		bcLection.getData().clear();

		bcLection.setTitle("Alle Karteikarten der Lektion (die letzten 7 Tage)\n");
		bcLection.setBarGap(3);
		bcLection.setCategoryGap(20);
		cAxisIndexCard.setLabel("Karteikarte");
		nAxisTotalNumberLection.setLabel("Anzahl richtig oder falsch");

		List<IndexCardStat> allIndexCardStatsByLectionId = ServiceFacade.getInstance()
				.findAllStatsByLectionId(lection.getLectionId(), 7);

		resetLectionLabels();
		calculateStatistics(allIndexCardStatsByLectionId, "Lection");
		
		Series<String, Number> seriesTotalNumberRight = new XYChart.Series<String, Number>();
		seriesTotalNumberRight.setName("Richtig");
		for (int i = 0; i < allIndexCardStatsByLectionId.size(); i++) {
			seriesTotalNumberRight.getData()
					.add(new XYChart.Data<String, Number>(
							String.valueOf(allIndexCardStatsByLectionId.get(i).getIndexCardId()),
							allIndexCardStatsByLectionId.get(i).getTotalNumberRight()));
		}

		Series<String, Number> seriesTotalNumberWrong = new XYChart.Series<String, Number>();
		seriesTotalNumberWrong.setName("Falsch");

		for (int i = 0; i < allIndexCardStatsByLectionId.size(); i++) {
			seriesTotalNumberWrong.getData()
					.add(new XYChart.Data<String, Number>(
							String.valueOf(allIndexCardStatsByLectionId.get(i).getIndexCardId()),
							allIndexCardStatsByLectionId.get(i).getTotalNumberWrong()));
		}
		bcLection.getData().addAll(seriesTotalNumberRight, seriesTotalNumberWrong);

		for (XYChart.Series<String, Number> series : bcLection.getData()) {
			for (XYChart.Data<String, Number> data : series.getData()) {
				IndexCard indexCard = ServiceFacade.getInstance()
						.findIndexCardByIdWithoutHTML(Integer.parseInt(data.getXValue()));
				Tooltip.install(data.getNode(),
						new Tooltip("Frage: " + indexCard.getQuestion() + "\n" + data.getYValue() + " mal"));
			}
		}
	}

	@FXML
	private void showIndexCardBarChart() {
		tvIndexCards.setOnMouseClicked(e -> {

			if (tvIndexCards.getItems().size() > 0) {

				if (tvIndexCards.getSelectionModel().getSelectedItem() != null) {
					IndexCard selectedIndexCard = tvIndexCards.getSelectionModel().getSelectedItem();
					loadIndexCardBarChart(selectedIndexCard);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void loadIndexCardBarChart(IndexCard selectedIndexCard) {
		bcIndexCard.getData().clear();

		bcIndexCard.setTitle("Karteikarte (die letzten 7 Tage)\n" + selectedIndexCard.getQuestion());
		bcIndexCard.setBarGap(3);
		bcIndexCard.setCategoryGap(20);
		cAxisDate.setLabel("Datum");
		nAxisTotalNumber.setLabel("Anzahl richtig oder falsch");

		List<IndexCardStat> allIndexCardStats = ServiceFacade.getInstance()
				.findAllStatsByIndexCardId(selectedIndexCard.getIndexCardId(), 7);

		calculateStatistics(allIndexCardStats, "IndexCard");
		
		Series<String, Number> seriesTotalNumberRight = new XYChart.Series<String, Number>();
		seriesTotalNumberRight.setName("Richtig");
		for (int i = 0; i < allIndexCardStats.size(); i++) {
			seriesTotalNumberRight.getData().add(new XYChart.Data<String, Number>(
					allIndexCardStats.get(i).getDate().toString(), allIndexCardStats.get(i).getTotalNumberRight()));
		}

		Series<String, Number> seriesTotalNumberWrong = new XYChart.Series<String, Number>();
		seriesTotalNumberWrong.setName("Falsch");

		for (int i = 0; i < allIndexCardStats.size(); i++) {
			seriesTotalNumberWrong.getData().add(new XYChart.Data<String, Number>(
					allIndexCardStats.get(i).getDate().toString(), allIndexCardStats.get(i).getTotalNumberWrong()));
		}
		bcIndexCard.getData().addAll(seriesTotalNumberRight, seriesTotalNumberWrong);

		for (XYChart.Series<String, Number> series : bcIndexCard.getData()) {
			for (XYChart.Data<String, Number> data : series.getData()) {
				Tooltip.install(data.getNode(),
						new Tooltip("Datum: " + data.getXValue() + "\n" + data.getYValue() + " mal"));
			}
		}
	}

	@FXML
	private void calculateStatistics(List<IndexCardStat> indexCardStatList, String type) {
		if (indexCardStatList.size() > 0) {
			double resultTotalNumberRight = 0;
			double resultTotalNumberWrong = 0;
			double totalTrials = 0;
			
			double resultTotalNumberRightPercentage = 0.0D;
			double resultTotalNumberWrongPercentage = 0.0D;

			for (int i = 0; i < indexCardStatList.size(); i++) {
				resultTotalNumberRight = resultTotalNumberRight + indexCardStatList.get(i).getTotalNumberRight();
				resultTotalNumberWrong = resultTotalNumberWrong + indexCardStatList.get(i).getTotalNumberWrong();
			}

			totalTrials = resultTotalNumberRight + resultTotalNumberWrong;
			resultTotalNumberRightPercentage = (resultTotalNumberRight * 100) / totalTrials;
			resultTotalNumberWrongPercentage = (resultTotalNumberWrong * 100) / totalTrials;
			
			DecimalFormat twoDecimalPlaces = new DecimalFormat("#.00");
			
			if(type.contentEquals("IndexCard")) {
				lblIndexCardNumberOfTrails.setText("100 % / (" + totalTrials +")");
				lblIndexCardRight.setText(twoDecimalPlaces.format(resultTotalNumberRightPercentage) + " % / (" + resultTotalNumberRight + ")");
				lblIndexCardWrong.setText(twoDecimalPlaces.format(resultTotalNumberWrongPercentage) + " % / (" + resultTotalNumberWrong + ")");
				
			} else if(type.contentEquals("Lection")) {
				lblLectionTotalNumberOfTrials.setText("100 % / (" + totalTrials +")");
				lblLectionTotalRight.setText(twoDecimalPlaces.format(resultTotalNumberRightPercentage) + " % / (" + resultTotalNumberRight + ")");
				lblLectionTotalWrong.setText(twoDecimalPlaces.format(resultTotalNumberWrongPercentage) + " % / (" + resultTotalNumberWrong + ")");
			}
		}
	}
	
	@FXML
	private void resetIndexCardLabels() {
		lblIndexCardNumberOfTrails.setText("---");
		lblIndexCardRight.setText("---");
		lblIndexCardWrong.setText("---");
	}
	
	@FXML
	private void resetLectionLabels() {
		lblLectionTotalNumberOfTrials.setText("---");
		lblLectionTotalRight.setText("---");
		lblLectionTotalWrong.setText("---");
	}
	
	public void closeCreateWindow(ActionEvent event) {
		Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
		stageInfo.close();
	}


//	@FXML
//	private void fillDB(ActionEvent event) {
//		LocalDate currDate = LocalDate.now().minusDays(7);
//		
//
//		
//		for(int i = 0; i < 7; i++) {
//			LocalDate newDate = currDate.plusDays(i);
//			Date sqlDate = Date.valueOf(newDate);
//			
//			IndexCardStat indexCardStat = new IndexCardStatImpl(5, 8, sqlDate, 4);
//			ServiceFacade.getInstance().addIndexCardStat(indexCardStat);
//		}
//		
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LocalDate ld = LocalDate.now();
		System.out.println(ld);
		showLoadedData();
		showIndexCardBarChart();
		cmbCategories.setOnAction(this::dynamicListLection);
		btnBack.setOnAction(this::closeCreateWindow);
	}
}