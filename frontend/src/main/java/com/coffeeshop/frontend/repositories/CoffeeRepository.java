package com.coffeeshop.frontend.repositories;

import com.coffeeshop.frontend.model.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    @Query("""
            SELECT new com.coffeeshop.frontend.dto.CoffeePopularityDTO(
                c.id,
                c.name,
                c.imageUrl,
                c.price,
                COUNT(c)
            )
            FROM Coffee c
            LEFT JOIN UserCoffee uc ON uc.coffee = c
            GROUP BY c
            ORDER BY COUNT(uc) DESC
           
            """)
    Page<Coffee> findAllOrderByFavoriteCountDesc(Pageable pageable);

}
