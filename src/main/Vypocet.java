package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Vypocet {
	
	public void citajsubor(){
		try {
			Informacie informacie = Informacie.getInstance();
			StringBuilder stringbuilder = new StringBuilder();
			Scanner s = new Scanner(new FileReader("zadanie4.txt"));
			boolean pravidla = true;
			String riadok = s.nextLine();
			while(s.hasNext() && pravidla){
				//System.out.println(s.nextLine().toString());
				if(riadok.matches("Fakty:")){
					pravidla = false;
					break;
				}
				System.out.println(riadok);
				Pravidlo pravidlo = new Pravidlo();
				pravidlo.setNazov(riadok.substring(0, riadok.length()-1));
				stringbuilder.append(s.nextLine());
				stringbuilder.reverse();
				ArrayList<String> podmienky = new ArrayList<String>(); 
				ArrayList<String> podmienkybezprem = new ArrayList<String>(); 
				boolean koniecpodmienok = true;
				int i = 0;
				while(koniecpodmienok){
					int poziciapz = stringbuilder.lastIndexOf(")");
					int pozicialz = stringbuilder.lastIndexOf("(");
					if(pozicialz == -1 || poziciapz == -1){
						koniecpodmienok = false;
						break;
					}
				//	System.out.println(pozicialz +" "+ poziciapz);
					String element = stringbuilder.substring(poziciapz+1, pozicialz);
					StringBuilder temp = new StringBuilder();
					temp.append(element);
					temp.reverse();
					element = temp.toString();
					String elementbezpod = zrus_premene(element);
					podmienky.add(element);
					podmienkybezprem.add(elementbezpod);
					//System.out.println(element);
					stringbuilder.setCharAt(pozicialz, ' ');
					stringbuilder.setCharAt(poziciapz, ' ');
				}
				pravidlo.setPodmienky(podmienky);
				pravidlo.setPodmienkybezprem(podmienkybezprem);
				riadok = s.nextLine();
				boolean koniec = false;
				while( !koniec){
					if(riadok.matches("Koniec;") || riadok.matches("Fakty:")){
						System.out.println("breakujem");
						koniec = true;
						break;
					}
					if(riadok.matches("Pridaj")){
						riadok = s.nextLine();
						System.out.println("Pridavam "+riadok);
						pravidlo.setPridaj(riadok);
						//System.out.println(riadok);	
					}else
						if(riadok.matches("Vymaz")){
							riadok = s.nextLine();
							pravidlo.setVymaz(riadok);
							//System.out.println(riadok);	

						}else
							if(riadok.matches("Sprava")){
								riadok = s.nextLine();
								pravidlo.setSprava(riadok);
								//System.out.println(riadok);	
							}
					riadok = s.nextLine();				
				}
				informacie.getPravidla().add(pravidlo);
				if(!riadok.matches("Fakty:"))
				riadok = s.nextLine();
			}
			System.out.println("Pravidla: ");
			for(Pravidlo prav : informacie.getPravidla()){
				prav.vypisPravidlo(prav);
			}
			System.out.println(riadok);
			while(s.hasNext()){
				riadok = s.nextLine();
				String faktbezprem = zrus_premene(riadok);
				System.out.println(riadok);
				informacie.getFakty().add(riadok);
				informacie.getFaktybezprem().add(faktbezprem);
			}
			zistifakty();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void zistifakty(){
		Informacie informacie = Informacie.getInstance();
		boolean pridal_som_fakt = true;
		boolean uzsommal = false;
		while(pridal_som_fakt){
			pridal_som_fakt = false;
		for(int o = 0; o< informacie.getPravidla().size();o++){
		Pravidlo pravidlo = informacie.getPravidla().get(o);
		System.out.println("Pravidlo: "+pravidlo.getNazov());
		boolean plati = true;
		int uzsommalpozicia = 0;
		int dlzkapod = pravidlo.getPodmienky().size();
		int aktpod = 0;
		String podmienkabezprem = null;
		String podmienka = null;
		HashMap<String,String> mapovanie = new HashMap<String,String>();
		HashMap<String,Integer>poctymapovani = new HashMap<String,Integer>();
		ArrayList<String> predoslemap2 = new ArrayList<String>();
		ArrayList<String> predoslemap1 = new ArrayList<String>();
		ArrayList<String> faktiky = new ArrayList<String>();
		ArrayList<Integer>pocetpredoslych  = new ArrayList<Integer>();
		ArrayList<Integer>poziciafaktov = new ArrayList<Integer>();
		int pozicia_faktu = 0;
		String mapovanie1;
		String mapovanie2;
		HashMap<String,Boolean>danefakty = new HashMap<String,Boolean>();
		while(plati && aktpod < dlzkapod){
			 podmienkabezprem = pravidlo.getPodmienkybezprem().get(aktpod);
			 podmienka = pravidlo.getPodmienky().get(aktpod);
			 System.out.println("Hladana podmienka : "+podmienka);
			 int i = 0;
			 boolean naslasazhoda = false;
			 for (int j = pozicia_faktu; j<informacie.getFakty().size(); j++) {
				 String fakt = informacie.getFakty().get(j);
				// System.out.println("Terajsi fakt : "+fakt);
				 uzsommal = false;
			 if(!danefakty.containsKey(fakt)){ 
				String faktbezprem = informacie.getFaktybezprem().get(j);
				if(faktbezprem.matches(podmienkabezprem)){
					System.out.println("Zhoda");
					int pocetprem = pocet_premenych(fakt);
					mapovanie2 = null;
					if(pocetprem == 2)
						mapovanie2 = najdi_premenu(2, fakt);
					mapovanie1 = najdi_premenu(1,fakt);
					boolean testik = skontrolujmapovanie(mapovanie, podmienka, mapovanie1, mapovanie2);
					System.out.println(testik);
					if(testik){
						faktiky.add(fakt);
						poziciafaktov.add(j);
						String pismenko1 = najdi_premenu(1,podmienka);
						if(pocetprem == 2){
							pocetpredoslych.add(2);
							String pismenko2 = najdi_premenu(2,podmienka);
						//	if(!mapovanie.containsKey(pismenko2))
							mapovanie.put(pismenko2, mapovanie2);
							predoslemap2.add(pismenko2);
							if(poctymapovani.containsKey(pismenko2))
								poctymapovani.put(pismenko2, poctymapovani.get(pismenko2)+1);
							else
								poctymapovani.put(pismenko2, 1);
						}else
							pocetpredoslych.add(1);
						predoslemap1.add(pismenko1);
						if(poctymapovani.containsKey(pismenko1))
							poctymapovani.put(pismenko1, poctymapovani.get(pismenko1)+1);
						else
							poctymapovani.put(pismenko1,1);
						mapovanie.put(pismenko1, mapovanie1);
						danefakty.put(fakt, true);
						if(faktiky.size() == dlzkapod){
							if(pravidlo.getPridaj() != null){
							 System.out.println("Dokazal som to");
							String vysledok = zisticijefakttam(pravidlo.getPridaj(), mapovanie);
							boolean uztamje = false;
							for (int k = 0; k < informacie.getFakty().size(); k++) {
								if(vysledok.contains(informacie.getFakty().get(k)))
									uztamje = true;
							}
							if(uztamje == false){
								informacie.getFakty().add(vysledok);
								naslasazhoda = true;
								j= informacie.getFakty().size()+1000;
								String vysledok_bez_prem = zrus_premene(vysledok);
								informacie.getFaktybezprem().add(vysledok_bez_prem);
								pridal_som_fakt = true;
								o = 0;
							}
							else{
								naslasazhoda = true;
								//pozicia_faktu = 0;
								 //uzsommal = true;
								 //uzsommalpozicia = j+1;
							}
							}
							if(pravidlo.getSprava() != null){
								//System.out.println(pravidlo.getSprava());
							}
						}else{
							naslasazhoda = true;
							j= informacie.getFakty().size();
						}
					}
				}
			 }
				 i++;
			}
			 if(naslasazhoda == false){
				 System.out.println("Nenasiel som zhodu s "+podmienkabezprem);
				 System.out.println(podmienka);
				 if(!pocetpredoslych.isEmpty()){
					 if(pocetpredoslych.get(pocetpredoslych.size()-1) == 2){
						 if(poctymapovani.get(predoslemap2.get(predoslemap2.size()-1)) <= 1)
							 mapovanie.remove(predoslemap2.get(predoslemap2.size()-1));
				     poctymapovani.put(predoslemap2.get(predoslemap2.size()-1),poctymapovani.get(predoslemap2.get(predoslemap2.size()-1))-1);
				     predoslemap2.remove(predoslemap2.size()-1);
					 }
					 if(poctymapovani.get(predoslemap1.get(predoslemap1.size()-1)) <= 1)
						 mapovanie.remove(predoslemap1.get(predoslemap1.size()-1));
					 poctymapovani.put(predoslemap1.get(predoslemap1.size()-1),poctymapovani.get(predoslemap1.get(predoslemap1.size()-1))-1);
					 predoslemap1.remove(predoslemap1.size()-1);
					 pocetpredoslych.remove(pocetpredoslych.size()-1);
					 aktpod--;
					 danefakty.remove(faktiky.get(faktiky.size()-1));
					 faktiky.remove(faktiky.size()-1);
					// if(uzsommal)
					//	 pozicia_faktu = uzsommalpozicia;
					// else
						 pozicia_faktu = poziciafaktov.get(poziciafaktov.size()-1)+1;
					 //System.out.println("Menim poziciu faktu na "+pozicia_faktu);
					 poziciafaktov.remove(poziciafaktov.size()-1);
				if(aktpod == -1){
					// System.out.println(poctymapovani);
					//System.out.println(mapovanie);
					//System.out.println(danefakty);
					plati = false;
				}
			 }else
				 plati = false;
			 }else{
				 pozicia_faktu = 0;
				 aktpod++;
			 }
		}
		 for (int i1 = 0; i1 < predoslemap1.size(); i1++) {
				System.out.println(predoslemap1.get(i1));
			}
		//System.out.println(mapovanie);
		//System.out.println(danefakty);
	}
	}
	for (int k = 0; k < informacie.getFakty().size(); k++) {
		System.out.println(informacie.getFakty().get(k));
	}
	}
	public String zisticijefakttam(String vstup,HashMap<String,String>mapa){
		StringBuilder stringbuilder = new StringBuilder();
		boolean jozko = true;
		int j = 0;
		while(jozko){
			if(vstup.charAt(j) == ' ')
				j++;
			else
				jozko = false;
		}
		for (int i = 0+j; i < vstup.length(); i++) {
			if(vstup.charAt(i) == '?'){
				stringbuilder.append(String.valueOf(vstup.charAt(i)));
				String prem = String.valueOf(vstup.charAt(i+1));
				//System.out.println(prem);
				//System.out.println(mapa.get(prem));
				stringbuilder.append(mapa.get(prem));
				i++;
				i++;
			}
			if(i<vstup.length())
			stringbuilder.append(String.valueOf(vstup.charAt(i)));
		}
		System.out.println("Ukoncujem "+stringbuilder.toString());
		return stringbuilder.toString();
	}
	
	public boolean skontrolujmapovanie(HashMap<String,String>mapovanie,String podmienka,String prem1,String prem2){
		boolean sedi = false;
		//System.out.println("Kontrolujem mapovanie");
		//System.out.println(prem1+prem2);
		String mapovanie1 = najdi_premenu(1, podmienka);
		//System.out.println(mapovanie1+" "+prem1+" "+prem2);
		//System.out.println(mapovanie.get(mapovanie1));
		if(prem2 != null){
			String mapovanie2 = najdi_premenu(2, podmienka);
			//System.out.println(mapovanie1+mapovanie2);
			if(mapovanie.containsKey(mapovanie2)){
				if(!mapovanie.get(mapovanie2).matches(prem2))
					return false;
			}else{
				if(mapovanie.containsKey(mapovanie1))
					if(!mapovanie.get(mapovanie1).matches(prem1))
						return false;
			}
		}else{
			if(mapovanie.containsKey(mapovanie1))
				if(!mapovanie.get(mapovanie1).matches(prem1))
					return false;
		}
		return true;
	}
	public String zrus_premene(String podmienka){
		StringBuilder builder = new StringBuilder();
		boolean koniec = false;
		for(int i=0;i<podmienka.length();i++){
			if(podmienka.charAt(i) == '?'){
				boolean premena = true;
				while(premena){
					if(i+1< podmienka.length()){
					i++;
					if(podmienka.charAt(i) == ' ')
						premena = false;
					}
					else{
						premena = false;
						koniec = true;
					}
				}
			}
			if(koniec)
				break;
			//System.out.println(podmienka.charAt(i));
			builder.append(podmienka.charAt(i));
		}
		return builder.toString();
	}
	public int pocet_premenych(String podmienka){
		int pocet = 0;
		for(int i = 0 ; i<podmienka.length();i++)
			if(podmienka.charAt(i) == '?')
				pocet++;
		return pocet;
	}
	public String najdi_premenu(int poradie,String podmienka){
		StringBuilder builder = new StringBuilder();
		int aktpor = 0;
		boolean koniec = false;
		boolean premena = true;
		for(int i = 0 ; i<podmienka.length();i++){
			if(podmienka.charAt(i) == '?'){
				aktpor++;
				if(aktpor == poradie){
					premena = true;
					while(premena){
						if(i+1< podmienka.length()){
							i++;
							if(podmienka.charAt(i) == ' ')
								premena = false;
							else
								builder.append(podmienka.charAt(i));
						}
						else{
							premena = false;
							koniec = true;
						}
					}
				}
			}
		}
		//System.out.println(builder.toString());
		return builder.toString();
	}

}
 