package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditIndexCardController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeletePicture;

    @FXML
    private Button btnSave;

    @FXML
    private HTMLEditor htmlEditQuestion;

    @FXML
    private HTMLEditor htmlEditAnswer;

    private CreateWindowController createWindowController = new CreateWindowController();
    private IndexCard indexCardTemp = null;

    public void setIndexcard(IndexCard card){
        indexCardTemp = card;
    }


    public void loadClickedIndexCard(String question, String answer) {
        htmlEditQuestion.setHtmlText(question);
        htmlEditAnswer.setHtmlText(answer);
    }

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
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
    public void saveChanges(ActionEvent event) {
        String question = htmlEditQuestion.getHtmlText();
        String answer = htmlEditAnswer.getHtmlText();
        String questionPlain = getText(htmlEditQuestion.getHtmlText());
        String answerPlain = getText(htmlEditAnswer.getHtmlText());

        if (questionPlain.isBlank() && answerPlain.isBlank()) {
            lblMessage.setText("Bitte Frage und Antwort angeben!");
        } else if (questionPlain.isBlank()) {
            lblMessage.setText("Bitte eine Frage angeben!");
        } else if (answerPlain.isBlank()) {
            lblMessage.setText("Bitte eine Antwort angeben!");
        } else{
            if(indexCardTemp != null){
                int lectionId = indexCardTemp.getLectionId();
                int indexCardId = indexCardTemp.getIndexCardId();
                int pictureId = indexCardTemp.getPictureId();
                IndexCardImpl card = new IndexCardImpl(indexCardId, question, answer, null, lectionId, pictureId);
                ServiceFacade.getInstance().updateIndexCard(card);
                createWindowController = UserData.getInstance().getC();
                createWindowController.loadIndexCards(lectionId);
                Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
                stageInfo.close();
            }else{
                System.out.println("Fehler! keine IndexCard bekommen!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::closeWindow);
        btnSave.setOnAction(this::saveChanges);
    }

}
