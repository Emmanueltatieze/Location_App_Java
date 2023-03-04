package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


//import com.mysql.cj.protocol.Message;
import javax.mail.Message;
import javax.mail.Address;
import javax.activation.DataSource;

import application.ConnectionSQL;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SignInController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	

    @FXML
    private Button btn_mdp_oubli;

    @FXML
    private Button btn_seConect;

    @FXML
    private PasswordField user_mdp;

    @FXML
    private TextField user_name;
    

    @FXML
    private VBox vbox;
    
    private Parent fxml;

    @FXML
    void OnConect() {

    	String nom=user_name.getText();
    	String mdp=user_mdp.getText();
    	String sql ="select userName, password from admin";
    	
    	try {
    		st=cnx.prepareStatement(sql);
    		rsl=st.executeQuery();
    		if(rsl.next())
    		{
    	    	if(nom.equals(rsl.getString("userName"))&&mdp.equals(rsl.getString("password")))
    	    	{
    	    		System.out.println("bien!");
    	    		vbox.getScene().getWindow().hide();
    	    		Stage home =new Stage();
    	    		try {
    	  			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
    	  			  Scene scene =new Scene(fxml);
    	  			  scene.setFill(Color.TRANSPARENT);
    	  			  home.setScene(scene);
    	  			  home.show();
    	    		}catch (IOException e) {
    					// TODO: handle exception
    	    			System.out.println("1");
    	    			e.printStackTrace();
    				}
    	    				
    	    	}
    	    	else {
    	    		System.out.println("nom d'utilisateur ou mot de passe incorrect");
    	    		Alert alert =new Alert(AlertType.ERROR, "nom d'utilisateur ou mot de passe incorrect", javafx.scene.control.ButtonType.OK);
    	    		alert.showAndWait();
    	    	}
    		}
			
		} catch (Exception e) {
			System.out.println("1");

		e.printStackTrace();}
    	
    	
    	
    	
    	
    	
    	
    	
    }

    @FXML
    void OnGetMdp() {
    	 String to = "emmanoutatieze@gmail.com";
         String from = "applilocation@gmail.com";
         String host = "smtp.gmail.com";
         final String username = "applilocation@gmail.com";
         final String password = "MonAppli@1234";

         // Configuration des propriétés
         Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.host", host);
         props.put("mail.smtp.port", "587");

         // Création d'une session
         Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(username, password);
               }
            });

         try {
            // Création d'un objet MimeMessage
            Message message = new MimeMessage(session);

            // Paramétrage de l'expéditeur, du destinataire et de l'objet
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
            message.setSubject("Objet de l'e-mail");

            // Ajout du contenu de l'e-mail
            message.setText("Contenu de l'e-mail");

            // Envoi de l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès");

         } catch (MessagingException e) {
            throw new RuntimeException(e);
         }
//    	// Paramètres SMTP du serveur de messagerie
//        String host = "smtp.gmail.com";
//        String port = "587";
//        String username = "applilocation@gmail.com";
//        String password = "MonAppli@1234";
//
//        // Paramètres du message
//        String to = "emmanoutatieze@gmail.com";
//        String subject = "Test Email";
//        String body = "Hello, this is a test email.";
//
//        // Configuration des propriétés SMTP
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", port);
//
//        // Création de la session
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            // Création du message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject(subject);
//            message.setText(body);
//
//            // Envoi du message
//            Transport.send(message);
//
//            System.out.println("Message sent successfully.");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
    	
    	
    	
    	
    	
    	

//    	String sql="select mail from admin where userName='"+user_name.getText()+"'";
//    	String email="empty";
//    	try {
//    		st=cnx.prepareStatement(sql);
//			rsl=st.executeQuery();
//			if(rsl.next()) {
//				email=rsl.getString("mail");
//			}
//		} catch (SQLException e) {
//			System.out.println("2");
//
//		e.printStackTrace();}
//    	sendMail(email);
//    }
//    
//    
//    public void sendMail(String recepient)
//    {
//    	System.out.println("Preparating to send email...");
//    	Properties properties =new Properties();
//    	
//    	//permettre les authorisations
//    	properties.put("mail.smtp.auth", "true");
//    	
//    	//Set TLS encryptio enabled
//    	properties.put("mail.smtp.starttls.enable", "true");
//    	
//    	//set SMTP host
//    	properties.put("mail.smtp.host", "smtp.gmail.com");
//    	
//    	//set SMTP port
//    	properties.put("mail.smtp.port", "587");
//    	
//    	//adresse gmail
//    	final String MyaccountEmail="applilocation@gmail.com";
//    	//mot de passe de cette adresse gmail
//    	final String password="MonAppli@1234";
//    	
//    	//create a session with account credentials
//    	
//    Session session=Session.getInstance(properties,new Authenticator() {
//    	protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
//    		return new javax.mail.PasswordAuthentication(MyaccountEmail, password);
//    	}
//	});
//    
//    Message message=preparedMessage(session, MyaccountEmail, recepient);
//    	try {
//    		Transport.send((javax.mail.Message) message);
//    		Alert alert =new Alert(AlertType.ERROR, "consultez votre boite mail", javafx.scene.control.ButtonType.OK);
//    		alert.showAndWait();
//			
//		} catch (MessagingException e) {
//			System.out.println("3");
//
//			e.printStackTrace();
//		}
//    		
   
    }
    
    @SuppressWarnings("unused")
    private Message preparedMessage(Session session,String myAccountEmail, String recepient)
    {
    	String sql="select * from admin where userName='"+user_name.getText()+"'";
    	String pass="empty";
    	try {
			st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			if(rsl.next()) {
				pass=rsl.getString("password");
			}
    }catch (SQLException e) {
		System.out.println("4");

    	e.printStackTrace();	
    	}
    	
    	String text="Votre mot de passe est : "+pass+"";

    	String objet="Recuperation mot de passe";
    	Message message= new MimeMessage(session);
    	try {
    		message.setFrom(new InternetAddress(myAccountEmail));
    		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient) );
    		message.setSubject(objet);
    		String htmlcode="<h1>"+text+"</h1> <h2><b> </b></h2>";
    		message.setContent(htmlcode,"text/html");
    		//return message;
			
		} catch (MessagingException e) {
			System.out.println("5");

			e.printStackTrace()	;	}
    	return message;

    
    }
    
    
    

	
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
	  
   	  
	}

}
