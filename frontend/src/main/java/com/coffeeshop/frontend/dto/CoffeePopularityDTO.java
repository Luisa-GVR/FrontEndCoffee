package com.coffeeshop.frontend.dto;

public record CoffeePopularityDTO(
        Long id,
        String name,
        String imageUrl,
        Double price,
        Long favoriteCount
) {
}
