package com.esp.irt.backend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.esp.irt.backend.repository.InondationZoneRepository;
import com.esp.irt.backend.entities.InondationZone;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import java.lang.*;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.SerializationHelper;

@Component
public class InondationCommandLineRunner implements CommandLineRunner {
  @Autowired
  private InondationZoneRepository inondationZoneRepository;
  
  public int getTopography(String name){
    if(name == "low") return 1;
    if(name == "high") return 2;
    else return 3;
   }
   public int getSoilType(String name){
    if(name == "clay") return 1;
    if(name == "sand") return 2;
    else return 3;
   }

  @Override
  public void run(String... args) throws Exception {
       
      

	  // Load data from Excel file
      Workbook workbook = new XSSFWorkbook(new File("/home/aziz/Documents/zone d'innodation.xlsx"));
      Sheet sheet = workbook.getSheetAt(0);
      DataFormatter formatter = new DataFormatter();
      Instances data = new Instances("InundationZoneData", getAttributes(), sheet.getPhysicalNumberOfRows());
      data.setClassIndex(5);
      List<InondationZone> zones = new ArrayList<>();
      //sheet.getPhysicalNumberOfRows()
      for (int i = 1; i < 46 ; i++) {
          Row row = sheet.getRow(i);
          if (row != null) {
        	  Instance instance = new DenseInstance(getAttributes().size());
              for (int j = 0; j < 5; j++) {
                  Cell cell = row.getCell(j);
                  if (cell != null) {
                        instance.setValue(data.attribute(j), Double.parseDouble(formatter.formatCellValue(cell)));
                  }
              }
              Cell cell = row.getCell(6);
              if (cell != null) {
                  instance.setValue(data.classAttribute(), Double.parseDouble(formatter.formatCellValue(cell)));
              }
              data.add(instance);
          }
      }
      workbook.close();      
      // Split data into training and testing sets
      data.randomize(new Random());
      int trainSize = (int) Math.round(data.numInstances() * 0.8);
      int testSize = data.numInstances() - trainSize;
      Instances trainData = new Instances(data, 0, trainSize);
      Instances testData = new Instances(data, trainSize, testSize);

      // Train a machine learning model
      LinearRegression model = new LinearRegression();
      model.buildClassifier(trainData);

      // Evaluate model on test data
      Evaluation evaluation = new Evaluation(trainData);
      evaluation.evaluateModel(model, testData);
      System.out.println("Mean absolute error: " + evaluation.meanAbsoluteError());
      System.out.println("Root mean squared error: " + evaluation.rootMeanSquaredError());
      System.out.println("R-squared: " + evaluation.correlationCoefficient());
      

      // Save the model to a file
        String filePath = "src/main/resources/model.model";
        SerializationHelper.write(filePath, model);

    //   NKTT
        Instance nktt = new DenseInstance(getAttributes().size());
        InondationZone i = new InondationZone(null,0.8,10.0,"low",1000,"sand",new Date(), "Nktt",0.0);
        nktt.setValue(0, i.getPrecipitation()); // precipitation
        nktt.setValue(1, i.getWaterLevel());  // waterLevel
        nktt.setValue(2, getTopography(i.getTopography()));   // topography
        nktt.setValue(3, i.getRiverCapacity()); // riverCapacity
        nktt.setValue(4, getSoilType(i.getSoilType()));   // topography
        // nktt.setValue(5, i.getDate().getTime()); // date (as a string)
        nktt.setDataset(data);
        double predictedZone = model.classifyInstance(nktt);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
    //  NDB
        Instance ndb = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,0.9,10.9,"low",1200,"sand",new Date(), "NDB",0.0);
        ndb.setValue(0, i.getPrecipitation()); // precipitation
        ndb.setValue(1, i.getWaterLevel());  // waterLevel
        ndb.setValue(2, getTopography(i.getTopography()));   // topography
        ndb.setValue(3, i.getRiverCapacity()); // riverCapacity
        ndb.setValue(4, getSoilType(i.getSoilType()));   // topography
        // ndb.setValue(5, i.getDate().getTime()); // date (as a string)
        ndb.setDataset(data);
        predictedZone = model.classifyInstance(ndb);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        // Zwrt
        Instance zwrt = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,0.9,10.8,"low",1400,"sand",new Date(), "Zwrt",0.0);
        zwrt.setValue(0, i.getPrecipitation()); // precipitation
        zwrt.setValue(1, i.getWaterLevel());  // waterLevel
        zwrt.setValue(2, getTopography(i.getTopography()));   // topography
        zwrt.setValue(3, i.getRiverCapacity()); // riverCapacity
        zwrt.setValue(4, getSoilType(i.getSoilType()));   // topography
        // zwrt.setValue(5, i.getDate().getTime()); // date (as a string)
        zwrt.setDataset(data);
        predictedZone = model.classifyInstance(zwrt);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        // Adrar
        Instance adrar = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,0.5,11.0,"low",800,"sand",new Date(), "Adrar",0.0);
        adrar.setValue(0, i.getPrecipitation()); // precipitation
        adrar.setValue(1, i.getWaterLevel());  // waterLevel
        adrar.setValue(2, getTopography(i.getTopography()));   // topography
        adrar.setValue(3, i.getRiverCapacity()); // riverCapacity
        adrar.setValue(4, getSoilType(i.getSoilType()));   // topography
        // adrar.setValue(5, i.getDate().getTime()); // date (as a string)
        adrar.setDataset(data);
        predictedZone = model.classifyInstance(adrar);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        //Brakna
        Instance brakna = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,0.4,12.4,"low",1300,"sand",new Date(), "Brakna",0.0);
        brakna.setValue(0, i.getPrecipitation()); // precipitation
        brakna.setValue(1, i.getWaterLevel());  // waterLevel
        brakna.setValue(2, getTopography(i.getTopography()));   // topography
        brakna.setValue(3, i.getRiverCapacity()); // riverCapacity
        brakna.setValue(4, getSoilType(i.getSoilType()));   // topography
        // brakna.setValue(5, i.getDate().getTime()); // date (as a string)
        brakna.setDataset(data);
        predictedZone = model.classifyInstance(brakna);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        //Akjoujt
        Instance akjoujt = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,1.4,10.4,"low",1100,"sand",new Date(), "Akjoujt",0.0);
        akjoujt.setValue(0, i.getPrecipitation()); // precipitation
        akjoujt.setValue(1, i.getWaterLevel());  // waterLevel
        akjoujt.setValue(2, getTopography(i.getTopography()));   // topography
        akjoujt.setValue(3, i.getRiverCapacity()); // riverCapacity
        akjoujt.setValue(4, getSoilType(i.getSoilType()));   // topography
        // akjoujt.setValue(5, i.getDate().getTime()); // date (as a string)
        akjoujt.setDataset(data);
        predictedZone = model.classifyInstance(akjoujt);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        //Kiffa
        Instance kiffa = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,1.2,12.0,"low",1200,"sand",new Date(), "Kiffa",0.0);
        kiffa.setValue(0, i.getPrecipitation()); // precipitation
        kiffa.setValue(1, i.getWaterLevel());  // waterLevel
        kiffa.setValue(2, getTopography(i.getTopography()));   // topography
        kiffa.setValue(3, i.getRiverCapacity()); // riverCapacity
        kiffa.setValue(4, getSoilType(i.getSoilType()));   // topography
        // kiffa.setValue(5, i.getDate().getTime()); // date (as a string)
        kiffa.setDataset(data);
        predictedZone = model.classifyInstance(kiffa);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);
        //Nema
        Instance nema = new DenseInstance(getAttributes().size());
        i = new InondationZone(null,0.2,11.0,"low",8000,"sand",new Date(), "Nema",0.0);
        nema.setValue(0, i.getPrecipitation()); // precipitation
        nema.setValue(1, i.getWaterLevel());  // waterLevel
        nema.setValue(2, getTopography(i.getTopography()));   // topography
        nema.setValue(3, i.getRiverCapacity()); // riverCapacity
        nema.setValue(4, getSoilType(i.getSoilType()));   // topography
        // nema.setValue(5, i.getDate().getTime()); // date (as a string)
        nema.setDataset(data);
        predictedZone = model.classifyInstance(nema);
        i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
        inondationZoneRepository.save(i);

         //Nema
         Instance nem = new DenseInstance(getAttributes().size());
         i = new InondationZone(null,0.2,11.0,"low",8000,"sand",new Date(), "Nema",0.0);
         nema.setValue(0, 0.8); // precipitation
         nema.setValue(1, 11.0);  // waterLevel
         nema.setValue(2, 3);   // topography
         nema.setValue(3, 1200); // riverCapacity
         nema.setValue(4, 3);   // soilType
        //  nema.setValue(5, 44996); // date (as a string)
         nema.setDataset(data);
         predictedZone = model.classifyInstance(nem);
         i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
         inondationZoneRepository.save(i);
  }

  private static ArrayList<Attribute> getAttributes() {
	    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	    attributes.add(new Attribute("precipitation"));
	    attributes.add(new Attribute("waterLevel"));
	    attributes.add(new Attribute("topography"));
	    attributes.add(new Attribute("riverCapacity"));
	    attributes.add(new Attribute("soilType"));
	    // attributes.add(new Attribute("date"));
	    attributes.add(new Attribute("Flooded"));
	    return attributes;
	}

}


