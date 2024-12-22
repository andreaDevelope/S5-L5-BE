package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.Reservation;
import it.epicode.gestione_prenotazioni.repo.ReservationRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServ {
    @Autowired
    ReservationRepo rr;

    public void save(Reservation r){
        rr.save(r);
    }

    public List<Reservation> findAll() {
        return rr.findAll();
    }

    public boolean isStationAvailable(Long stationId, LocalDate day) {
        return !rr.existsByStation_IdAndDay(stationId, day);
    }
    public boolean hasReservationOnDate(String username, LocalDate date) {
        return rr.existsByUserUsernameAndDay(username, date);
    }

    public void deleteById(Long reservationId) {
        if (!rr.existsById(reservationId)) {
            throw new EntityNotFoundException("Prenotazione non trovata con ID: " + reservationId);
        }
        rr.deleteById(reservationId);
    }

    public Optional<Reservation> findById(Long reservationId) {
        return rr.findById(reservationId);
    }

}
