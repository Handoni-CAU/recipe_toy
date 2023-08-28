package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "RECIPE")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String dish_name;

    private Integer totalTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeStep> steps;

}
