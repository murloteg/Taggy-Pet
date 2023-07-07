package ru.nsu.sberlab.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser (User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.setActive(true);
        user.setHasElevatedPrivileges(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }
}