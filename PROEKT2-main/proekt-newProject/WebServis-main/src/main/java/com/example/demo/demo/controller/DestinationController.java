package com.example.demo.demo.controller;

import com.example.demo.demo.model.Destination;
import com.example.demo.demo.model.Discount;
import com.example.demo.demo.service.DestinationService;
import com.example.demo.demo.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private DiscountService discountService;

    // Add a new destination
    @PostMapping
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        return ResponseEntity.ok(destinationService.saveDestination(destination));
    }

    // Get all destinations
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    // Add a discount to a destination
    @PostMapping("/{destinationId}/discounts")
    public ResponseEntity<Discount> addDiscountToDestination(
            @PathVariable Integer destinationId,
            @RequestBody Discount discount
    ) {
        return ResponseEntity.ok(discountService.addDiscountToDestination(destinationId, discount));
    }

    // Remove a discount from a destination
    @DeleteMapping("/discounts/{discountId}")
    public ResponseEntity<String> removeDiscount(@PathVariable Integer discountId) {
        discountService.removeDiscount(discountId);
        return ResponseEntity.ok("Discount removed successfully");
    }

    // Get all discounts for a destination
    @GetMapping("/{destinationId}/discounts")
    public ResponseEntity<List<Discount>> getDiscountsByDestination(@PathVariable Integer destinationId) {
        return ResponseEntity.ok(discountService.getDiscountsByDestination(destinationId));
    }

    //Filter discounts by destination name or destination country
    @GetMapping("/discounts/filter")
    public List<Discount> filterDiscounts(@RequestParam String destination) {
        return discountService.filterDiscountsByDestination(destination);
    }

    @GetMapping("/discounts/{discountId}")
    public Discount getDiscountById(@PathVariable Integer discountId ) {
        return discountService.getDiscountById(discountId);
    }
    
    //Update discount
    @PutMapping("discounts/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Integer id, @RequestBody Discount updatedDiscount) {
        try {
            Discount discount = discountService.updateDiscount(id, updatedDiscount);
            return ResponseEntity.ok(discount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
