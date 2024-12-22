package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.pojo.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    boolean existsByStation_IdAndDay(Long stationId, LocalDate day);
    boolean existsByUserUsernameAndDay(String username, LocalDate date);
}
