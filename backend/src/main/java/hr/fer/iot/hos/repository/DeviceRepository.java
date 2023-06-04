package hr.fer.iot.hos.repository;

import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DeviceRepository extends JpaRepository<Device, String> {

    /**
     * Returns all devices associated with the given user
     *
     * @param user - user to search records for
     * @return collection of records
     */
    Collection<Device> findByUser(User user);

    /**
     * Checks if deviceId exsists in db
     * @param deviceId- deviceID
     * @return Boolean
     */
    Boolean existsByDeviceId(String deviceId);

    /**
     * Returns device for given deviceId
     * @param deviceId - String id of device
     * @return - Device object
     */
    Device findByDeviceId(String deviceId);
}
