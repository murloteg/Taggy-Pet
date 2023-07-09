package ru.nsu.sberlab.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.models.mappers.UserDtoMapper;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @Transactional
    public boolean createUser (UserRegistrationDto userDto) {
        User user = userDtoMapper.mapDtoToUser(userDto);
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
        user.setEmail(user.getEmail() + " DELETED WITH ID: " + user.getId());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}