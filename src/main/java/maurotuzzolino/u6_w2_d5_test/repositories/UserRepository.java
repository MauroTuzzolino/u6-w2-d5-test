package maurotuzzolino.u6_w2_d5_test.repositories;

import maurotuzzolino.u6_w2_d5_test.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}