package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(bundle.getString("api.chipped-pets-helper.server.error.user-not-found")); // TODO
        }
        return user.get();
    }
}