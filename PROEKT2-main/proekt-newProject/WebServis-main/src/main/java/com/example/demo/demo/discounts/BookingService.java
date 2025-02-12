package com.example.demo.demo.discounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.demo.repository.BookingRepository;
import com.example.demo.demo.model.Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;


@Service("soapBookingService")
public class BookingService {

        private final BookingRepository bookingRepository;
        private static final Map<Integer,com.example.demo.demo.discounts.Booking> bookings = new HashMap<>();
        private static final List<com.example.demo.demo.discounts.Booking> list = new ArrayList<>();

    @Autowired
    com.example.demo.demo.discounts.BookingService bookingService;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

        @PostConstruct
    public void initialize() {
        List<com.example.demo.demo.model.Booking> bookingList;
        bookingList = bookingService.getAllBookings();
        // usersList = restService.getAllUsers();

        for(int i = 0; i < bookingList.size(); i++){
            com.example.demo.demo.discounts.Booking booking = new com.example.demo.demo.discounts.Booking();
 
            booking.setId(bookingList.get(i).getId());
            booking.setDiscountDescription(bookingList.get(i).getDiscount().getDescription());
            booking.setReservationDate(bookingList.get(i).getReservationDate());
            booking.setUser(bookingList.get(i).getUser());
            bookings.put(booking.getId(), booking);
            list.add(booking);
        }

        // for(int i = 0; i < usersList.size(); i++){
        //     User user = new User();

        //     user.setName(usersList.get(i).getUsername());
        //     user.setId(String.valueOf(usersList.get(i).getId_user()));
        //     user.setPassword(usersList.get(i).getPassword());

        //     users.put(user.getName(), user);
        // }
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    //get bookings for a particular user

    public List<com.example.demo.demo.discounts.Booking> getBookingsByUserId(Integer userId) {
        // List<Booking> bookings = bookingRepository.findByUserIdUser(userId);
        
        // return bookings.stream()
        //         .map(booking -> new com.example.demo.demo.discounts.Booking(
        //                 booking.getId(),
        //                 booking.getDiscount().getDescription(),  // Directly mapping the field
        //                 booking.getReservationDate()))
        //         .collect(Collectors.toList());
        List<com.example.demo.demo.discounts.Booking> userBookings = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getUser().getId_user() == userId){
                userBookings.add(list.get(i));
            }
        }
        return userBookings;
    }

}
