package it.epicode.gestione_prenotazioni.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isAvailable;

    private LocalDate day;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Station> stations;


    public Reservation(User user, List<Station> stations) {
        this.user = user;
        this.stations = stations;
        this.isAvailable = true;
    }

    public Reservation() {
    }
}
