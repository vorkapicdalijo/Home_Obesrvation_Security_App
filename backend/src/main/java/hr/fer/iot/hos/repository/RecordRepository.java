package hr.fer.iot.hos.repository;

import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RecordRepository extends JpaRepository<Record, Long> {

    /**
     * Returns all records associated with the given user
     *
     * @param user - user to search records for
     * @return collection of records
     */
    Collection<Record> findByUser(User user);

}
