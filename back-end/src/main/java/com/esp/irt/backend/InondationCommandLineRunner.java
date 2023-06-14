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
      Workbook workbook = new XSSFWorkbook(new File("src/main/resources/inondationZones.xlsx"));
      Sheet sheet = workbook.getSheetAt(0);
      DataFormatter formatter = new DataFormatter();
      Instances data = new Instances("InundationZoneData", getAttributes(), sheet.getPhysicalNumberOfRows());
      data.setClassIndex(5); 

      for (int i = 1; i < sheet.getPhysicalNumberOfRows() ; i++) {
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


