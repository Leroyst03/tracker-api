package app.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.tracker.dto.UserRequest;
import app.tracker.entities.User;
import app.tracker.exception.HttpException;
import app.tracker.repositories.RepoUser;

@Service
public class UserService {
    @Autowired
    private RepoUser repoUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRequest user) {
        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        repoUser.save(newUser);
    }

    public Long verifyPassword(UserRequest user) {
        if(!repoUser.existsByEmail(user.getEmail())) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        User actualUser = repoUser.findUserByEmail(user.getEmail());

        if(passwordEncoder.matches(user.getPassword(), actualUser.getPassword())) {
            return actualUser.getId();
        }
        return (long) 0;
    }

}
