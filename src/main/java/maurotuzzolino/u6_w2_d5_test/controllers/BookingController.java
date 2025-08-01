package maurotuzzolino.u6_w2_d5_test.controllers;

import jakarta.validation.Valid;
import maurotuzzolino.u6_w2_d5_test.entities.Booking;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.payloads.BookingRequestDTO;
import maurotuzzolino.u6_w2_d5_test.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> book(@RequestBody @Valid BookingRequestDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.book(dto.getEventId(), user));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getMyBookings(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(bookingService.getByUser(user));
    }
}
