package com.example.demo.demo.discounts;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlRootElement(name = "GetSortedDiscountsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetSortedDiscountsResponse {

    @XmlElement(name = "discounts")
    private List<com.example.demo.demo.discounts.Discount> discounts;

    public List<com.example.demo.demo.discounts.Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<com.example.demo.demo.discounts.Discount> discounts) {
        this.discounts = discounts;
    }
}