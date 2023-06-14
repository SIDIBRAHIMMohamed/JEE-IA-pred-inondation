package com.esp.irt.backend.controller;


import java.util.*;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.repository.InondationZoneRepository;
import com.esp.irt.backend.services.InondationZoneService;
import com.esp.irt.backend.services.ModelService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ExcelUploadController {
    
    private ModelService modelService;

    public ExcelUploadController() {
        try {
            modelService = new ModelService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

       
    @PostMapping("/predict")
    public ResponseEntity<String> uploadEtudiantsData(@RequestBody List<Map<String, String>> data) throws IOException {
        if (data != null && !data.isEmpty()) {
            // Date date = new Date();
            // LocalDate date = LocalDate.now();
            List<InondationZone> inondationZones = new ArrayList<>();
            for (Map<String, String> row : data) {
                Instance pred = new DenseInstance(getAttributes().size());
                double excelDateValue = Double.parseDouble(row.get("Date"));
                Date dateValue = DateUtil.getJavaDate(excelDateValue);
                Instant instant = dateValue.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate date = instant.atZone(zoneId).toLocalDate();
                InondationZone i = new InondationZone(
                    null,
                    Double.parseDouble(row.get("precipitation")),
                    Double.parseDouble(row.get("waterLevel")),
                    row.get("topography"),
                    Integer.parseInt(row.get("riverCapacity")),
                    row.get("soilType"),
                    date,
                    row.get("ville"),
                    0.0
                );
                pred.setValue(0, i.getPrecipitation()); // precipitation
                pred.setValue(1, i.getWaterLevel());  // waterLevel
                pred.setValue(2, getTopography(i.getTopography()));   // topography
                pred.setValue(3, i.getRiverCapacity()); // riverCapacity
                pred.setValue(4, getSoilType(i.getSoilType()));   // topography
                // pred.setDataset(data);                
                double predictedZone = modelService.predict(pred);
                i.setFlooded(Double.parseDouble(String.format("%.1f", Math.max(0, Math.min(1, predictedZone)))));
                
                inondationZones.add(i);
            }
            inondationZoneRepository.saveAll(inondationZones);
            return ResponseEntity.ok().body("{\"message\": \"Prediction successfull\"}");
        } else {
            return ResponseEntity.badRequest().body("Failed to predict");
        }
    }

    private  ArrayList<Attribute> getAttributes() {
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
