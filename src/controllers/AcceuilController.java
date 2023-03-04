package controllers;


import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionSQL;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AcceuilController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;
	
	

	
    @FXML
    private TextField adr;
    @FXML
    private ImageView img_loc;

    @FXML
    private Label lab_nbr;

	
    @FXML
    private TextField loyer;

    @FXML
    private Button precedent;

    @FXML
    private TextField region;

    @FXML
    private Button suivant;

    @FXML
    private TextField superficie;
	private int position;

    @FXML
    void onPrecedent() {

    	String sql2="select loyer,superficie,nomRegion,image,addr_L,id_logement from logement l inner join region r on l.region=r.idRegion  where (id_logement < '"+position+"' and id_logement not in (select logement from location)) ORDER BY id_logement DESC ";

    	int loy=0;
    	int superf=0;
    	byte byteImg[];
    	Blob blob;
    	try {
			st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next()) {
				position=rsl.getInt("l.id_logement");
				loy=rsl.getInt("l.loyer");
				loyer.setText(Integer.toString(loy)+" $");
				superf=rsl.getInt("l.superficie");
				superficie.setText(Integer.toString(superf));
				region.setText(rsl.getString("r.nomRegion"));
				adr.setText(rsl.getString("l.addr_L"));
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_loc.getFitWidth(), img_loc.getFitHeight(), true, true);
				img_loc.setImage(img);

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

   
    @FXML
    void onSuivant() {
    	String sql2="select loyer,superficie,nomRegion,image,addr_L,id_logement from logement l inner join region r on l.region=r.idRegion  where id_logement > '"+position+"' and id_logement not in (select logement from location)";

    	int loy=0;
    	int superf=0;
    	byte byteImg[];
    	Blob blob;
    	try {
			st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next()) {
				position=rsl.getInt("l.id_logement");
				loy=rsl.getInt("l.loyer");
				loyer.setText(Integer.toString(loy)+" $");
				superf=rsl.getInt("l.superficie");
				superficie.setText(Integer.toString(superf));
				region.setText(rsl.getString("r.nomRegion"));
				adr.setText(rsl.getString("l.addr_L"));
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_loc.getFitWidth(), img_loc.getFitHeight(), true, true);
				img_loc.setImage(img);

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    
    public void showLogement() {
    	String sql="select count(*) from logement where id_logement not in (select logement from location)";
    	int i=1;
    	
    	try {
			st=cnx.prepareStatement(sql);
			rsl=st.executeQuery();
			if(rsl.next()) {
				i=rsl.getInt(i);
				
			}
			lab_nbr.setText("( "+Integer.toString(i)+" )");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql2="select loyer,superficie,nomRegion,image,addr_L,id_logement from logement l inner join region r on l.region=r.idRegion  where id_logement not in (select logement from location)";

    	int loy=0;
    	int superf=0;
    	byte byteImg[];
    	Blob blob;
    	try {
			st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next()) {
				position=rsl.getInt("l.id_logement");
				loy=rsl.getInt("l.loyer");
				loyer.setText(Integer.toString(loy)+" $");
				superf=rsl.getInt("l.superficie");
				superficie.setText(Integer.toString(superf));
				region.setText(rsl.getString("r.nomRegion"));
				adr.setText(rsl.getString("l.addr_L"));
				blob=rsl.getBlob("l.image");
				byteImg=blob.getBytes(1, (int) blob.length());
				Image img=new Image(new ByteArrayInputStream(byteImg), img_loc.getFitWidth(), img_loc.getFitHeight(), true, true);
				img_loc.setImage(img);

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    }
    
    
    @Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
		showLogement();

   	  
	}

}
