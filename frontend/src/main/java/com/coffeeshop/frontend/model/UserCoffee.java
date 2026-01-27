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

    public UserCoffee(User user, Coffee coffee) {
        this.user = user;
        this.coffee = coffee;
    }

    public UserCoffee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }
}
