package it.epicode.gestione_prenotazioni.serv;

import it.epicode.gestione_prenotazioni.pojo.User;
import it.epicode.gestione_prenotazioni.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServ {

    @Autowired
    UserRepo ur;

    public void save(User u){
        ur.save(u);
    }

}
