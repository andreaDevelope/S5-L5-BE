package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.pojo.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station, Long> {
}
