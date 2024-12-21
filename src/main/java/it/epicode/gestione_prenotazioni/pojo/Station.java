package it.epicode.gestione_prenotazioni.pojo;

import it.epicode.gestione_prenotazioni.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private Type type;

    private Integer maximumOccupancy;

    @ManyToOne
    private Building building;

    public Station(Building building) {
        this.building = building;
    }

    public Station() {
    }
}
