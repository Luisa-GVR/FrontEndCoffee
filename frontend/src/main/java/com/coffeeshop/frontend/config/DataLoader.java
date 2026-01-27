package com.coffeeshop.frontend.config;

import com.coffeeshop.frontend.model.Coffee;
import com.coffeeshop.frontend.model.User;
import com.coffeeshop.frontend.repositories.CoffeeRepository;
import com.coffeeshop.frontend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private UserRepository userRepository;


    @PostConstruct
    public void loadData() {
        if (userRepository.count() == 0) {
            userRepository.save(
                    new User("Luisa", "luisagavr@gmail.com", "luisa123")
            );
        }

        if (coffeeRepository.count() == 0) {
            coffeeRepository.save(new Coffee("Espresso", "img/espresso.png", 35.0));
            coffeeRepository.save(new Coffee("Capuccino", "img/capuccino.png", 45.0));
            coffeeRepository.save(new Coffee("Mocha", "img/mocha.png", 28.0));
        }
    }


}
