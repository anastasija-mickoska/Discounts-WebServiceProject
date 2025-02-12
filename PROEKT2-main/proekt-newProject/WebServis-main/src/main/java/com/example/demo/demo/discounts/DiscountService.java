package com.example.demo.demo.discounts;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.demo.repository.DiscountRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;


@Service("soapDiscountService")
public class DiscountService {

        private final DiscountRepository discountRepository;
        private static final Map<Integer, Discount> discounts = new HashMap<>();
        private static final List<Discount> list = new ArrayList<>();

        @Autowired
        public DiscountService(DiscountRepository discountRepository) {
            this.discountRepository = discountRepository;
        }

    @Autowired
    com.example.demo.demo.service.DiscountService discountService;

        @PostConstruct
    public void initialize() {
        List<com.example.demo.demo.model.Discount> discountList;
        discountList = discountService.getAllDiscounts();
        // usersList = restService.getAllUsers();

        for(int i = 0; i < discountList.size(); i++){
            Discount discount = new Discount();
 
            discount.setId(discountList.get(i).getDiscountId());
            discount.setDescription(discountList.get(i).getDescription());
            discount.setValue(discountList.get(i).getDiscountValue());
            discount.setStatus(discountList.get(i).getStatus().toString());
            discount.setType(discountList.get(i).getDiscountType().toString());
           // discount.setPrice(discountList.get(i).getPrice());
            discount.setStartDate(discountList.get(i).getStartDate());
            discount.setEndDate(discountList.get(i).getEndDate());
            

            discounts.put(discount.getId(), discount);
            list.add(discount);
        }

        // for(int i = 0; i < usersList.size(); i++){
        //     User user = new User();

        //     user.setName(usersList.get(i).getUsername());
        //     user.setId(String.valueOf(usersList.get(i).getId_user()));
        //     user.setPassword(usersList.get(i).getPassword());

        //     users.put(user.getName(), user);
        // }
    }



    public List<Discount> getDiscountsByStatusAndType(String status, String type) {
        // Status statusEnum = Status.valueOf(status);  // Convert string to Status enum
        // DiscountType typeEnum = DiscountType.valueOf(type);  // Convert string to DiscountType enum
        List<Discount> filteredDiscounts = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getStatus().equals(status)  &&  list.get(i).getType().equals(type)  ){
                filteredDiscounts.add(list.get(i));
            }
        }
        return filteredDiscounts;
    }

    public List<Discount> getDiscountsAbove45() {
        List<Discount> filteredDiscounts = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getValue() >=45){
                filteredDiscounts.add(list.get(i));
            }
        }
        return filteredDiscounts;
    }

    public List<Discount> getDiscountsEndingIn2Days() {
        //
        List<Discount> filteredDiscounts = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            long diffInMillis= System.currentTimeMillis()- list.get(i).getEndDate().getTime();
        long daysLeft = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            if( daysLeft >=0 && daysLeft <= 2){
                filteredDiscounts.add(list.get(i));
            }
            
        }
        return filteredDiscounts;
    }

        //Get all discounts
        public List<com.example.demo.demo.discounts.Discount> getAllDiscounts() {
            return list;
        }
    
        // private com.example.demo.demo.discounts.Discount convertToSoapModel(com.example.demo.demo.model.Discount discount) {
        //     com.example.demo.demo.discounts.Discount soapDiscount = new com.example.demo.demo.discounts.Discount();
        //     soapDiscount.setId(discount.getDiscountId());
        //     soapDiscount.setDescription(discount.getDescription());
        //     soapDiscount.setValue(discount.getDiscountValue());
        //     soapDiscount.setStartDate(discount.getStartDate());
        //     soapDiscount.setEndDate(discount.getEndDate());
        //     return soapDiscount;
        // }
    
        //Get discounts sorted by discount value
        public List<com.example.demo.demo.discounts.Discount> getSortedDiscounts() {
            list.sort((d1, d2) -> Double.compare(d2.getValue(), d1.getValue()));
            return list;
        }
}
