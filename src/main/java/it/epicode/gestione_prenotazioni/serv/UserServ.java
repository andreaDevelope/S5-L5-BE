package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.User;
import it.epicode.gestione_prenotazioni.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServ {

    @Autowired
    UserRepo ur;

    public void save(User u){
        ur.save(u);
    }

    public List<User> findAll(){
        return ur.findAll();
    }

    public User findByUsername(String username) {
        return ur.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username + " non trovato"));
    }

}
