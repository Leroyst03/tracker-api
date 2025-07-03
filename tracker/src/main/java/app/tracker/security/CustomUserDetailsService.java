package app.tracker.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.tracker.entities.User;
import app.tracker.repositories.RepoUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private RepoUser repoUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repoUser.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No encontrado: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = repoUser.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ID: " + id));

        return new CustomUserDetails(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
