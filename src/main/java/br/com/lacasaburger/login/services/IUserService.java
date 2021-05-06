package br.com.lacasaburger.login.services;

import java.util.Optional;

import br.com.lacasaburger.login.models.User;

public interface IUserService {


    Optional<User> loadUserByEmail(final String email);
    
}
