package controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import application.ConnectionSQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LocationController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	

	 @FXML
	 private DatePicker DateNaiss;

    @FXML
    private DatePicker Datedebut;

    @FXML
    private DatePicker Datefin;


    @FXML
    private TextField identifiant;
    
    @FXML
    private TextField adresse;

    @FXML
    private TextField cni;

    @FXML
    private TextField cni_2;

    @FXML
    private ImageView img_log;

    @FXML
    private TextField loyer;

    @FXML
    private TextField nom_prenom;

    @FXML
    private TextField periode;

    @FXML
    private TextField region;

    @FXML
    private TextField telephone;

    @FXML
    private TextField type;

    
    @FXML
    void Trouver() {

    	String sql2="select loyer,superficie,nomRegion,image,addr_L,id_logement,nomType from logement l inner join region r on l.region=r.idRegion inner join type t on t.idType=l.type  where id_logement='"+identifiant.getText()+"' and id_logement not in (select logement from location)";

    	int loy=0;
    	int superf=0;
    	byte byteImg[];
    	Blob blob;
    	int m=0;
    	try {
    		

			st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next()) {
				loy=rsl.getInt("l.loyer");
				loyer.setText(Integer.toString(loy)+" $");
				superf=rsl.getInt("l.superficie");
				type.setText(rsl.getString("t.nomType"));
				region.setText(rsl.getString("r.nomRegion"));
				adresse.setText(rsl.getString("l.addr_L"));
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_log.getFitWidth(), img_log.getFitHeight(), true, true);
				img_log.setImage(img);
				m++;
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(m==0)
    	{
    		adresse.setText("");
    		type.setText("");
    		region.setText("");
    		loyer.setText("");
    		region.setText("");
    		Alert alert =new Alert(AlertType.ERROR, "Ce logement est deja pris ou n'existe pas", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();    	
    	}
    

    }
    
    @FXML
    void Ajouter() {
    	int nbIdLoc=0;
    	String name=nom_prenom.getText();
    	String cni3=cni_2.getText();
    	String tele=telephone.getText();
    	
    	

    	

    	if(!name.equals("")&&!cni3.equals("")&&!tele.equals("")&&!Datefin.getValue().equals(null)&&!Datedebut.getValue().equals(null))
    	{
    		String sql2="insert into locataire(nom_prenom,telephone,CNI,dateNaiss) values(?,?,?,?)";
		try {
    		st=cnx.prepareStatement(sql2);
    		java.util.Date date3=java.util.Date.from(DateNaiss.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    		Date sqlDate3=new Date(date3.getTime());
    		st.setString(1, name);
    		st.setString(3, cni3);
    		st.setString(2, tele);
    		st.setDate(4, sqlDate3);
    		st.execute();
    		String sql5="select id_Locataire from locataire where CNI='"+cni_2.getText()+"'";
    		st=cnx.prepareStatement(sql5);
			rsl=st.executeQuery();
			if(rsl.next()) {
				String idLoc=rsl.getString("id_Locataire");
				 nbIdLoc=Integer.parseInt(idLoc);
				
				
			}


//    		cni_2.setText("");
//    		nom_prenom.setText("");
//    		telephone.setText("");
//    		Datedebut.setValue(null);
//    		Datefin.setValue(null);
//    		Alert alert =new Alert(AlertType.CONFIRMATION, " Location Ajouté avec succes!", javafx.scene.control.ButtonType.OK);
//    		alert.showAndWait(); 
//    		
			
		} catch (SQLException e) {
			e.printStackTrace();		
			}
        	String sql="insert into location(logement,locataire,dateDebut,dateFin) values('"+identifiant.getText()+"','"+nbIdLoc+"',?,?)";
    		try {
        		st=cnx.prepareStatement(sql);
        		java.util.Date date=java.util.Date.from(Datedebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        		java.util.Date date2=java.util.Date.from(Datefin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        		Date sqlDate=new Date(date.getTime());
        		Date sqlDate2=new Date(date2.getTime());
        		st.setDate(1, sqlDate);
        		st.setDate(2, sqlDate2);
        		st.execute();
        		Datedebut.setValue(null);
        		Datefin.setValue(null);
        		periode.setText("");
        		adresse.setText("");
        		loyer.setText("");
        		type.setText("");
        		region.setText("");
        		cni_2.setText("");
        		nom_prenom.setText("");
        		telephone.setText("");
        		Datedebut.setValue(null);
        		DateNaiss.setValue(null);
        		Datefin.setValue(null);
        		Alert alert =new Alert(AlertType.CONFIRMATION, " Location Ajouté avec succes!", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait(); 
        		
    			
    		} catch (SQLException e) {
    			e.printStackTrace();		
    			}
    		
    	}
    	else {
    		Alert alert =new Alert(AlertType.WARNING, "Veuillez remplir tous les champs", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    		
    	}
    	

    }

    

	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionSQL.connexionDB();
		

		
		
	}

}
