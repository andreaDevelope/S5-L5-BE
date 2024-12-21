package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.Building;
import it.epicode.gestione_prenotazioni.repo.BuildingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServ {
    @Autowired
    BuildingRepo br;

    public void save(Building b){
        br.save(b);
    }

}
