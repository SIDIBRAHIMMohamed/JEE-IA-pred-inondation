package com.esp.irt.backend.serviceImpl;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.repository.InondationZoneRepository;
import com.esp.irt.backend.services.InondationZoneService;
import org.springframework.format.annotation.DateTimeFormat;

@Service
public class InondationZoneServiceImpl implements InondationZoneService{

    private final InondationZoneRepository inondationZoneRepository;

    @Autowired
    public InondationZoneServiceImpl(InondationZoneRepository InondationZoneRepository) {
        this.inondationZoneRepository = InondationZoneRepository;
    }

    @Override
    public List<InondationZone> getAllInondationZones() {
        return (List<InondationZone>) inondationZoneRepository.findAll();
    }

    @Override
    public Optional<InondationZone> getInondationZoneById(Long id) {
        return inondationZoneRepository.findById(id);
    }

    @Override
    public InondationZone saveInondationZone(InondationZone InondationZone) {
        return inondationZoneRepository.save(InondationZone);
    }

    @Override
    public void deleteInondationZoneById(Long id) {
    	inondationZoneRepository.deleteById(id);
    }

    @Override
    public List<InondationZone> geLastInsertedInondationZone() {
        return (List<InondationZone>) inondationZoneRepository.findByLastInsertedDate();
    }

    @Override
    public List<InondationZone> getInondationZonesByDate(Date date) {
        return (List<InondationZone>) inondationZoneRepository.findByDate(date);
    }
}