package hr.fer.iot.hos.repository;

import hr.fer.iot.hos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {

    /**
     * Returns the user from the database that has the same email as value of input
     * @param email - email of user
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Returns the user from the database that has the same username as value of input
     * @param username - username of user
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if username exsist in db
     * @param username - username of user
     * @return Boolean
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if email exsist in db
     * @param email - email of user
     * @return Boolean
     */
    Boolean existsByEmail(String email);
}
