package maurotuzzolino.u6_w2_d5_test.repositories;

import maurotuzzolino.u6_w2_d5_test.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    List<Booking> findByUserId(Long userId);
}
