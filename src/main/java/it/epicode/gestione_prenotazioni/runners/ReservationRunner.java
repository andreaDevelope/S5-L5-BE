package it.epicode.gestione_prenotazioni.runners;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.pojo.Reservation;
import it.epicode.gestione_prenotazioni.pojo.Station;
import it.epicode.gestione_prenotazioni.pojo.User;
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
import java.util.List;
import java.util.Random;

@Component
@Order(10)
public class ReservationRunner implements ApplicationRunner {

    @Autowired
    ReservationServ rs;

    @Autowired
    UserServ us;

    @Autowired
    StationServ ss;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {


        List<User> users = us.findAll();
        List<Station> stations = ss.findAll();


        if (users.isEmpty() || stations.isEmpty()) {
            System.out.println("Non ci sono utenti o stazioni salvate. Impossibile creare prenotazioni.");
            return;
        }


        for (int i = 0; i < 10; i++) {
            User randomUser = users.get(random.nextInt(users.size())); // Utente casuale
            Station randomStation = stations.get(random.nextInt(stations.size())); // Stazione casuale
            LocalDate randomDay = LocalDate.now().plusDays(faker.number().numberBetween(1, 30)); // Data casuale


            if (rs.isStationAvailable(randomStation.getId(), randomDay)) {
                Reservation r = new Reservation();
                r.setUser(randomUser);
                r.setStation(randomStation);
                r.setDay(randomDay);
                r.setAvailable(true);

                rs.save(r);
                System.out.printf("Prenotazione creata: Stazione %s, Giorno %s, Utente %s%n",
                        randomStation.getDescription(), randomDay, randomUser.getUsername());
            } else {
                System.out.printf("La stazione %s non è disponibile per il giorno %s%n",
                        randomStation.getDescription(), randomDay);
            }
        }

        System.out.println("Prenotazioni create con successo.");
    }
}
