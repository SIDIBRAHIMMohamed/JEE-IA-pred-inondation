package com.esp.irt.backend.repository;

import com.esp.irt.backend.entities.InondationZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InondationZoneRepository extends JpaRepository<InondationZone, Long> {
    @Query("SELECT i FROM InondationZone i GROUP BY i.date, i.id ORDER BY i.date DESC")
    List<InondationZone> findByLastInsertedDate();
}
