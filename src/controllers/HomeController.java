package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionSQL;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HomeController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	
	

	    @FXML
	    private Button b0=new Button();

	    @FXML
	    private Button b1=new Button();

	    @FXML
	    private Button b2=new Button();

	    @FXML
	    private Button b3=new Button();

	    @FXML
	    private Button b4=new Button();

	    @FXML
	    private Button b5=new Button();

	    @FXML
	    private Button b6=new Button();
	
	
	
	    
	public Button[] tab_but= {b0,b1,b2,b3,b4,b5,b6};
	
	
	
	
	
    @FXML
    private Label agence;

    @FXML
    private Label user;
    
	private Parent fxml;
	 @FXML
	 private AnchorPane root;
	 
	 
	 public void modifier_bouttons(int pos)
	 {
		 for (int i = 0; i < tab_but.length; i++) {
			 if(i==pos)
			 {
			 tab_but[i].setStyle("-fx-background-color: blue;"); // Applique la couleur initiale
			 int c=i;
			 tab_but[i].setOnMousePressed(e -> tab_but[c].setStyle("-fx-background-color: red;"));
			 }
			 else
			 {
				 tab_but[i].setStyle("-fx-background-color: #373737;"); // Applique la couleur initiale
				// int c=i;
				 //tab_but[i].setOnMousePressed(e -> tab_but[c].setStyle("-fx-background-color: red;"));
			 }
			 
		}
	 }
	 
    @FXML
    void Acceuil() {
    	modifier_bouttons(0);
    	
    	
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acceuil.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Contrats() {
    	modifier_bouttons(1);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Contrats.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Factures() {
    	modifier_bouttons(2);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Factures.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Historique() {
    	modifier_bouttons(3);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Historique.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Locataires() {
    	modifier_bouttons(4);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Locataires.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Locations() {
    	modifier_bouttons(5);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Locations.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }

    @FXML
    void Logement() {
    	modifier_bouttons(6);
    	try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Logement.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
    	} catch (IOException e) {
			e.printStackTrace()	;	
			}

    }
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
		String sql="select userName,agence from admin";
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			if(rsl.next())
    		{
				user.setText(rsl.getString("userName"));
				agence.setText(rsl.getString("agence"));
				
    		}
			

    	}
    	catch (SQLException e) {
			e.printStackTrace()	;	
			}
    	

		
		try {
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acceuil.fxml"));
			  root.getChildren().removeAll();
			  root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace()	;	
			}
	}

}
