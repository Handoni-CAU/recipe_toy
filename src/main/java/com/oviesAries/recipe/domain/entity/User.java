package com.oviesAries.recipe.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserIngredient> userIngredients;

    @Column(nullable = false, length = 20, unique = true)
    private String userName;

    private String nickName;

    private String password;


    private Time created_at;
    

    private String email;


    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateUserIngredients(List<UserIngredient> userIngredients) {
        this.userIngredients = userIngredients;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
