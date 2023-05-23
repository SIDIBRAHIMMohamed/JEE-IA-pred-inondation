package com.esp.irt.backend.controller;

import java.util.List;
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

import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.services.InondationZoneService;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/inondationZones")
public class InondationZoneController {
	private InondationZoneService inondationZoneService;

	@Autowired
    public void InondationZoneController(InondationZoneService inondationZoneService) {
        this.inondationZoneService = inondationZoneService;
    }

    @GetMapping
    public List<InondationZone> getAllInondationZones() {
        return inondationZoneService.getAllInondationZones();
    }

    @GetMapping("/{id}")
    public Optional<InondationZone> getInondationZoneById(@PathVariable Long id) {
        return inondationZoneService.getInondationZoneById(id);
    }

    @PostMapping
    public InondationZone saveInondationZone(@RequestBody InondationZone inundationZone) {
        return inondationZoneService.saveInondationZone(inundationZone);
    }

    @PutMapping("/{id}")
    public InondationZone updateInondationZoneById(@PathVariable Long id, @RequestBody InondationZone inondationZone) {
        inondationZone.setId(id);
        return inondationZoneService.saveInondationZone(inondationZone);
    }

    @DeleteMapping("/{id}")
    public void deleteInundationZoneById(@PathVariable Long id) {
        inondationZoneService.deleteInondationZoneById(id);
    }

    @GetMapping("/lastInserted")
    public List<InondationZone> getLastInsertedInondationZone() {
        return inondationZoneService.geLastInsertedInondationZone();
    }
}
