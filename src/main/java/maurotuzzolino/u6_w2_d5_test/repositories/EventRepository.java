package maurotuzzolino.u6_w2_d5_test.repositories;

import maurotuzzolino.u6_w2_d5_test.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
}
