package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(bundle.getString("api.chipped-pets-helper.server.error.user-not-found")));
    }
}