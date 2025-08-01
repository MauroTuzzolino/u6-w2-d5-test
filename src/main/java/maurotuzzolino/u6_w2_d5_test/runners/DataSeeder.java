package maurotuzzolino.u6_w2_d5_test.runners;

import maurotuzzolino.u6_w2_d5_test.entities.Booking;
import maurotuzzolino.u6_w2_d5_test.entities.Event;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.enums.Role;
import maurotuzzolino.u6_w2_d5_test.repositories.BookingRepository;
import maurotuzzolino.u6_w2_d5_test.repositories.EventRepository;
import maurotuzzolino.u6_w2_d5_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

//@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Crea utenti
        User user1 = new User();
        user1.setEmail("utente1@email.com");
        user1.setPassword(passwordEncoder.encode("password123"));
        user1.setRole(Role.USER);

        User organizer1 = new User();
        organizer1.setEmail("organizzatore1@email.com");
        organizer1.setPassword(passwordEncoder.encode("password345"));
        organizer1.setRole(Role.ORGANIZER);

        userRepository.saveAll(List.of(user1, organizer1));

        // Crea eventi
        Event event1 = new Event();
        event1.setTitle("Spring Boot Workshop");
        event1.setDescription("Un workshop pratico su Spring Boot");
        event1.setDate(LocalDateTime.now().plusDays(10));
        event1.setLocation("Bologna");
        event1.setTotalSeats(30);
        event1.setAvailableSeats(30);
        event1.setCreatedBy(organizer1);

        Event event2 = new Event();
        event2.setTitle("Corso PostgreSQL Avanzato");
        event2.setDescription("Approfondimento su query e indexing");
        event2.setDate(LocalDateTime.now().plusDays(20));
        event2.setLocation("Milano");
        event2.setTotalSeats(50);
        event2.setAvailableSeats(50);
        event2.setCreatedBy(organizer1);

        eventRepository.saveAll(List.of(event1, event2));

        // Prenotazione d'esempio
        Booking booking = new Booking();
        booking.setUser(user1);
        booking.setEvent(event1);
        booking.setBookingDate(LocalDateTime.now());

        bookingRepository.save(booking);

        // Aggiorna posti disponibili
        event1.setAvailableSeats(event1.getAvailableSeats() - 1);
        eventRepository.save(event1);

        System.out.println("Seeder eseguito con successo");
    }
}
