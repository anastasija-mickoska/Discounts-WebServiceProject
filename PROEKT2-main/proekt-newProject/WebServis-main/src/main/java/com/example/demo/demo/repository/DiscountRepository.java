package com.example.demo.demo.repository;

import com.example.demo.demo.model.Discount;
import com.example.demo.demo.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    List<Discount> findByDestination(Destination destination);
      @Query("SELECT d FROM ModelDiscount d " +
       "WHERE (LOWER(d.destination.name) LIKE LOWER(CONCAT('%', :destination, '%')) OR d.destination.name IS NULL) " +
       "OR (LOWER(d.destination.country) LIKE LOWER(CONCAT('%', :destination, '%')) OR d.destination.country IS NULL)")
    List<Discount> filterByDestination(String destination);
    Optional<Discount> findById(Integer id);

}
