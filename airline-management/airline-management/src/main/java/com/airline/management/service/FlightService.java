package com.airline.management.service;

import com.airline.management.model.Flight;

import java.util.List;

public interface FlightService {
    Flight addFlight(Flight flight);
    Flight getFlightById(Long id);
    List<Flight> getAllFlights();
}
