package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Table(name = "INGREDIENT")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private String name;

    private Blob icon;

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(Blob icon) {
        this.icon = icon;
    }
}
