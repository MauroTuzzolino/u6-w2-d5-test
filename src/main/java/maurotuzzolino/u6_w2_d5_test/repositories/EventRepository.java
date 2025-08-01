package maurotuzzolino.u6_w2_d5_test.repositories;

import maurotuzzolino.u6_w2_d5_test.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreatedById(Long organizerId);
}
