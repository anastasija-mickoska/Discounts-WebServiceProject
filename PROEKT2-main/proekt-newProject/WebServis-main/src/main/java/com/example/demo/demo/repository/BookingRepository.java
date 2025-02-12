package com.example.demo.demo.repository;

import com.example.demo.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.user.id_user = :iduser")
    List<Booking> findByUserIdUser(@Param("iduser") Integer iduser);
    @Query("SELECT b FROM Booking b WHERE b.discount.id = :discountId")
    List<Booking> findByDiscountId(@Param("discountId") Integer discountId);
}
