package com.esp.irt.backend.repository;

import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.entities.User;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface InondationZoneRepository extends JpaRepository<InondationZone, Long> {
    @Query("SELECT i FROM InondationZone i WHERE i.date = (SELECT MAX(i2.date) FROM InondationZone i2)")
    List<InondationZone> findByLastInsertedDate();

    @Query("SELECT i FROM InondationZone i WHERE i.date = :filterDate GROUP BY i.date, i.id")
    List<InondationZone> findByDate(@Param("filterDate") LocalDate filterDate);

    Optional<InondationZone>  findByVille(String ville);
}
