package it.epicode.gestione_prenotazioni.runner;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.pojo.Building;
import it.epicode.gestione_prenotazioni.serv.BuildingServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    BuildingServ bs;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker;
        for(int i = 0; i < 10; i++){
            Building b = new Building();
            bs.save(b);

        }
        System.out.println("fino a qui tutto bene ");
    }
}
