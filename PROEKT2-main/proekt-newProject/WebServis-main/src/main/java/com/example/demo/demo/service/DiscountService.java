package com.example.demo.demo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.demo.demo.model.Discount;
import com.example.demo.demo.model.Booking;
import com.example.demo.demo.model.Destination;
import com.example.demo.demo.repository.DiscountRepository;
import com.example.demo.demo.repository.BookingRepository;
import com.example.demo.demo.repository.DestinationRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("regularDiscountService")
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DestinationRepository destinationRepository;
    private final BookingRepository bookingRepository;

    public DiscountService(DiscountRepository discountRepository, DestinationRepository destinationRepository,
            BookingRepository bookingRepository) {
        this.discountRepository = discountRepository;
        this.destinationRepository = destinationRepository;
        this.bookingRepository = bookingRepository;
    }

    // Add a new discount
    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    // Add a discount to a destination
    public Discount addDiscountToDestination(Integer destinationId, Discount discount) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);

        if (destinationOptional.isPresent()) {
            Destination destination = destinationOptional.get();
            discount.setDestination(destination);
            return discountRepository.save(discount);
        } else {
            throw new IllegalArgumentException("Destination with ID " + destinationId + " does not exist.");
        }
    }

    // Remove an existing discount by ID
    public void removeDiscountById(Integer discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
        } else {
            throw new IllegalArgumentException("Discount with ID " + discountId + " does not exist.");
        }
    }

    // Remove a discount from a destination
    public void removeDiscount(Integer discountId) {
        if (!discountRepository.existsById(discountId)) {
            throw new IllegalArgumentException("Discount with ID " + discountId + " does not exist.");
        }

        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("Discount with ID " + discountId + " not found."));
        List<Booking> bookings = bookingRepository.findByDiscountId(discountId);
        for (Booking booking : bookings) {
            booking.setDiscount(null);
            bookingRepository.save(booking);
        }
        if (discount.getDestination() != null) {
            Destination destination = discount.getDestination();
            destination.getDiscounts().remove(discount); 
            discount.setDestination(null); 
            destinationRepository.save(destination);
        }
        try {
            discountRepository.delete(discount);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot delete discount. It is still referenced.");
        }
    }

    // Get all discounts for a destination
    public List<Discount> getDiscountsByDestination(Integer destinationId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);

        if (destinationOptional.isPresent()) {
            return discountRepository.findByDestination(destinationOptional.get());
        } else {
            throw new IllegalArgumentException("Destination with ID " + destinationId + " does not exist.");
        }
    }

    // Get all discounts
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    // Filter discount by destination name or country
    public List<Discount> filterDiscountsByDestination(String destination) {
        return discountRepository.filterByDestination(destination);
    }

    // Update discount
    public Discount updateDiscount(Integer id, Discount updatedDiscount) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Discount not found with ID: " + id));
        if (updatedDiscount.getDescription() != null) {
            discount.setDescription(updatedDiscount.getDescription());
        }
        if (updatedDiscount.getDiscountValue() != 0) {
            discount.setDiscountValue(updatedDiscount.getDiscountValue());
        }
        if (updatedDiscount.getDiscountType() != null) {
            discount.setDiscountType(updatedDiscount.getDiscountType());
        }
        if (updatedDiscount.getPrice() != 0) {
            discount.setPrice(updatedDiscount.getPrice());
        }
        if (updatedDiscount.getStartDate() != null) {
            discount.setStartDate(updatedDiscount.getStartDate());
        }
        if (updatedDiscount.getEndDate() != null) {
            discount.setEndDate(updatedDiscount.getEndDate());
        }
        if (updatedDiscount.getStatus() != null) {
            discount.setStatus(updatedDiscount.getStatus());
        }
        if (updatedDiscount.getDestination() != null) {
            discount.setDestination(updatedDiscount.getDestination());
        }

        return discountRepository.save(discount);
    }

    public Discount getDiscountById(Integer discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("Discount with ID " + discountId + " not found."));
    }
}
