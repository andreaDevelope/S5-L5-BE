package it.epicode.gestione_prenotazioni.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isAvailable;

    @Column(name = "days")
    private LocalDate day;

    @ManyToOne
    private User user;

    @ManyToOne
    private Station station;


    public Reservation(User user, Station station) {
        this.user = user;
        this.station = station;
        this.isAvailable = true;
    }

    public Reservation() {
    }
}
