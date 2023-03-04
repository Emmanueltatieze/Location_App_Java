package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.jfoenix.controls.*;
import javafx.util.Duration;

public class MainController implements Initializable {


    @FXML
    private Button btn_seconnecter;

    @FXML
    private Button btn_inscrire;
    
    @FXML
    private VBox vbox;
    
    private Parent fxml;
    

    @FXML
    void open_SignIn() {

    	  TranslateTransition t= new TranslateTransition(Duration.seconds(1),vbox);
    	 t.setToX(vbox.getLayoutX()*(0.000005));
    	  t.play();
    	  t.setOnFinished(e ->{
    		  
    		  try {
    			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Sign_in.fxml"));
    			  vbox.getChildren().removeAll();
    			  vbox.getChildren().setAll(fxml);
    			  
    		  }
    		  catch (Exception e1) {
				// TODO: handle exception
    			  e1.printStackTrace();
			}
    	  });
    
    }

    @FXML
    void open_SignUp() {

    	  TranslateTransition t= new TranslateTransition(Duration.seconds(1),vbox);
       	  t.setToX(vbox.getLayoutX()*(-0.95));
       	  t.play();
       	t.setOnFinished(e ->{
  		  
  		  try {
  			  
  			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Sign_Up.fxml"));
  			  vbox.getChildren().removeAll();
  			  vbox.getChildren().setAll(fxml);
  			  
  		  }
  		  catch (Exception e1) {
				// TODO: handle exception
  			  e1.printStackTrace();
			}
  	  });
       	  
    }

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		
	  TranslateTransition t= new TranslateTransition(Duration.seconds(2),vbox);
   	  t.setToX(vbox.getLayoutX()*(-0.9));
   	  t.play();
  	t.setOnFinished(e ->{
		  
		  try {
			  
			  fxml=FXMLLoader.load(getClass().getResource("/interfaces/Sign_Up.fxml"));
			  vbox.getChildren().removeAll();
			  vbox.getChildren().setAll(fxml);
			  
		  }
		  catch (Exception e1) {
				// TODO: handle exception
			  e1.printStackTrace();
			}
	  });
   	  
	}

 
}
