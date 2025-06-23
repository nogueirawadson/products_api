package jwt_token.jwt.repository;

import jwt_token.jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
