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
            try {
                System.out.println("\n--- Menu Gestione Prenotazioni ---");
                System.out.println("1. Aggiungi un edificio");
                System.out.println("2. Aggiungi una postazione");
                System.out.println("3. Aggiungi un utente");
                System.out.println("4. Effettua una prenotazione");
                System.out.println("5. Ricerca postazioni per tipo e città");
                System.out.println("6. Visualizza tutte le prenotazioni");
                System.out.println("7. Elimina una prenotazione");
                System.out.println("8. Modifica una prenotazione");
                System.out.println("9. Esci");
                System.out.print("Seleziona un'opzione: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addBuilding();
                    case 2 -> addStation();
                    case 3 -> addUser();
                    case 4 -> makeReservation();
                    case 5 -> searchStations();
                    case 6 -> listReservations();
                    case 7 -> deleteReservation();
                    case 8 -> updateReservation();
                    case 9 -> exit = true;
                    default -> System.out.println("Opzione non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore inatteso: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addBuilding() {
        try {
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
        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta dell'edificio: " + e.getMessage());
        }
    }

    private void addStation() {
        try {
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

            Building building = buildingOpt.get();
            Station station = new Station();
            station.setDescription(description);
            station.setType(Type.valueOf(type));
            station.setMaximumOccupancy(maxOccupancy);
            station.setBuilding(building);

            stationServ.save(station);
            System.out.println("Postazione aggiunta con successo!");
        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta della postazione: " + e.getMessage());
        }
    }

    private void addUser() {
        try {
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
        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta dell'utente: " + e.getMessage());
        }
    }

    private void searchStations() {
        try {
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
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca delle postazioni: " + e.getMessage());
        }
    }

    private void listReservations() {
        try {
            List<Reservation> reservations = reservationServ.findAll();
            reservations.forEach(reservation -> {
                System.out.printf("Utente: %s, Postazione: %s, Data: %s%n",
                        reservation.getUser().getUsername(),
                        reservation.getStation().getDescription(),
                        reservation.getDay());
            });
        } catch (Exception e) {
            System.out.println("Errore durante la visualizzazione delle prenotazioni: " + e.getMessage());
        }
    }

    private void makeReservation() {
        try {
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

            if (!reservationServ.isStationAvailable(stationId, date)) {
                System.out.println("La postazione non è disponibile per la data selezionata.");
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
        } catch (Exception e) {
            System.out.println("Errore durante la creazione della prenotazione: " + e.getMessage());
        }
    }

    private void deleteReservation() {
        try {
            System.out.print("Inserisci l'ID della prenotazione da eliminare: ");
            Long reservationId = scanner.nextLong();

            reservationServ.deleteById(reservationId);
            System.out.println("Prenotazione eliminata con successo!");
        } catch (EntityNotFoundException e) {
            System.out.println("Prenotazione non trovata.");
        } catch (Exception e) {
            System.out.println("Errore durante l'eliminazione della prenotazione: " + e.getMessage());
        }
    }

    private void updateReservation() {
        try {
            System.out.print("Inserisci l'ID della prenotazione da modificare: ");
            Long reservationId = scanner.nextLong();
            scanner.nextLine();

            Optional<Reservation> reservationOpt = reservationServ.findById(reservationId);
            if (reservationOpt.isEmpty()) {
                System.out.println("Prenotazione non trovata.");
                return;
            }

            Reservation reservation = reservationOpt.get();

            System.out.print("Inserisci la nuova data della prenotazione (YYYY-MM-DD): ");
            String dateInput = scanner.next();
            LocalDate newDate = LocalDate.parse(dateInput);

            if (!reservationServ.isStationAvailable(reservation.getStation().getId(), newDate)) {
                System.out.println("La postazione non è disponibile per la nuova data selezionata.");
                return;
            }

            reservation.setDay(newDate);
            reservationServ.save(reservation);
            System.out.println("Prenotazione aggiornata con successo!");
        } catch (Exception e) {
            System.out.println("Errore durante la modifica della prenotazione: " + e.getMessage());
        }
    }
}
