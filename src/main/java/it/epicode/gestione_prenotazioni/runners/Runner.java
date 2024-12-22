package it.epicode.gestione_prenotazioni.runners;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.enums.Type;
import it.epicode.gestione_prenotazioni.pojo.Building;
import it.epicode.gestione_prenotazioni.pojo.Reservation;
import it.epicode.gestione_prenotazioni.pojo.Station;
import it.epicode.gestione_prenotazioni.pojo.User;
import it.epicode.gestione_prenotazioni.serv.BuildingServ;
import it.epicode.gestione_prenotazioni.serv.ReservationServ;
import it.epicode.gestione_prenotazioni.serv.StationServ;
import it.epicode.gestione_prenotazioni.serv.UserServ;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class Runner implements ApplicationRunner {

    @Autowired
    BuildingServ bs;

    @Autowired
    StationServ ss;

    @Autowired
    ReservationServ rs;

    @Autowired
    UserServ us;

    Faker faker=new Faker();


    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Station> stations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Building b = new Building();
            b.setAddress(faker.address().streetAddress());
            b.setCity(faker.address().city());
            b.setName(faker.company().name());
            bs.save(b);

            Station s = new Station(b);
            s.setDescription(faker.lorem().sentence());
            s.setMaximumOccupancy(faker.number().numberBetween(10, 100));
            s.setType(Type.PRIVATE);
            stations.add(s);
            ss.save(s);

            User u = new User();
            u.setName(faker.name().firstName());
            u.setLastname(faker.name().lastName());
            u.setUsername(faker.internet().uuid());
            u.setEmail(faker.internet().emailAddress());
            us.save(u);
        }
        System.out.println("fino a qui tutto bene ");
    }
}
