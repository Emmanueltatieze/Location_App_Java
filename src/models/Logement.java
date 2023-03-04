package models;

public class Logement {
	int id_log;
	String type;
	String region;
	int superficie;
	String addr;
	int loyer;
	
	public Logement()
	{
		super();
	}

	public Logement(int id_log, String type, String region, int superficie, String addr, int loyer) {
		super();
		this.id_log = id_log;
		this.type = type;
		this.region = region;
		this.superficie = superficie;
		this.addr = addr;
		this.loyer = loyer;
	}

	public int getId_log() {
		return id_log;
	}

	public void setId_log(int id_log) {
		this.id_log = id_log;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getSuperficie() {
		return superficie;
	}

	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getLoyer() {
		return loyer;
	}

	public void setLoyer(int loyer) {
		this.loyer = loyer;
	}

	

}
