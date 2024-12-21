package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.pojo.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
