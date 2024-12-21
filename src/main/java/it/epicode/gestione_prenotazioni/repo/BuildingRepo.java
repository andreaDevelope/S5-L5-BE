package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.pojo.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepo extends JpaRepository<Building, Long> {
}
