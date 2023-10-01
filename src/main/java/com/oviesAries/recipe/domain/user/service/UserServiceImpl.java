package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.dao.IngredientRepository;
import com.oviesAries.recipe.domain.entity.*;
import com.oviesAries.recipe.domain.user.dao.UserIngredientRepository;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import com.oviesAries.recipe.domain.user.domain.AuthPrincipal;
import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import com.oviesAries.recipe.domain.user.domain.IngredientMapper;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.response.UserIngredientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserIngredientRepository userIngredientRepository;
    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    @Override
    public User createUser(String username, EncodedPassword password) {
        User user = User.builder()
                .userName(username)
                .encodedPassword(password)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserIngredient> getAllUserIngredient(Long id) {
        return  userIngredientRepository.findByUserId(id);
    }

    @Override
    public UserIngredient getUserIngredientById(Long id) {
        return userIngredientRepository.findById(id).orElse(null);
    }

    @Transactional
    public UserIngredient addIngredient(final UserIngredientDTO request, final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Recipe with user: " + userId + " does not exist."));

        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(request.getProductId());

        Ingredient savedIngredient = existingIngredient.get();

        UserIngredient userIngredient = UserIngredient.builder()
                .user(user)
                .ingredient(savedIngredient)
                .quantity(request.getQuantity())
                .build();

        int currentSequenceCount = user.getUserIngredients().size();
        userIngredient.setUserIngredientId((long) (currentSequenceCount + 1));

        if (user.getUserIngredients() == null) {
            user.updateUserIngredients(new ArrayList<>());
        }

        user.addUserIngredient(userIngredient);

        return userIngredientRepository.save(userIngredient);
    }

    // delete

    @Transactional
    public void deleteUserIngredient(Long userId, Long ingredientOrderToRemove) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Recipe with user: " + userId + " does not exist."));

        List<UserIngredient> userIngredients = user.getUserIngredients();

        UserIngredient stepToRemove = null;
        for (UserIngredient ingredient : userIngredients) {
            if (ingredient.getUserIngredientId().equals(ingredientOrderToRemove)) {
                stepToRemove = ingredient;
                break;
            }
        }
        if (stepToRemove != null) {
            userIngredients.remove(stepToRemove);
            userIngredientRepository.delete(stepToRemove);
        }

        for (UserIngredient ingredient : userIngredients) {
            if (ingredient.getUserIngredientId() > ingredientOrderToRemove) {
                ingredient.setUserIngredientId(ingredient.getUserIngredientId() - 1);
                userIngredientRepository.save(ingredient);
            }
        }
    }

}
