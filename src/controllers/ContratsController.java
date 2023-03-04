package controllers;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.ConnectionSQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ContratsController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet rsl;

    @FXML
    private ImageView img_log;
    @FXML
    private TextField Datedebut;

    @FXML
    private TextField Datefin;

    @FXML
    private TextField adresse;

    @FXML
    private TextField forfaitaire;

    @FXML
    private TextField id_loc;

    @FXML
    private TextField locataire;

    @FXML
    private TextField logement;

    @FXML
    private TextField loyer;

    @FXML
    private TextField montant;

    @FXML
    private TextField region;

    @FXML
    private TextField type;

    @FXML
    void Trouver() {

    	String sql2="select id_Location,nom_prenom,addr_L,nomRegion,image,nomType,loyer,dateDebut,dateFin,id_logement,forfaitaire from location inner join logement on location.logement=logement.id_logement inner join locataire on locataire.id_Locataire=logement.id_logement inner join region on logement.region=region.idRegion inner join type on type.idType=logement.type  where location.id_Location='"+id_loc.getText()+"'";

    	int loy=0;
    	int superf=0;
    	byte byteImg[];
    	Blob blob;
    	int m=0;
    	try {
    		

			st=cnx.prepareStatement(sql2);
			rsl=st.executeQuery();
			if(rsl.next()) {
				locataire.setText(rsl.getString("locataire.nom_prenom"));
				logement.setText(rsl.getString("logement.id_logement"));
				region.setText(rsl.getString("region.nomRegion"));
				adresse.setText(rsl.getString("logement.addr_L"));
				type.setText(rsl.getString("type.nomType"));
				int loye=rsl.getInt("logement.loyer");
				loyer.setText(Integer.toString(loye)+" $");
				int forf=rsl.getInt("type.forfaitaire");
				forfaitaire.setText(Integer.toString(forf)+" $");
				Datedebut.setText(rsl.getString("location.dateDebut"));
				Datefin.setText(rsl.getString("location.dateFin"));
				blob=rsl.getBlob("logement.image");
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
    		locataire.setText("");
			logement.setText("");
			region.setText("");
			adresse.setText("");
			type.setText("");
			loyer.setText("");
			forfaitaire.setText("");
			Datedebut.setText("");
			Datefin.setText("");
			img_log.setImage(null);
    		Alert alert =new Alert(AlertType.ERROR, "Aucun logement ne correspond a ce numero", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();    	
    	}
    
    }
	
    @FXML
    void imprimer()  {
    	if(!montant.getText().equals(""))
    	{
    		Document doc=new Document();
    	
    	try {
    		PdfWriter.getInstance(doc, new FileOutputStream("contrat.pdf"));
    		doc.open();
    		String format="dd/mm/yyyy hh:hh";
    		
    		SimpleDateFormat formater=new SimpleDateFormat(format);
    		java.util.Date date =new java.util.Date();
    		com.itextpdf.text.Image img;
			try {
				    img = com.itextpdf.text.Image.getInstance("C:\\Users\\Emmanuel Tatieze\\Documents\\3il\\JavaFX_App_Location\\src\\images\\entete.jpg");
					img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
    		         doc.add(img);
    		} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		doc.add(new Paragraph("Entre : Emmanuel Tatieze"
    				+"\nDemeurant à : Limoges"
    				+"\n de nationnalité : Camerounaise, CNI: 11114"
    				+"\n\nD'autre part"
    				+"\n Il a été convenu et arreté ce qui suit;"
    				+"\nM. Emmanuel Tatieze (proprietaire) loue à M(Me). " +locataire.getText()+""
    						+ "à un loyé de "+loyer.getText()+"  par mois"
    						+"\net pour une durée allant de : "+Datedebut.getText()+" jusqu'a "+Datefin.getText()+""
    							+"\nUn(e) "+type.getText()+" situé (e) a : "+adresse.getText()+""
    								+"Tel que le tout se poursuit et comporte sans exception ni reserve et sans"
    									+" plus ample description, "
    			+ "et reconnaitre qu'ils sont en bon etat pour les avoir vus et visités. Les locaux présentement loués doivent etre utilisés tout en respectant les règles"
    			+ "d'habitation.Le present bail se renouvellera par accord , pour une durée équivalente, à"
    			+ "partir d'une lettre recommandée deux mois avant l'expiration de la periode en cours."
    			+"\nFait à Limoges le : "+formater.format(date)+"",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK)));
    		doc.close();
    		try {
				Desktop.getDesktop().open(new File("contrat.pdf"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();		}
    	}
    	else {
    		Alert alert =new Alert(AlertType.ERROR, "veuillez remplir les champs vides", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait(); 
    	}

    }
	
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		cnx=ConnectionSQL.connexionDB();
   	  
	}
}
