package main;

import java.util.ArrayList;

public class Pravidlo {
	private String nazov;
	private ArrayList<String> podmienky;
	private ArrayList<String> podmienkybezprem;
	private String akcia;
	private String pridaj = null;
	private String sprava = null;
	private String vymaz = null;
	public String getNazov() {
		return nazov;
	}
	public void setNazov(String nazov) {
		this.nazov = nazov;
	}
	public ArrayList<String> getPodmienky() {
		return podmienky;
	}
	public void setPodmienky(ArrayList<String> podmienky) {
		this.podmienky = podmienky;
	}
	public String getAkcia() {
		return akcia;
	}
	public void setAkcia(String akcia) {
		this.akcia = akcia;
	}
	public String getPridaj() {
		return pridaj;
	}
	public void setPridaj(String pridaj) {
		this.pridaj = pridaj;
	}
	public String getSprava() {
		return sprava;
	}
	public void setSprava(String sprava) {
		this.sprava = sprava;
	}
	public String getVymaz() {
		return vymaz;
	}
	public void setVymaz(String vymaz) {
		this.vymaz = vymaz;
	}
	
	public void vypisPravidlo(Pravidlo pravidlo){
		System.out.println("Pravidlo: "+pravidlo.getNazov());
		System.out.println("Podmienky:");
		for (String podd : pravidlo.getPodmienky()) {
			System.out.println(podd);
		}
		System.out.println("Akcie:");
		if(pravidlo.getPridaj() != null)
		System.out.println(pravidlo.getPridaj());
		if(pravidlo.getVymaz() != null)
			System.out.println(pravidlo.getVymaz());
		if(pravidlo.getSprava() != null)
			System.out.println(pravidlo.getSprava());
	}
	public ArrayList<String> getPodmienkybezprem() {
		return podmienkybezprem;
	}
	public void setPodmienkybezprem(ArrayList<String> podmienkybezprem) {
		this.podmienkybezprem = podmienkybezprem;
	}
}
