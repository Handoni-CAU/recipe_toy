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

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserIngredientRepository userIngredientRepository;
    private final IngredientRepository ingredientRepository;

    private final PasswordEncoder passwordEncoder;
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

    @Override
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

    @Override
    public UserIngredient addIngredientToUser(Long userId, UserIngredient userIngredient) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Recipe with user: " + userId + " does not exist."));

        Ingredient ingredient = Ingredient.builder()
                .name(userIngredient.getIngredient().getName())
                .icon(userIngredient.getIngredient().getIcon())
                .build();

        int currentSequenceCount = user.getUserIngredients().size();
        userIngredient.setUserIngredientId(currentSequenceCount + 1);

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        userIngredient.setIngredient(savedIngredient);
        userIngredient.setUser(user);


        if (user.getUserIngredients() == null) {
            user.updateUserIngredients(new ArrayList<>());
        }

        user.getUserIngredients().add(userIngredient);
        return userIngredientRepository.save(userIngredient);
    }

    @Override
    public void deleteUserIngredient(Long userId, Integer ingredientOrderToRemove) {
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

    @Transactional
    public UserIngredientResponse create(final AuthPrincipal authPrincipal, final List<UserIngredientDTO> request) {
        log.info("주문 생성 memberId: {}", authPrincipal.getId());
        UserIngredient user = ingredientMapper.mapFrom(authPrincipal.getId(), request);
        // user에서 가져오겠금

        userIngredientRepository.save(user);
        return UserIngredientResponse.from(user.getUser());
    }






}
