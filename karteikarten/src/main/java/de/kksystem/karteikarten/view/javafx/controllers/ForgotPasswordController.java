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
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "express-relay.jangosmtp.net");
        prop.put("mail.smtp.port", 2525);
        prop.put("mail.smtp.ssl.trust", "express-relay.jangosmtp.net");
        
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("letmetry", "@X123abc");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("forgot@karteikarten.com"));
            System.out.println("1");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ignatiusjuanpradipta@gmail.com"));
            System.out.println("2");
            message.setSubject("Recovery Key");
            System.out.println("3");
            String msg = "This is my first email using JavaMailer";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            //MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            //attachmentBodyPart.attachFile(new File("pom.xml"));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            //multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		btnConfirm.setOnAction(this::sendRecovery);
		//btnBack.setOnAction(this::switchToLoginWindow);
	}

}
