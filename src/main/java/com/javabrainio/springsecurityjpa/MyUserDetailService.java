package com.javabrainio.springsecurityjpa;

import com.javabrainio.springsecurityjpa.model.MyUserDetails;
import com.javabrainio.springsecurityjpa.model.User;
import com.javabrainio.springsecurityjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// You have to create your customer UserDetailsService
@Service
public class MyUserDetailService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // return that hardcoded instance. (No security validation check)
        // (Below is commented to implement the algorithm to lookup User database and check username and password.
        // return new MyUserDetails(username);


        // Validate your user in the user table through JPA
        // My own comment: should validate the password here.
        Optional<User> userOptional = userRepository.findByUsername(username);
//        return new MyUserDetails(userOptional.get());

        userOptional.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        // New discovery, you can do this like below:
        // This algorithm can prevent you have to use isPresent() method before creating MyUserDetails object.
        // If there's no such user, it saves null, which mean saves nothing into the Spring Security context.
        // Better, elegant, safer and faster.
        return userOptional.map(MyUserDetails::new).get();
    }
}
