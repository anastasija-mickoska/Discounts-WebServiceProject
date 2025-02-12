package com.example.demo.demo.controller;
import java.util.Date;
public class BookingRequest {
    private String token;
    private Integer discountId;
    private Date reservationDate;

    // Getters and setters

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
}
