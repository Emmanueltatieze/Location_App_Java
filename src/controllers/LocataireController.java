package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import application.ConnectionSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Locataire;

public class LocataireController implements Initializable {


	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	
    @FXML
    private TextField cni;

    @FXML
    private TextField cni_2;

    @FXML
    private DatePicker dateNaiss;

    @FXML
    private TextField nom;
    
    @FXML
    private TextField tel;


    @FXML
    private TableView<Locataire> tab_locataire;
    @FXML
    private TableColumn<Locataire,Integer> tab_cni;

    @FXML
    private TableColumn<Locataire,Date> tab_dateNaiss;

    @FXML
    private TableColumn<Locataire,Integer> tab_id;

    @FXML
    private TableColumn<Locataire,String> tab_name;

    @FXML
    private TableColumn<Locataire,String> tab_tel;

   
    
    ObservableList<Locataire> data=FXCollections.observableArrayList();

    @FXML
    void Ajouter() {
    	String name=nom.getText();
    	String cni3=cni_2.getText();
    	String tele=tel.getText();
    	
    	String sql="insert into locataire(nom_prenom,telephone,CNI,dateNaiss) values(?,?,?,?)";
    	if(!nom.equals("")&&!cni3.equals("")&&!tele.equals("")&&!dateNaiss.getValue().equals(null))
    	{
    		try {
        		st=cnx.prepareStatement(sql);
        		st.setString(1, name);
        		java.util.Date date=java.util.Date.from(dateNaiss.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        		Date sqlDate=new Date(date.getTime());
        		st.setDate(4, sqlDate);
        		st.setString(2, tele);
        		st.setString(3, cni3);
        		st.execute();
        		cni_2.setText("");
        		nom.setText("");
        		tel.setText("");
        		dateNaiss.setValue(null);
        		Alert alert =new Alert(AlertType.CONFIRMATION, " Locataire Ajouté avec succes!", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait(); 
        		showLocataire();
        		
    			
    		} catch (SQLException e) {
    			e.printStackTrace();		
    			}
    	}
    	else {
    		Alert alert =new Alert(AlertType.WARNING, "Veuillez remplir tous les champs", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    		
    	}
    	
    	

    }

    @FXML
    void Modiffier() {

    	String name=nom.getText();
    	String cni3=cni_2.getText();
    	String tele=tel.getText();
    	
    	String sql="update locataire set nom_prenom=?,telephone=?,CNI=?,dateNaiss=? where CNI='"+cni.getText()+"' ";
    	if(!nom.equals("")&&!cni3.equals("")&&!tele.equals("")&&!dateNaiss.getValue().equals(null))
    	{
    		try {
        		st=cnx.prepareStatement(sql);
        		st.setString(1, name);
        		java.util.Date date=java.util.Date.from(dateNaiss.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        		Date sqlDate=new Date(date.getTime());
        		st.setDate(4, sqlDate);
        		st.setString(2, tele);
        		st.setString(3, cni3);
        		st.executeUpdate();
        		cni_2.setText("");
        		nom.setText("");
        		tel.setText("");
        		dateNaiss.setValue(null);
        		Alert alert =new Alert(AlertType.CONFIRMATION, " Locataire Modifié avec succes!", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait(); 
        		showLocataire();
        		
    			
    		} catch (SQLException e) {
    			e.printStackTrace();		
    			}
        	
    	}
    	else {
    		Alert alert =new Alert(AlertType.WARNING, "Veuillez remplir tous les champs", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    		
    	}
		
    }

    @FXML
    void Rechercher() {

    	String sql="select * from locataire where CNI='"+cni.getText()+"'";
    	int m=0;
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				cni_2.setText(rsl.getString("CNI"));
				nom.setText(rsl.getString("nom_prenom"));
				tel.setText(rsl.getString("telephone"));
				Date date=rsl.getDate("dateNaiss");
				dateNaiss.setValue(date.toLocalDate());
				m=1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			}
    	if(m==0)
    	{
    		cni_2.setText("");
    		nom.setText("");
    		tel.setText("");
    		dateNaiss.setValue(null);
    		Alert alert =new Alert(AlertType.ERROR, "Aucun Locataire trouvé avec cette CNI", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();    	
    	}
    	
    }

    @FXML
    void Supprimer() {
    	//String sql="select id_Location from location loc inner join logement log on log.id_logement=loc.logement   where locataire='"+cni_2.getText()+"' ";

    	String sql="delete from location where locataire=(select id_Locataire from locataire where locataire.nom_prenom='"+nom.getText()+"') ";
    	 try {
    			st=cnx.prepareStatement(sql);
    			st.executeUpdate();
    	 }
    	 catch (SQLException e) {
    			e.printStackTrace();		
    			}
    String sql1="delete from locataire where CNI='"+cni_2.getText()+"' ";
    try {
		st=cnx.prepareStatement(sql1);
		st.executeUpdate();
		cni_2.setText("");
		nom.setText("");
		cni.setText("");
		tel.setText("");
		dateNaiss.setValue(null);
		Alert alert =new Alert(AlertType.CONFIRMATION, " Locataire Supprimé avec succes!", javafx.scene.control.ButtonType.OK);
		alert.showAndWait(); 
		showLocataire();
		
		
	} catch (SQLException e) {
		e.printStackTrace();		
		}


    }
    public void showLocataire()
    {
    	tab_locataire.getItems().clear();
    	String sql="select * from Locataire";
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				data.add(new Locataire(rsl.getInt("id_Locataire"), rsl.getString("nom_prenom"), rsl.getDate("dateNaiss"), rsl.getString("telephone"), rsl.getInt("CNI")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();		}
    	tab_dateNaiss.setCellValueFactory(new PropertyValueFactory<Locataire,Date>("dateNaiss"));
    	tab_id.setCellValueFactory(new PropertyValueFactory<Locataire,Integer>("id"));
    	tab_tel.setCellValueFactory(new PropertyValueFactory<Locataire,String>("tel"));
    	tab_name.setCellValueFactory(new PropertyValueFactory<Locataire,String>("nom_prenom"));
    	tab_cni.setCellValueFactory(new PropertyValueFactory<Locataire,Integer>("cni"));
    	tab_locataire.setItems(data);

    }
    

    @FXML
    void TableLocataireEvent() {
    	Locataire locataire=tab_locataire.getSelectionModel().getSelectedItem();
    	String sql="select * from Locataire where id_Locataire=?";
    	try {
    		st=cnx.prepareStatement(sql);
    		st.setInt(1, locataire.getId());
			rsl=st.executeQuery();
			if(rsl.next())
			{
				cni_2.setText(rsl.getString("CNI"));
				nom.setText(rsl.getString("nom_prenom"));
				tel.setText(rsl.getString("telephone"));
				Date date=rsl.getDate("dateNaiss");
				dateNaiss.setValue(date.toLocalDate());
				cni.setText(rsl.getString("CNI"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			}


    }

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
		showLocataire();

		
   	  
	}
}
