package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "RECIPE")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter // setter 대신 필요한 것들만 따로 메서드 만들어두는게 좋을 듯
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String dishName;

    private String subtitle;

    private Integer totalTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeStep> recipeSteps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients;


}
