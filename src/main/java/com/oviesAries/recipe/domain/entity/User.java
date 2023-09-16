package com.oviesAries.recipe.domain.entity;

import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "password", nullable = false)
    private EncodedPassword encodedPassword;
    private Time created_at;
    private String email;

    @Builder
    private User(
            String userName,
            String nickName,
            final EncodedPassword encodedPassword,
            String email
    ) {
        this.userName = userName;
        this.nickName = nickName;
        this.encodedPassword = encodedPassword;
        this.email = email;
    }

    // 정적 팩토리 메소드 추가
    public static User of(
            String userName,
            String nickName,
            final EncodedPassword encodedPassword,
            String email
    ) {
        return new User(userName, nickName, encodedPassword, email);
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


    // equals() 메소드 추가
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    // hashCode() 메소드 추가
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString() 메소드 추가
    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + encodedPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

