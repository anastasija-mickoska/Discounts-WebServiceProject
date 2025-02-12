package com.example.demo.demo.service;

import com.example.demo.demo.model.Destination;
import com.example.demo.demo.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    // Save a destination
    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    // Get all destinations
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    // Find a destination by ID
    public Destination findDestinationById(Integer id) {
        return destinationRepository.findById(id).orElseThrow(() -> new RuntimeException("Destination not found"));
    }
}