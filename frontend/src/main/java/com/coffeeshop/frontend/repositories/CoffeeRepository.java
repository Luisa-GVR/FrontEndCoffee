package com.coffeeshop.frontend.repositories;

import com.coffeeshop.frontend.dto.CoffeePopularityDTO;
import com.coffeeshop.frontend.model.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    @Query("""
                SELECT new com.coffeeshop.frontend.dto.CoffeePopularityDTO(
                    c.id,
                    c.name,
                    c.imageUrl,
                    c.price,
                    COALESCE(COUNT(uc), 0)
                )
                FROM Coffee c
                LEFT JOIN UserCoffee uc ON uc.coffee = c
                GROUP BY c.id, c.name, c.imageUrl, c.price
                ORDER BY COALESCE(COUNT(uc), 0) DESC
            """)
    Page<CoffeePopularityDTO > findAllOrderByFavoriteCountDesc(Pageable pageable);



    @Query("""
        SELECT uc.coffee 
        FROM UserCoffee uc 
        WHERE uc.user.email = :email
        """)
    List<Coffee> findFavoritesByUserEmail(@Param("email") String email);


}
