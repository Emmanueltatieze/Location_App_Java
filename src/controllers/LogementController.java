package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.ConnectionSQL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Locataire;
import models.Logement;

public class LogementController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	


    @FXML
    private ImageView img_log;
	@FXML
    private TextField adresse;

	 @FXML
	    private ComboBox<String> cb_commune;

	    @FXML
	    private ComboBox<String> cb_departement;

	    @FXML
	    private ComboBox<String> cb_region;

	    @FXML
	    private ComboBox<String> cb_type;

    @FXML
    private TextField identifiant;

    @FXML
    private TextField loyer;

    @FXML
    private Label selection;

    @FXML
    private TextField superficie;

    @FXML
    private TableColumn<Logement, String> tab_adr;

    @FXML
    private TableColumn<Logement, Integer> tab_id;

    @FXML
    private TableView<Logement> tab_logement;

    @FXML
    private TableColumn<Logement, Integer> tab_loyer;

    @FXML
    private TableColumn<Logement, String> tab_region;

    @FXML
    private TableColumn<Logement, Integer> tab_superf;

    @FXML
    private TableColumn<Logement, Integer> tab_type;

    private FileInputStream fis;
    
    @FXML
    void Ajouter() {
    	String adr=adresse.getText();
    	String superf=superficie.getText();
    	int superfic=Integer.parseInt(superf);
    	String loy=superficie.getText();
    	int loye=Integer.parseInt(loy);
    	
    	String typ=cb_type.getValue();
    	String sql1="select idType from type where nomType='"+typ+"'";
    	int Typ=0;
    	try {
    		st=cnx.prepareStatement(sql1);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Typ=rsl.getInt("idType");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_TYP");
			e.printStackTrace();	
			

			}
    	
    	String reg=cb_region.getValue();
    	String sql2="select idRegion from region where nomRegion='"+reg+"'";
    	int Regi=0;
    	try {
    		st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Regi=rsl.getInt("idRegion");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_REG");
			e.printStackTrace();
			

			}
    	
    	String prov=cb_departement.getValue();
    	String sql3="select idProvince from province where nomProvince='"+prov+"'";
    	int Provi=0;
    	try {
    		st=cnx.prepareStatement(sql3);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Provi=rsl.getInt("idProvince");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_PROV");
			e.printStackTrace();
			

			}
    	
    	String com=cb_commune.getValue();
    	String sql4="select idCommune from commune where nomCommune='"+com+"'";
    	int Commu=0;
    	try {
    		st=cnx.prepareStatement(sql4);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Commu=rsl.getInt("idCommune");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_COM");
			e.printStackTrace();
			

			}
    	
    	File image=new File(selection.getText());
    	
    	String sql="insert into logement(type,region,province,commune,superficie,addr_L,loyer,image) values(?,?,?,?,?,?,?,?)";
    	if(!adresse.equals("adresse")&&!superficie.equals("superficie")&&cb_region.getValue()!="region"&&cb_commune.getValue()!="commune"&&cb_departement.getValue()!="departement"&&cb_type.getValue()!="type")
    	{
    		try {
        		st=cnx.prepareStatement(sql);
        		st.setString(6, adr);
        		st.setInt(7, loye);
        		st.setInt(1, Typ);
        		st.setInt(5, superfic);
        		st.setInt(2, Regi);
        		st.setInt(4, Commu);
        		st.setInt(3, Provi);
        		try {
					fis=new FileInputStream(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR_IMG");
					e.printStackTrace();
	    			

				}
        		st.setBinaryStream(8, fis, image.length());
        		st.executeUpdate();
        		
        		showLogement();
        		adresse.setText("");
        		superficie.setText("");
        		identifiant.setText(""); 
        		loyer.setText(""); 
        		cb_type.setValue("type");
				cb_region.setValue("region");
				cb_departement.setValue("departement");
				cb_commune.setValue("commune");
				img_log=null;
				Alert alert =new Alert(AlertType.CONFIRMATION, "Nouveau Logement Ajouté avec succes!", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait(); 
        		
        		
    			
    		} catch (SQLException e) {
    			System.out.println("ERROR2");
    			e.printStackTrace();	
    			
    			}
    	}
    	else {
    		Alert alert =new Alert(AlertType.WARNING, "Veuillez remplir tous les champs", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    		
    	}
    	
    	
    	
    	

    }

    @FXML
    void Telecharger() {
    	FileChooser fc=new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg"));
    	File f=fc.showOpenDialog(null);
    	if(f!=null)
    	{
    		selection.setText(f.getAbsolutePath());
			Image img=new Image(f.toURI().toString(), img_log.getFitWidth(), img_log.getFitHeight(), true, true);
			img_log.setImage(img);
    		
    	}

    }

    
    
    
    
    @FXML
    void Trouver() {

    	String sql="select * from logement where id_logement='"+identifiant.getText()+"'";
    	int m=0;
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			
			if(rsl.next())
			{
				adresse.setText(rsl.getString("addr_L"));
				superficie.setText(rsl.getString("superficie"));
				loyer.setText(rsl.getString("loyer"));
				m++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			}
    	byte byteImg[];
    	Blob blob;
    	String sql2="select id_logement,addr_L,superficie,loyer,nomType,nomRegion,nomProvince,nomCommune,image,type  from logement l inner join region r on l.region=r.idRegion inner join province p on p.idProvince=l.province inner join commune c on c.idCommune=l.commune inner join type t on l.type=t.idType  where id_logement='"+identifiant.getText()+"'";
    	try {
    		st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				int id=rsl.getInt("id_logement");
				identifiant.setText(String.valueOf(id));
				adresse.setText(rsl.getString("addr_L"));
				
				int superf=rsl.getInt("superficie");
				superficie.setText(String.valueOf(superf));
				int loy=rsl.getInt("loyer");
				loyer.setText(String.valueOf(loy));
				
				cb_type.setValue(rsl.getString("nomType"));
				cb_region.setValue(rsl.getString("nomRegion"));
				cb_departement.setValue(rsl.getString("nomProvince"));
				cb_commune.setValue(rsl.getString("nomCommune"));
				
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_log.getFitWidth(), img_log.getFitHeight(), true, true);
				img_log.setImage(img);

				
			}
			m++;
			
		} catch (SQLException e) {
			e.printStackTrace();
			}
    	
    	
    	if(m<2)
    	{
    		
    		adresse.setText("");
    		superficie.setText("");
    		loyer.setText("");
    		cb_type.setValue("type");
			cb_region.setValue("region");
    		cb_departement.setValue("departement");
			cb_commune.setValue("commune");
    		
			
			img_log.setImage(null);
    		Alert alert =new Alert(AlertType.ERROR, "Aucun logement trouvé avec cet identifiant", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();    	
    	}

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void modifier() {
    	String adr=adresse.getText();
    	String superf=superficie.getText();
    	int superfic=Integer.parseInt(superf);
    	String loy=superficie.getText();
    	int loye=Integer.parseInt(loy);
    	
    	String typ=cb_type.getValue();
    	String sql1="select idType from type where nomType='"+typ+"'";
    	int Typ=0;
    	try {
    		st=cnx.prepareStatement(sql1);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Typ=rsl.getInt("idType");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_TYP");
			e.printStackTrace();	
			

			}
    	
    	String reg=cb_region.getValue();
    	String sql2="select idRegion from region where nomRegion='"+reg+"'";
    	int Regi=0;
    	try {
    		st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Regi=rsl.getInt("idRegion");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_REG");
			e.printStackTrace();
			

			}
    	
    	String prov=cb_departement.getValue();
    	String sql3="select idProvince from province where nomProvince='"+prov+"'";
    	int Provi=0;
    	try {
    		st=cnx.prepareStatement(sql3);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Provi=rsl.getInt("idProvince");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_PROV");
			e.printStackTrace();
			

			}
    	
    	String com=cb_commune.getValue();
    	String sql4="select idCommune from commune where nomCommune='"+com+"'";
    	int Commu=0;
    	try {
    		st=cnx.prepareStatement(sql4);
			rsl=st.executeQuery();
			if(rsl.next())
			{
				Commu=rsl.getInt("idCommune");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR_COM");
			e.printStackTrace();
			

			}
    	
    	File image=new File(selection.getText());

    	String sql="update logement set type=?,region=?,province=?,commune=?,superficie=?,addr_L=?,loyer=?,image=? where id_logement='"+identifiant.getText()+"' ";
    	if(!adresse.equals("adresse")&&!superficie.equals("superficie")&&cb_region.getValue()!="region"&&cb_commune.getValue()!="commune"&&cb_departement.getValue()!="departement"&&cb_type.getValue()!="type")
    	{
    		try {
        		st=cnx.prepareStatement(sql);
        		st.setString(6, adr);
        		st.setInt(7, loye);
        		st.setInt(1, Typ);
        		st.setInt(5, superfic);
        		st.setInt(2, Regi);
        		st.setInt(4, Commu);
        		st.setInt(3, Provi);
        		try {
					fis=new FileInputStream(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR_IMG");
					e.printStackTrace();
	    			

				}
        		st.setBinaryStream(8, fis, image.length());
        		st.executeUpdate();
        		
        		showLogement();
        		adresse.setText("");
        		superficie.setText("");
        		identifiant.setText(""); 
        		loyer.setText(""); 
        		cb_type.setValue("type");
				cb_region.setValue("region");
				cb_departement.setValue("departement");
				cb_commune.setValue("commune");
				selection.setText("selection");
				
				img_log.setImage(null);
				Alert alert =new Alert(AlertType.CONFIRMATION, "Logement modifié avec succes!", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait(); 
        		
        		
    			
    		} catch (SQLException e) {
    			System.out.println("ERROR2");
    			e.printStackTrace();	
    			
    			}
    	}
    	else {
    		Alert alert =new Alert(AlertType.WARNING, "Veuillez remplir tous les champs", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    		
    	}
    	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void supprimer() {
    	String sql="delete from logement where id_logement='"+identifiant.getText()+"'";
    	String sql2="delete from location where logement='"+identifiant.getText()+"'";

    	
    	try {
    		st=cnx.prepareStatement(sql2);
			st.executeUpdate();
			st=cnx.prepareStatement(sql);
			st.executeUpdate();
			showLogement();
    		adresse.setText("");
    		superficie.setText("");
    		identifiant.setText(""); 
    		loyer.setText(""); 
    		cb_type.setValue("type");
			cb_region.setValue("region");
			cb_departement.setValue("departement");
			cb_commune.setValue("commune");
			img_log.setImage(null);
			Alert alert =new Alert(AlertType.CONFIRMATION, "Logement supprimé avec succes!", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}

    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    @FXML
    void tabLogEvent() {
    	String sql="select nomProvince from province";
    	List<String> provinces=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				provinces.add(rsl.getString("nomProvince"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_departement.setItems(FXCollections.observableArrayList(provinces));
    	
    	String sql1="select nomCommune from commune";
    	List<String> communes=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql1);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				communes.add(rsl.getString("nomCommune"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_commune.setItems(FXCollections.observableArrayList(communes));
    	
    	

    	byte byteImg[];
    	Blob blob;
    	Logement logement=tab_logement.getSelectionModel().getSelectedItem();
    	String sql2="select id_logement,addr_L,superficie,loyer,nomType,nomRegion,nomProvince,nomCommune,image,type  from logement l inner join region r on l.region=r.idRegion inner join province p on p.idProvince=l.province inner join commune c on c.idCommune=l.commune inner join type t on l.type=t.idType  where id_logement=?";
    	try {
    		st=cnx.prepareStatement(sql2);
    		st.setInt(1, logement.getId_log());
			rsl=st.executeQuery();
			while(rsl.next())
			{
				int id=rsl.getInt("id_logement");
				identifiant.setText(String.valueOf(id));
				adresse.setText(rsl.getString("addr_L"));
				
				int superf=rsl.getInt("superficie");
				superficie.setText(String.valueOf(superf));
				int loy=rsl.getInt("loyer");
				loyer.setText(String.valueOf(loy));
				
				cb_type.setValue(rsl.getString("nomType"));
				cb_region.setValue(rsl.getString("nomRegion"));
				cb_departement.setValue(rsl.getString("nomProvince"));
				cb_commune.setValue(rsl.getString("nomCommune"));
				
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_log.getFitWidth(), img_log.getFitHeight(), true, true);
				img_log.setImage(img);

				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			}



    }
    
    
    ObservableList<Logement> listLogement=FXCollections.observableArrayList();
    
    public void showLogement()
    {
    	tab_logement.getItems().clear();
    	String sql="select * from logement l inner join type t on l.type=t.idType inner join region r on l.region=r.idRegion";
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				listLogement.add(new Logement(rsl.getInt("l.id_logement"), rsl.getString("t.nomType"), rsl.getString("r.nomRegion"), rsl.getInt("l.superficie"), rsl.getString("l.addr_L"),rsl.getInt("l.loyer")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();		}
    	
    	tab_id.setCellValueFactory(new PropertyValueFactory<Logement, Integer>("id_log"));
    	tab_region.setCellValueFactory(new PropertyValueFactory<Logement, String>("region"));
    	tab_type.setCellValueFactory(new PropertyValueFactory<Logement, Integer>("type"));
    	tab_superf.setCellValueFactory(new PropertyValueFactory<Logement, Integer>("superficie"));
    	tab_adr.setCellValueFactory(new PropertyValueFactory<Logement, String>("addr"));
    	tab_loyer.setCellValueFactory(new PropertyValueFactory<Logement, Integer>("loyer"));
    	tab_logement.setItems(listLogement);

    	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    public void remplirType()
    {
    	String sql="select nomType from type";
    	List<String> types=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				types.add(rsl.getString("nomType"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_type.setItems(FXCollections.observableArrayList(types));
	
    }
    
    
    
    
    
    
    
    
    
    
    public void remplirRegion()
    {
    	String sql="select nomRegion from region";
    	List<String> regions=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				regions.add(rsl.getString("nomRegion"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_region.setItems(FXCollections.observableArrayList(regions));
	
    }
    
    
    
    
    
    
    
    
    
    @FXML
    public void remplirCommune()
    {
    	String sql="select nomCommune from commune where province=(select idProvince from province where nomprovince='"+cb_departement.getValue()+"')";
    	List<String> communes=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				communes.add(rsl.getString("nomCommune"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_commune.setItems(FXCollections.observableArrayList(communes));
	
    }
    
    
    
    
    
    
    
    
    @FXML
    public void remplirProvinces()
    {
    	String sql="select nomProvince from province where region=(select idRegion from region where nomRegion='"+cb_region.getValue()+"')";
    	List<String> provinces=new ArrayList<String>();
    	try {
    		st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			while(rsl.next())
			{
				provinces.add(rsl.getString("nomProvince"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();	
			}
    	cb_departement.setItems(FXCollections.observableArrayList(provinces));
	
    }


	@Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
		showLogement();
		remplirType();
		remplirRegion();

	}
}
