package com.example.demo.demo.discounts;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "GetUserBookingsResponse", namespace = "http://demo.demo.example.com/discounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetUserBookingsResponse {
    @XmlElementWrapper(name = "bookings")  
    @XmlElement(name = "booking")  
    private List<Booking> bookings;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
