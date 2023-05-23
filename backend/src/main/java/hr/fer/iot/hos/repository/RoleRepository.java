package hr.fer.iot.hos.repository;

import hr.fer.iot.hos.model.ERole;
import hr.fer.iot.hos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds role by role name
     * @param name - name of the role
     * @return Optional<Role>
     */
    Optional<Role> findByName(ERole name);
}
