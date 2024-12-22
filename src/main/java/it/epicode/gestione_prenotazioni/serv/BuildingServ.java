package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.Building;
import it.epicode.gestione_prenotazioni.repo.BuildingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingServ {
    @Autowired
    BuildingRepo br;

    public void save(Building b){
        br.save(b);
    }

    public Optional<Building> findById(Long id) {
        return br.findById(id);
    }
}
