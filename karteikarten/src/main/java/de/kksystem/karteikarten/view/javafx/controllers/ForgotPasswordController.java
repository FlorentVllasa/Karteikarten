package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable{
	LoginWindow lw = new LoginWindow();
	WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txtFieldEmail;
    
    @FXML
    private TextField txtRecoveryKey;
    
    @FXML
    private Button btnSendEmail;
    
	@FXML
    private Button btnConfirm;
	
	@FXML
    private Button btnCancel;
	
	private String generatedKey;
	
	private void switchToLoginWindow(ActionEvent event){
        Stage stage = new Stage();
        try{
            lw.start(stage);
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stageInfo = (Stage) btnCancel.getScene().getWindow();
        stageInfo.close();
    }
	
	private void switchToCreateNewPasswordWindow(ActionEvent event) {
		
	}
	
	private String generateKey() {
		final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_§$%&/()=#+*";
	    final SecureRandom RANDOM = new SecureRandom();
	    StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        System.out.println(sb.toString());
        return sb.toString();
	}
	
	private void sendEmail(String receiver, String subject, String msg) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.setProperty("mail.smtp.user", "recovery.karteikarten@gmail.com");
        prop.setProperty("mail.smtp.password", "Lernmolwas!");
        prop.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication("recovery.karteikarten@gmail.com","Lernmolwas!");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("recovery.karteikarten@gmail.com", "Karteikarten Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (javax.mail.SendFailedException e) {
        	e.printStackTrace();
        	//System.out.println(e.getInvalidAddresses());
        	//System.out.println(e.getValidUnsentAddresses());
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	private void sendRecovery(ActionEvent event) {
		
		if (txtFieldEmail.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Achtung!");
    		alert.setHeaderText("Bitte füllen Sie das Emailfeld aus!");
    		alert.showAndWait();
		} else if (ServiceFacade.getInstance().findUserByEMail(txtFieldEmail.getText()) == null) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText("Wir können Ihre Emailadresse leider nicht finden. Bitte Überprüfen Sie Ihre Emailadresse.");
    		alert.showAndWait();
		} else {
			generatedKey = generateKey();
            
            String msg = 	"<!DOCTYPE html>\r\n" + 
            				"<html>\r\n" + 
            				"<body>\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"<p>Hallo!</p>" +
            				"<br>\r\n" +
		            		"<p>Du hast gerade eine Wiederherstellungschlüssel angefordert!</p>\r\n" + 
		            		"<p>Kopier den Schlüssel <b>ins zweite Feld</b> des Wiederherstellungsfenster und auf Bestätigen drucken!</p> \r\n" + 
		            		"<p>Ein neues Passwort wird dann zu dieser Emailadresse zugeschickt.</p>\r\n" + 
		            		"<p>Dein Code lautet: <b><i>" + generatedKey + "</i></b></p> \r\n" + 
		            		"<br>\r\n" + 
		            		"<p>Mit freundlichen Grüßen</p>\r\n" + 
		            		"<p>Dein Karteikarten Team</p>\r\n" + 
		            		"\r\n" + 
		            		"\r\n" + 
		            		"\r\n" + 
		            		"</body>\r\n" + 
		            		"</html>\r\n";
            
            sendEmail(txtFieldEmail.getText(), "Wiederherstellen deines Karteikarten-Kontos", msg);
            
		}
	}
	
	private void compareKey(ActionEvent event) {
		if (txtRecoveryKey.getText().equals(generatedKey)) {
			wpss.createWindowSwitchScene("/fxml/CreateNewPasswortWindow.fxml", new CreateNewPasswortWindowController(txtFieldEmail.getText()), lw.getWindow());
		} else {
			
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		btnSendEmail.setOnAction(this::sendRecovery);
		btnConfirm.setOnAction(this::compareKey);
		btnCancel.setOnAction(this::switchToLoginWindow);
	}

}
