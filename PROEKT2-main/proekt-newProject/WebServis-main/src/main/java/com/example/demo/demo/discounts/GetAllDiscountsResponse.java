package com.example.demo.demo.discounts;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetAllDiscountsResponse")
public class GetAllDiscountsResponse {

    private List<com.example.demo.demo.discounts.Discount> discounts;

    @XmlElement(name = "discounts")
    public List<com.example.demo.demo.discounts.Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<com.example.demo.demo.discounts.Discount> discounts) {
        this.discounts = discounts;
    }
}

