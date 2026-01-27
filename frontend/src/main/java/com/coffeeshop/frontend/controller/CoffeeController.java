package com.coffeeshop.frontend.controller;

import com.coffeeshop.frontend.model.Coffee;
import com.coffeeshop.frontend.model.User;
import com.coffeeshop.frontend.model.UserCoffee;
import com.coffeeshop.frontend.repositories.CoffeeRepository;
import com.coffeeshop.frontend.repositories.UserCoffeeRepository;
import com.coffeeshop.frontend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    UserCoffeeRepository userCoffeeRepository;

    @Autowired
    UserRepository userRepository;
    @GetMapping("/popular")
    public Page<?> popularCoffees(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (sortBy.equals("favoriteCount")) {
            //use favorite sorting
            return coffeeRepository.findAllOrderByFavoriteCountDesc(pageable);
        } else {
            // Use normal entity sorting
            Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
            Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
            return coffeeRepository.findAll(sortedPageable);
        }
    }


    @GetMapping("/favorites")
    public List<Coffee> getUserFavorites(@RequestParam String email) {
        return coffeeRepository.findFavoritesByUserEmail(email);
    }

    @PostMapping("/{coffeeId}/favorite")
    public void toggleFavorite(
            @PathVariable Long coffeeId,
            @RequestParam String email) {

        userCoffeeRepository.findByUserEmailAndCoffeeId(email, coffeeId)
                .ifPresentOrElse(
                        userCoffeeRepository::delete,
                        () -> {
                            User user = userRepository.findByEmail(email)
                                    .orElseThrow();
                            Coffee coffee = coffeeRepository.findById(coffeeId)
                                    .orElseThrow();

                            UserCoffee uc = new UserCoffee(user, coffee);
                            userCoffeeRepository.save(uc);
                        }
                );
    }




}
