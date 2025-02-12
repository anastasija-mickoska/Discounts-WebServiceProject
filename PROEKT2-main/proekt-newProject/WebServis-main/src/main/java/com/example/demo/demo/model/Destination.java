package com.example.demo.demo.model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "Destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer destination_id;

    private String name;
    private String description;
    private String country;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    @JsonManagedReference  // Manage serialization of the "parent" side
    private List<Discount> discounts;

    // Getters and Setters
    public Integer getId() {
        return destination_id;
    }

    public void setId(Integer id) {
        this.destination_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }  
}
