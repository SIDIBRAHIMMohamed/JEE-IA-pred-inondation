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


@Component
public class InondationCommandLineRunner implements CommandLineRunner {
  @Autowired
  private InondationZoneRepository inondationZoneRepository;
  


  @Override
  public void run(String... args) throws Exception {
	  // Load data from Excel file
      Workbook workbook = new XSSFWorkbook(new File("/home/aziz/Documents/zone d'innodation.xlsx"));
      Sheet sheet = workbook.getSheetAt(0);
      DataFormatter formatter = new DataFormatter();
      Instances data = new Instances("InondationZoneData", getAttributes(), sheet.getPhysicalNumberOfRows());
      data.setClassIndex(6);
      List<InondationZone> zones = new ArrayList<>();
      for (int i = 1; i < 46; i++) {
          Row row = sheet.getRow(i);
          if (row != null) {
//        	    double precipitation = Double.parseDouble(formatter.formatCellValue(row.getCell(0)));
//              double waterLevel = Double.parseDouble(formatter.formatCellValue(row.getCell(1)));
//              double topography = Double.parseDouble(formatter.formatCellValue(row.getCell(2)));
//              double riverCapacity = Double.parseDouble(formatter.formatCellValue(row.getCell(3)));
//              String soilType = formatter.formatCellValue(row.getCell(4));
//              String date = formatter.formatCellValue(row.getCell(5));
//              boolean isFlooded = Boolean.parseBoolean(formatter.formatCellValue(row.getCell(6)));
//              InundationZone zone = new InndationZone(precipitation, waterLevel, topography, riverCapacity, soilType, date, isFlooded);
//              zones.add(zone);
        	  Instance instance = new DenseInstance(getAttributes().size());
              for (int j = 0; j < 6; j++) {
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

      // Convert categorical variables to nominal using one-hot encoding
//      StringToNominal stringToNominal = new StringToNominal();
//      stringToNominal.setAttributeRange("2,4"); // topography, ville, date
//      stringToNominal.setInputFormat(data);
//      data = Filter.useFilter(data, stringToNominal);
//      
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
      
      Instance newObservation = new DenseInstance(getAttributes().size());
      InondationZone i = new InondationZone(null,0.8,10.0,"low",1000,"sand",new Date(), "Nktt",false);
      newObservation.setValue(0, 0.8); // precipitation
      newObservation.setValue(1, 10.0);  // waterLevel
      newObservation.setValue(2, 1.0);   // topography
      newObservation.setValue(3, 1000.0); // riverCapacity
      newObservation.setValue(4, 2.0);   // soilType
      newObservation.setValue(5, 45550.0); // date (as a string)
      newObservation.setDataset(data);
      double predictedZone = model.classifyInstance(newObservation);
      if (predictedZone > 0.5) {
//    	  i.setIsFlooded(true);
          System.out.println("The zone is flooded!");
      } else {
//    	  i.setIsFlooded(false);
          System.out.println("The zone is not flooded.");
      }
      System.out.println("predictedZone: "+predictedZone);
      inondationZoneRepository.save(i);
  }

  private static ArrayList<Attribute> getAttributes() {
	    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	    attributes.add(new Attribute("precipitation"));
	    attributes.add(new Attribute("waterLevel"));
	    attributes.add(new Attribute("topography"));
	    attributes.add(new Attribute("riverCapacity"));
	    attributes.add(new Attribute("soilType"));
	    attributes.add(new Attribute("date"));
	    attributes.add(new Attribute("isFlooded"));
	    return attributes;
	}

}


