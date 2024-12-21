package it.epicode.gestione_prenotazioni.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String Username;

    private String name;

    private String lastname;

    private String email;

}
