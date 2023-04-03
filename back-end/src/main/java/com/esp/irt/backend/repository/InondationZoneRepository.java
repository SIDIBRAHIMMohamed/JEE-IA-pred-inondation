package com.esp.irt.backend.repository;

import com.esp.irt.backend.entities.InondationZone;
import com.esp.irt.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InondationZoneRepository extends JpaRepository<InondationZone, Long> {

}
