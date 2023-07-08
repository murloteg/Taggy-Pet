package ru.nsu.sberlab.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.UserDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.models.mappers.UserDtoMapper;
import ru.nsu.sberlab.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    public boolean createUser (User user) {
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isPresent() && currentUser.get().isActive()) {
            return false;
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }

    public void deleteUser(Long id) {
        User user = userRepository.getById(id);
        user.setActive(false);
        user.setEmail("DELETED: " + user.getEmail() + " WITH ID: " + user.getId());
        Optional<User> possibleUser = userRepository.findByEmail(user.getEmail());
        possibleUser.ifPresent(value -> userRepository.deleteById(value.getId()));
        userRepository.save(user);
    }

    public UserDto getUserByPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .map(userDtoMapper)
                .orElse(null);
    }
}