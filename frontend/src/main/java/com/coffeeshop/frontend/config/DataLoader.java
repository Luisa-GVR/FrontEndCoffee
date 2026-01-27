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
            userRepository.save(
                    new User("Marianin", "marianin@gmail.com", "marianin123")
            );
        }

        if (coffeeRepository.count() == 0) {
            coffeeRepository.save(new Coffee("Espresso", "img/espresso.png", 35.0));
            coffeeRepository.save(new Coffee("Capuccino", "img/capuccino.png", 45.0));
            coffeeRepository.save(new Coffee("Mocha", "img/mocha.png", 28.0));
            coffeeRepository.save(new Coffee("Americano", "img/americano.png", 22.0));
            coffeeRepository.save(new Coffee("Latte", "img/latte.png", 40.0));
            coffeeRepository.save(new Coffee("Turkish", "img/turkish.png", 55.0));
            coffeeRepository.save(new Coffee("Frappe", "img/frappe.png", 70.0));
            coffeeRepository.save(new Coffee("Cold Brew", "img/coldbrew.png", 33.0));
            coffeeRepository.save(new Coffee("Irish", "img/irish.png", 38.0));
            coffeeRepository.save(new Coffee("Cortado", "img/cortado.png", 33.0));
            coffeeRepository.save(new Coffee("Flat White", "img/flatwhite.png", 44.0));
            coffeeRepository.save(new Coffee("Machiatto", "img/machiatto.png", 52.0));
            coffeeRepository.save(new Coffee("El Gallo jefe", "img/logo.jpg", 1234.56));

        }
    }


}
