package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.Station;
import it.epicode.gestione_prenotazioni.repo.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServ {
    @Autowired
    StationRepo sr;

    public void save(Station s){
        sr.save(s);
    }
}
