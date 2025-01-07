package com.airline.management.service.impl;

import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.model.Flight;
import com.airline.management.repository.FlightRepository;
import com.airline.management.service.FlightService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
}
