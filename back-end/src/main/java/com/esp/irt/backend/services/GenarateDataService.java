package com.esp.irt.backend.services;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import com.esp.irt.backend.entities.InondationZone;

@Service
public class GenarateDataService {
   private Random random;	
   private ArrayList<String> villes;
   public GenarateDataService() {
       this.random = new Random();

	   initializeVilles();
   }
   private void initializeVilles() {
      villes = new ArrayList<>(Arrays.asList(
    "Akjoujt","Aleg","Amourj","Aoujeft","Arafat","Atar","Bababe","Chami","Chinguitti","Dar Naim","Djigueni","Eayoune El Atrous","El Mejrya","El Mina","F'Derick","Ghabou","Guerrou","Kaedi","Kankossa","Keur Macene","Kiffa","Koubenni","Ksar","M'Bagne","M'bout","Maghama","Magta lahjar","Mederdra","Monguel","Moughataa d'Enneama","N'beiket Lehouach","Nouadhibou","Ouad Naga","Ouadane","Oualata","Ould Yenge","R'Kiz","Riyadh","Rosso","Sebkha","Selibabi","Tamchekett","Tevragh Zein","Teyarett","Tichitt","Tijigja","Timbedgha","Tintane","Toujounine","Zoueratt,Barkeol","Basseknou","Benichab","Bir Moghrein","Boghe","Boumdeid","Boutilimitt"
));
    }
	
    public int generateRandomindex(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	public double generateRandomPrecipitation(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

    public double generateRandomwaterLevel(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

	public int generateRandomtopography(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

    public int generateRandomriverCapacity(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
    public int generateRandomSoilType(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	public String generateRandomville() {
		return "";
	}


    public int generateRandomisFlooded(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
    public Datagenerated generateRandomData() {
		double minPrecipitation = 0.0;
		double maxPrecipitation = 20.0;
		double precipitation = generateRandomPrecipitation(minPrecipitation, maxPrecipitation);
		
		double minWaterLevel = 9;
		double maxWaterLevel = 13.2;
		double waterLevel = generateRandomwaterLevel(minWaterLevel, maxWaterLevel);

		int minTopography = 1;
		int maxTopography = 3;
		int topography = generateRandomtopography(minTopography, maxTopography);

		int minRiverCapacity = 700;
		int maxRiverCapacity = 1500;
		int riverCapacity = generateRandomriverCapacity(minRiverCapacity, maxRiverCapacity);

		String ville = villes.get(generateRandomindex(0,villes.size()-1));

        int minsoiltype = 1;
		int maxsoiltype = 3;
		int soiltype =  generateRandomSoilType(minsoiltype, maxsoiltype);

		int minisFlooded = 0;
		int maxisFlooded = 1;
		int isFlooded =  generateRandomisFlooded(minisFlooded, maxisFlooded);

		Datagenerated datagenerated = new Datagenerated();
		
		datagenerated.setDate(LocalDate.now());
		datagenerated.setWaterLevel(waterLevel);
		datagenerated.setTopography(topography);
		datagenerated.setRiverCapacity(riverCapacity);
		datagenerated.setPrecipitation(precipitation);
		datagenerated.setSoilType(soiltype);
		datagenerated.setVille(ville);
		datagenerated.setIsFlooded(isFlooded);

		return datagenerated;
	}
	public InondationZone convertdataToiNd(Datagenerated data){
		InondationZone inondationzone=new InondationZone();
		inondationzone.setPrecipitation(data.getPrecipitation());
		inondationzone.setFlooded(data.getIsFlooded());
		inondationzone.setRiverCapacity(data.getRiverCapacity());
		inondationzone.setDate(data.getDate());
		inondationzone.setVille(data.getVille());
		int n=data.getTopography();
		if(n==1)
				inondationzone.setTopography("low");
		else if (n==2)
				inondationzone.setTopography("medium");
		else if(n==3)
				inondationzone.setTopography("high");
		inondationzone.setWaterLevel(data.getWaterLevel());
		int m=data.getSoilType();
		if(m==1)
				inondationzone.setSoilType("clay");
		else if(m==2)
				inondationzone.setSoilType("sand");
		else if( m==3)
				inondationzone.setSoilType("slit");
		
		return inondationzone;
		
	}


	public static Date attribuerDate(String dateString) {
		Date parsedDate = Date.valueOf(dateString);
		return parsedDate;
	}
}
