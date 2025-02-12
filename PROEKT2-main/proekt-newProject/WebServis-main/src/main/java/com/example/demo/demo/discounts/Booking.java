package com.example.demo.demo.discounts;

import java.util.Date;
import com.example.demo.demo.model.User;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Booking", namespace = "http://demo.demo.example.com/discounts", propOrder = {
    "id",
    "discountDescription",
    "reservationDate"
})

public class Booking {
    @XmlElement(name="id",required = true)
    private Integer id;
    @XmlElement(name="discountDescription",required = true)
    private String discountDescription;
    @XmlElement(name = "reservationDate")
    private Date reservationDate;
    @XmlTransient
    private User user;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    } 
}

