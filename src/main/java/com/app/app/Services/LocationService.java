package com.app.app.Services;

import com.app.app.Models.Location;
import com.app.app.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location location) {
        Location existingLocation = getLocationById(id);
        existingLocation.setLocationName(location.getLocationName());
        return locationRepository.save(existingLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
