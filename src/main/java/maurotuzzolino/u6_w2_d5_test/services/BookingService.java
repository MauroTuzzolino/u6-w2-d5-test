package maurotuzzolino.u6_w2_d5_test.services;

import jakarta.persistence.EntityNotFoundException;
import maurotuzzolino.u6_w2_d5_test.entities.Booking;
import maurotuzzolino.u6_w2_d5_test.entities.Event;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.repositories.BookingRepository;
import maurotuzzolino.u6_w2_d5_test.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    public Booking book(Long eventId, User user) {
        if (bookingRepository.existsByUserIdAndEventId(user.getId(), eventId)) {
            throw new IllegalStateException("Hai già prenotato questo evento");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException("Posti esauriti");
        }

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    public List<Booking> getByUser(User user) {
        return bookingRepository.findByUserId(user.getId());
    }
}
