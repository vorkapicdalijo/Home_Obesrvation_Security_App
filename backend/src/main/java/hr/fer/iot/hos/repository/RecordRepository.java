package hr.fer.iot.hos.repository;

import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface RecordRepository extends JpaRepository<Record, Long> {

    /**
     * Returns all records associated with the given user
     *
     * @param user - user to search records for
     * @return collection of records
     */
    Collection<Record> findByUser(User user);

    /**
     * Returns all records associated with the device
     *
     * @param device - device object to search records for
     * @return collection of records
     */
    Collection<Record> findByDevice(Device device);

    @Modifying
    @Query("delete from Record r where r.device.deviceId=:device_id")
    void deleteRecordsByDeviceId(@Param("device_id") String deviceId);

}
