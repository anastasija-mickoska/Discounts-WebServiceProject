package com.example.demo.demo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity(name = "ModelDiscount")
@Table(name = "Discounts")

public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="discount_id")
    private Integer discount_id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", precision = 5, scale = 2, nullable = false)
    private double discountValue;

    @Column(precision = 5, scale = 2, nullable = false)
    private double price;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.Active;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonBackReference  // Prevent infinite recursion by ignoring the "back" side
    private Destination destination;
    public Discount() {
    }
    // Constructor
    public Discount(String description, double discountValue, Status status, DiscountType discountType,Date startDate ,Date endDate) {
        this.description = description;
        this.discountValue = discountValue;
        this.status = status;
        this.discountType = discountType;
        this.startDate=startDate;
        this.endDate = endDate;
    }
    // Getters and Setters
    public Integer getDiscountId() {
        return discount_id;
    }

    public void setDiscountId(Integer id) {
        this.discount_id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public enum DiscountType {
        Travel, Food, Accommodation, Other
    }

    public enum Status {
        Active, Expired
    }
    
}
