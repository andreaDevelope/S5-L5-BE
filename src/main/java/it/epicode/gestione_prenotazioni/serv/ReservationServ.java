package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.Reservation;
import it.epicode.gestione_prenotazioni.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServ {
    @Autowired
    ReservationRepo rr;

    public void save(Reservation r){
        rr.save(r);
    }

}
