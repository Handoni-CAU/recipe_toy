package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "RECIPE")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String dishName;

    private Integer totalTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeStep> steps;

}
