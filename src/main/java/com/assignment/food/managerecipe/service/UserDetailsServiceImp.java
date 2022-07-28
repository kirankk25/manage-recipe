package com.assignment.food.managerecipe.service;

import com.assignment.food.managerecipe.model.User;
import com.assignment.food.managerecipe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional=userRepository.findByUserName(username);
        User user=userOptional.orElseThrow(()->new UsernameNotFoundException("No user found by username:" +
                username));

        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),
                user.isEnabled(),true,true,true,
                getAuthorities("USER"));

    }
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
