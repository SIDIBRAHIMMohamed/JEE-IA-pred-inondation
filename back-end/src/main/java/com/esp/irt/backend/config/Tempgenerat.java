package com.esp.irt.backend.config;

import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.repository.InondationZoneRepository;
import com.esp.irt.backend.services.Datagenerated;
import com.esp.irt.backend.services.GenarateDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Tempgenerat {
    @Autowired
    private GenarateDataService dataservice;
    
    @Autowired
    private InondationZoneRepository inondationZoneRepository;
    
    @Scheduled(fixedRate = 60000) // Exécuter toutes les 1 minute
    public void tempgenerate(){
        Datagenerated data1 = new Datagenerated();
        for(int i = 0; i <= 50; i++){
            InondationZone inondationZone = new InondationZone();
            data1 = dataservice.generateRandomData();
            inondationZone = dataservice.convertdataToiNd(data1);
            if(inondationZoneRepository.findByVille(inondationZone.getVille()).isEmpty())
                inondationZoneRepository.save(inondationZone);
        }
    }
}
