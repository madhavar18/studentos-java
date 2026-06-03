package com.studentos.security;

import com.studentos.repository.UserRepository;
import com.studentos.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

            Optional<User> found = userRepository.findByUserName(userName);
            if(found.isEmpty()) throw new UsernameNotFoundException("User not found: " + userName);
            User user = found.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPasswordHash())
                    .roles(user.getRole())
                    .build();
    }
}
