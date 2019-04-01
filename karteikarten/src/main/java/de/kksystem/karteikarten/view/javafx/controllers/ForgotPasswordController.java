package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import com.sun.mail.smtp.SMTPAddressFailedException;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable{
	LoginWindow lw = new LoginWindow();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    
    @FXML
    private TextField txtFieldEmail;
    
    @FXML
    private TextField txtRecoveryKey;
    
    @FXML
    private Button btnSendEmail;
    
	@FXML
    private Button btnConfirm;
	
	@FXML
    private Button btnBack;
	
	public void switchToLoginWindow(ActionEvent event){
        Stage stage = new Stage();
        try{
            lw.start(stage);
        }catch(IOException io){
            io.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stageInfo = (Stage) btnBack.getScene().getWindow();
        stageInfo.close();
    }
	
	private void sendRecovery(ActionEvent event) {
		
		if (ServiceFacade.getInstance().findUserByEMail(txtFieldEmail.getText()) != null) {
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
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(txtFieldEmail.getText()));
	            message.setSubject("Wiederherstellen deines Karteikarten-Kontos");
	            String code = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	            
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
			            		"<p>Dein Code lautet: <b><i>" + code + "</i></b></p> \r\n" + 
			            		"<br>\r\n" + 
			            		"<p>Mit freundlichen Grüßen</p>\r\n" + 
			            		"<p>Dein Karteikarten Team</p>\r\n" + 
			            		"\r\n" + 
			            		"\r\n" + 
			            		"\r\n" + 
			            		"</body>\r\n" + 
			            		"</html>\r\n";
	
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
        } else {
        	
        }
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		btnSendEmail.setOnAction(this::sendRecovery);
		//btnBack.setOnAction(this::switchToLoginWindow);
	}

}
