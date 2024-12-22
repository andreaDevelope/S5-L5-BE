package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.enums.Type;
import it.epicode.gestione_prenotazioni.pojo.Station;
import it.epicode.gestione_prenotazioni.repo.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServ {
    @Autowired
    private StationRepo stationRepo;

    public List<Station> findAll() {
        return stationRepo.findAll();
    }

    public Station save(Station station) {
        return stationRepo.save(station);
    }

    public Station findById(Long id) {
        return stationRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        stationRepo.deleteById(id);
    }

    public List<Station> findByTypeAndCity(Type type, String city) {
        return stationRepo.findByTypeAndBuildingCity(type, city);
    }
}

