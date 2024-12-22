package it.epicode.gestione_prenotazioni.repo;

import it.epicode.gestione_prenotazioni.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
