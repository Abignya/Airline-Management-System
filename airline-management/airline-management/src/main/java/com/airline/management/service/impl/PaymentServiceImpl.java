package com.airline.management.service.impl;

import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.model.Booking;
import com.airline.management.model.Payment;
import com.airline.management.repository.BookingRepository;
import com.airline.management.repository.PaymentRepository;
import com.airline.management.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Payment createPayment(Payment payment) {
        Booking booking = bookingRepository.findById(payment.getBooking().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + payment.getBooking().getId()));
        payment.setBooking(booking);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
