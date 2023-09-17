package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INGREDIENTS")
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private Integer quantity;

    private Integer userIngredientId;

    @Builder
    public UserIngredient(
            final Long id,
            final User user,
            final Ingredient ingredient,
            final Integer quantity
    ) {
            this.id = id;
            this.user = user;
            this.ingredient = ingredient;
            this.quantity = quantity;
    }


    public static UserIngredient of(
            final Long id,
            final User user,
            final Ingredient ingredient,
            final Integer quantity
    ) {
        return UserIngredient.builder()
                .id(id)
                .user(user)
                .ingredient(ingredient)
                .quantity(quantity)
                .build();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUserIngredientId(Integer userIngredientId) {
        this.userIngredientId = userIngredientId;
    }
}
