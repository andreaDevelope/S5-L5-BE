package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.enums.Type;
import it.epicode.gestione_prenotazioni.pojo.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepo extends JpaRepository<Station, Long> {
    List<Station> findByTypeAndBuildingCity(Type type, String city);
}
