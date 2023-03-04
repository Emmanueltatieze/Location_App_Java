package models;

import java.sql.Date;

public class Locataire {

	int id;
	String nom_prenom;
	Date dateNaiss;
	String tel;
	int cni;
	
	
	public Locataire()
	{
		super();
	}
	
	public Locataire(int id,String nom_prenom,Date dateNaiss,String tel,int cni)
	{
		this();
		this.cni=cni;
		this.dateNaiss=dateNaiss;
		this.id=id;
		this.nom_prenom=nom_prenom;
		this.tel=tel;
		

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom_prenom() {
		return nom_prenom;
	}

	public void setNom_prenom(String nom_prenom) {
		this.nom_prenom = nom_prenom;
	}

	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getCni() {
		return cni;
	}

	public void setCni(int cni) {
		this.cni = cni;
	}
	
	
	
}
