package com.coffeeshop.frontend.repositories;

import com.coffeeshop.frontend.model.UserCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCoffeeRepository extends JpaRepository<UserCoffee, Long> {


    Optional<UserCoffee> findByUserIdAndCoffeeId(Long userId, Long coffeeId);
    List<UserCoffee> findByUserId(Long userId);

}
