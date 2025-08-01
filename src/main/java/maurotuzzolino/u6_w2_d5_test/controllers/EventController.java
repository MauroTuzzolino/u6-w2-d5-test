package maurotuzzolino.u6_w2_d5_test.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import maurotuzzolino.u6_w2_d5_test.entities.Event;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.exceptions.UnauthorizedException;
import maurotuzzolino.u6_w2_d5_test.payloads.EventDTO;
import maurotuzzolino.u6_w2_d5_test.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Event> create(@RequestBody @Valid EventDTO dto, Authentication auth) {
        User organizer = (User) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(dto, organizer));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody @Valid EventDTO dto, Authentication auth) {
        User organizer = (User) auth.getPrincipal();
        Event event = eventService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (!event.getCreatedBy().getId().equals(organizer.getId())) {
            throw new UnauthorizedException("Puoi modificare solo i tuoi eventi.");
        }

        return ResponseEntity.ok(eventService.updateEvent(event, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        User organizer = (User) auth.getPrincipal();
        Event event = eventService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (!event.getCreatedBy().getId().equals(organizer.getId())) {
            throw new UnauthorizedException("Puoi eliminare solo i tuoi eventi.");
        }

        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getAll(@PageableDefault(size = 10, sort = "date", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(eventService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
    }
}
