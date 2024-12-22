package it.epicode.gestione_prenotazioni.runners;

import it.epicode.gestione_prenotazioni.enums.Type;
import it.epicode.gestione_prenotazioni.pojo.Building;
import it.epicode.gestione_prenotazioni.pojo.Reservation;
import it.epicode.gestione_prenotazioni.pojo.Station;
import it.epicode.gestione_prenotazioni.pojo.User;
import it.epicode.gestione_prenotazioni.serv.BuildingServ;
import it.epicode.gestione_prenotazioni.serv.ReservationServ;
import it.epicode.gestione_prenotazioni.serv.StationServ;
import it.epicode.gestione_prenotazioni.serv.UserServ;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MainRunner implements CommandLineRunner {
    @Autowired
    private BuildingServ buildingServ;

    @Autowired
    private StationServ stationServ;

    @Autowired
    private UserServ userServ;

    @Autowired
    private ReservationServ reservationServ;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Menu Gestione Prenotazioni ---");
            System.out.println("1. Aggiungi un edificio");
            System.out.println("2. Aggiungi una postazione");
            System.out.println("3. Aggiungi un utente");
            System.out.println("4. Effettua una prenotazione");
            System.out.println("5. Ricerca postazioni per tipo e città");
            System.out.println("6. Visualizza tutte le prenotazioni");
            System.out.println("7. Esci");
            System.out.print("Seleziona un'opzione: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma newline

            switch (choice) {
                case 1 -> addBuilding();
                case 2 -> addStation();
                case 3 -> addUser();
                case 4 -> makeReservation();
                case 5 -> searchStations();
                case 6 -> listReservations();
                case 7 -> exit = true;
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private void addBuilding() {
        System.out.print("Inserisci il nome dell'edificio: ");
        String name = scanner.nextLine();
        System.out.print("Inserisci l'indirizzo dell'edificio: ");
        String address = scanner.nextLine();
        System.out.print("Inserisci la città dell'edificio: ");
        String city = scanner.nextLine();

        Building building = new Building();
        building.setName(name);
        building.setAddress(address);
        building.setCity(city);

        buildingServ.save(building);
        System.out.println("Edificio aggiunto con successo!");
    }

    private void addStation() {
        System.out.print("Inserisci la descrizione della postazione: ");
        String description = scanner.nextLine();
        System.out.print("Inserisci il tipo di postazione (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
        String type = scanner.nextLine().toUpperCase();
        System.out.print("Inserisci il numero massimo di occupanti: ");
        int maxOccupancy = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Inserisci l'ID dell'edificio: ");
        Long buildingId = scanner.nextLong();
        scanner.nextLine();

        Optional<Building> buildingOpt = buildingServ.findById(buildingId);
        if (buildingOpt.isEmpty()) {
            System.out.println("Edificio non trovato!");
            return;
        }

        Building b;
        if (buildingOpt.isPresent()) {
            b = buildingOpt.get();
        } else {
            throw new EntityNotFoundException("Entità non trovata con ID: " + buildingId);
        }

        Station station = new Station();
        station.setDescription(description);
        station.setType(Type.valueOf(type));
        station.setMaximumOccupancy(maxOccupancy);
        station.setBuilding(b);

        stationServ.save(station);
        System.out.println("Postazione aggiunta con successo!");
    }

    private void addUser() {
        System.out.print("Inserisci lo username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci il nome: ");
        String name = scanner.nextLine();
        System.out.print("Inserisci il cognome: ");
        String lastname = scanner.nextLine();
        System.out.print("Inserisci l'email: ");
        String email = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);

        userServ.save(user);
        System.out.println("Utente aggiunto con successo!");
    }

    private void makeReservation() {
        System.out.print("Inserisci lo username dell'utente: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci l'ID della postazione: ");
        Long stationId = scanner.nextLong();
        System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
        String dateInput = scanner.next();
        LocalDate date = LocalDate.parse(dateInput);

        if (reservationServ.hasReservationOnDate(username, date)) {
            System.out.println("L'utente ha già una prenotazione per questa data.");
            return;
        }

        Station station = stationServ.findById(stationId);
        if (station == null) {
            System.out.println("Postazione non trovata!");
            return;
        }

        User user = userServ.findByUsername(username);
        if (user == null) {
            System.out.println("Utente non trovato!");
            return;
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setStation(station);
        reservation.setDay(date);
        reservation.setAvailable(true);

        reservationServ.save(reservation);
        System.out.println("Prenotazione effettuata con successo!");
    }

    private void searchStations() {
        System.out.print("Inserisci il tipo di postazione (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
        String type = scanner.nextLine().toUpperCase();
        System.out.print("Inserisci la città: ");
        String city = scanner.nextLine();

        List<Station> stations = stationServ.findByTypeAndCity(Type.valueOf(type), city);
        if (stations.isEmpty()) {
            System.out.println("Nessuna postazione trovata.");
        } else {
            stations.forEach(station -> System.out.println(station.getDescription()));
        }
    }

    private void listReservations() {
        List<Reservation> reservations = reservationServ.findAll();
        reservations.forEach(reservation -> {
            System.out.printf("Utente: %s, Postazione: %s, Data: %s%n",
                    reservation.getUser().getUsername(),
                    reservation.getStation().getDescription(),
                    reservation.getDay());
        });
    }
}

