package com.esp.irt.backend.services;

import java.util.List;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esp.irt.backend.entities.InondationZone;

@Service
public interface InondationZoneService {
    List<InondationZone> getAllInondationZones();

    List<InondationZone> geLastInsertedInondationZone();

    List<InondationZone> getInondationZonesByDate(LocalDate date);

    Optional<InondationZone> getInondationZoneById(Long id);

    InondationZone saveInondationZone(InondationZone inondationZone);

    void deleteInondationZoneById(Long id);
}