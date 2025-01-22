package com.app.app.Services;

import com.app.app.Models.UserDetails;
import com.app.app.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    public UserDetails getUserById(Long id) {
        return userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public UserDetails createUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public UserDetails updateUser(Long id, UserDetails userDetails) {
        UserDetails existingUser = getUserById(id);
        existingUser.setPhoneNo(userDetails.getPhoneNo());
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setLocation(userDetails.getLocation());
        return userDetailsRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userDetailsRepository.deleteById(id);
    }
}

