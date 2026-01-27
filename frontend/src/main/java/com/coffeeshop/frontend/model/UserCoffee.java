package com.coffeeshop.frontend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_coffee")
public class UserCoffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;


}
