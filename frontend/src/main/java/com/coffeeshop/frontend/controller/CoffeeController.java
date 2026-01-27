package com.coffeeshop.frontend.controller;

import com.coffeeshop.frontend.model.Coffee;
import com.coffeeshop.frontend.repositories.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    CoffeeRepository coffeeRepository;

    @GetMapping("/coffees/popular")
    public Page<Coffee> popularCoffees(Pageable pageable) {
        return coffeeRepository.findAllOrderByFavoriteCountDesc(pageable);
    }


}
