package br.com.lacasaburger.login.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lacasaburger.login.models.User;
import br.com.lacasaburger.login.repositories.IUserRepository;
import br.com.lacasaburger.login.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    
    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
