package maurotuzzolino.u6_w2_d5_test.services;

import jakarta.persistence.EntityNotFoundException;
import maurotuzzolino.u6_w2_d5_test.entities.Event;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.payloads.EventDTO;
import maurotuzzolino.u6_w2_d5_test.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventDTO dto, User organizer) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setTotalSeats(dto.getTotalSeats());
        event.setAvailableSeats(dto.getTotalSeats());
        event.setCreatedBy(organizer);

        return eventRepository.save(event);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findByOrganizerId(Long id) {
        return eventRepository.findByCreatedById(id);
    }

    public Event updateEvent(Event event, EventDTO dto) {
        int postiVecchi = event.getTotalSeats();
        int postiNuovi = dto.getTotalSeats();

        if (postiNuovi < (postiVecchi - event.getAvailableSeats())) {
            throw new IllegalArgumentException("Non puoi ridurre i posti totali sotto il numero di prenotazioni già effettuate.");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setTotalSeats(postiNuovi);

        int differenza = postiNuovi - postiVecchi;
        event.setAvailableSeats(event.getAvailableSeats() + differenza);

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento non trovato");
        }
        eventRepository.deleteById(id);
    }
}
