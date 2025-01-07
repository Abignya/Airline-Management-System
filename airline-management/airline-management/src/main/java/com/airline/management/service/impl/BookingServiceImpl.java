package com.airline.management.service.impl;

import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.model.Booking;
import com.airline.management.model.Flight;
import com.airline.management.repository.BookingRepository;
import com.airline.management.repository.FlightRepository;
import com.airline.management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Booking createBooking(Booking booking) {
        Flight flight = flightRepository.findById(booking.getFlight().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + booking.getFlight().getId()));

        int bookedSeats = bookingRepository.findAll().stream()
                .filter(b -> b.getFlight().getId().equals(flight.getId()))
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        if(bookedSeats + booking.getNumberOfSeats() > flight.getCapacity()){
            throw new IllegalArgumentException("Not enough seats available for flight " + flight.getFlightNumber());
        }

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
