package com.example.demo.demo.service;

import java.util.List;
import com.example.demo.demo.repository.BookingRepository;
import com.example.demo.demo.repository.DestinationRepository;
import com.example.demo.demo.repository.DiscountRepository;
import com.example.demo.demo.repository.UserRepository;
import com.example.demo.demo.model.Booking;
import com.example.demo.demo.model.User;
import com.example.demo.demo.model.Discount;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, DiscountRepository discountRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
    }
    
    //add booking 

    public Booking addBooking(Integer userId, Integer discountId, Date reservationDate) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        System.out.println("User details: " + user);
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
                Discount discount = optionalDiscount
                    .orElseThrow(() -> new IllegalArgumentException("Discount not found for id " + discountId));
        System.out.println("Discount details:" + discount);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDiscount(discount);
        booking.setReservationDate(reservationDate);
        System.out.println("Booking before save: " + booking);
        System.out.println("Booking User ID: " + booking.getUser().getId_user());
        System.out.println("Booking User: " + booking.getUser());
        return saveBooking(booking);
    }

    //get user ID by username
    public Integer getUserIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getId_user).orElse(null);
    }
    
    @Transactional
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    //get all bookings

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    //find booking by id

    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));
    }

    //delete a booking  

    public void deleteBooking(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found with ID: " + id);
        }
        bookingRepository.deleteById(id);
    }

}
