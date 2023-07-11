package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PropertyResolverUtils propertyResolverUtils;

    @Transactional
    public void createUser (UserRegistrationDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getFirstName(),
                userDto.getPassword()
        );
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isPresent() && currentUser.get().isActive()) {
            throw new FailedUserCreationException();
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(propertyResolverUtils.resolve("api.server.error.user-not-found", Locale.getDefault())));
        user.setActive(false);
        user.setEmail(user.getEmail() + " DELETED WITH ID: " + user.getId());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(propertyResolverUtils.resolve("api.server.error.user-not-found", Locale.getDefault())));
    }
}