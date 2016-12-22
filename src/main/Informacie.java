package main;

import java.util.ArrayList;

public class Informacie {
	private ArrayList<Pravidlo> pravidla;
	private ArrayList<String> fakty;
	private ArrayList<String> faktybezprem;
	public Informacie(){
	
	}
	private static Informacie instance = null;
	public static Informacie getInstance(){
		if(instance == null){
			instance = new Informacie();
			instance.setPravidla(new ArrayList<Pravidlo>());
			instance.setFakty(new ArrayList<String>());
			instance.setFaktybezprem(new ArrayList<String>());
		}
		return instance;
	}
	public ArrayList<Pravidlo> getPravidla() {
		return pravidla;
	}
	public void setPravidla(ArrayList<Pravidlo> pravidla) {
		this.pravidla = pravidla;
	}
	public ArrayList<String> getFakty() {
		return fakty;
	}
	public void setFakty(ArrayList<String> fakty) {
		this.fakty = fakty;
	}
	public ArrayList<String> getFaktybezprem() {
		return faktybezprem;
	}
	public void setFaktybezprem(ArrayList<String> faktybezprem) {
		this.faktybezprem = faktybezprem;
	}
}


