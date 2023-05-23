package com.esp.irt.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esp.irt.backend.entities.InondationZone;

@Service
public interface InondationZoneService {
    List<InondationZone> getAllInondationZones();

    List<InondationZone> geLastInsertedInondationZone();

    Optional<InondationZone> getInondationZoneById(Long id);

    InondationZone saveInondationZone(InondationZone inondationZone);

    void deleteInondationZoneById(Long id);
}