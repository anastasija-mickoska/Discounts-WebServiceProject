package com.example.demo.demo.controller;

import com.example.demo.demo.com.example.config.JwtService;
import com.example.demo.demo.model.Booking;
import com.example.demo.demo.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final JwtService jwtService;
    private final BookingService bookingService;

    @Autowired
    public BookingController(JwtService jwtService, BookingService bookingService) {
        this.jwtService = jwtService;
        this.bookingService = bookingService;
    }
    //Add booking

@PostMapping
public ResponseEntity<?> addBooking(@RequestBody BookingRequest bookingRequest) {
    try {
        System.out.println("Request received: " + bookingRequest);
        Integer userId = jwtService.extractUserIdFromToken(bookingRequest.getToken());
        Integer discountId = bookingRequest.getDiscountId();
        System.out.println("Received discountId: " + discountId);
        System.out.println("Received userId: " + userId);
        // Call service to add the booking
        Booking booking = bookingService.addBooking(userId, discountId, bookingRequest.getReservationDate());
        return ResponseEntity.ok(booking);
    } catch (IllegalArgumentException e) {
        // Return a 404 if user or discount is not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
        // Handle any other exceptions and return a 500 error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the booking.");
    }
}

//     @PostMapping
//     public ResponseEntity<Booking> addBooking(
//         @RequestParam String token,
//         @RequestParam Integer discountId,
//         @RequestParam Integer destinationId,
//         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date reservationDate) {

//     System.out.println("Received token: " + token);
//     System.out.println("Received discountId: " + discountId);
//     System.out.println("Received destinationId: " + destinationId);
//     System.out.println("Received reservationDate: " + reservationDate);

//     Integer userId = jwtService.extractUserIdFromToken(token);
//     Booking booking = bookingService.addBooking(userId, discountId, destinationId,reservationDate);

//     return ResponseEntity.ok(booking);
// }

    //Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    //Get booking by id
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    //Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    //Get user ID by username
    @GetMapping("/users")
    public ResponseEntity<Integer> getUserId(@RequestParam String username) {
        Integer userId = bookingService.getUserIdByUsername(username);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}