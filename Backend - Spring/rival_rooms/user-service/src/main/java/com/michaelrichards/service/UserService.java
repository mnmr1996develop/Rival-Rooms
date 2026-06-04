package com.michaelrichards.service;

import com.michaelrichards.dto.RegisterUser;
import com.michaelrichards.dto.UserDetails;
import com.michaelrichards.model.User;
import com.michaelrichards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDetails> findAll() {
        return userRepository.findAll().stream().map(this::mapUserToUserDetails)
                .toList();
    }

    public UserDetails mapUserToUserDetails(User user) {
        return UserDetails.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .build();
    }

    public UserDetails register(RegisterUser user) {

        checkUsername(user.getUsername());
        checkEmail(user.getEmail());
        checkPassword(user.getPassword(), user.getPassword());
        checkBirthday(user.getBirthday());



        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .birthDate(user.getBirthday())
                .password(user.getPassword())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        User savedUser  = userRepository.save(newUser);

       return mapUserToUserDetails(savedUser);
    }

    private void checkEmail(String email) {

        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email is null or empty");

        if (!email.contains(".") || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email");


        if (userRepository.existsByEmailIgnoreCase(email))
            throw new IllegalArgumentException("E-mail already taken");
    }

    private void checkUsername(String username){

        if (username.contains(" "))
            throw new IllegalArgumentException("Username cannot contain spaces");

        if (userRepository.existsByUsernameIgnoreCase(username))
            throw new IllegalArgumentException("Username already taken");
    }

    private void checkPassword(String password, String confirmPassword) {

        if(password.isBlank() || confirmPassword.isBlank())
            throw new IllegalArgumentException("Password or Confirm Password is blank");

        if (!password.equals(confirmPassword))
            throw new IllegalArgumentException("Passwords do not match");

        if (password.length() < 6)
            throw new IllegalArgumentException("Passwords too short");

    }

    private void checkBirthday(LocalDate birthday) {
        if (birthday == null)
            throw new IllegalArgumentException("Birthday is null");

        if(Period.between(birthday, LocalDate.now()).getYears() > 200)
            throw new IllegalArgumentException("Invalid birthday");

        if (LocalDate.now().isAfter(birthday)) {
            throw new IllegalArgumentException("Invalid birthday");
        }

        if(Period.between(birthday, LocalDate.now()).getYears() < 13)
            throw new IllegalArgumentException("User too young");
    }

    private static boolean hasNoSpecialCharacters(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^[a-zA-Z]+$");
    }


}
