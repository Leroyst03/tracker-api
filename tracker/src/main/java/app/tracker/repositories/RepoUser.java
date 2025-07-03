package app.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.tracker.entities.User;

@Repository
public interface RepoUser extends JpaRepository<User, Long>{
    public User findUserByEmail(String email);
    public boolean existsByEmail(String email);
    public User findUserById(Long id);
}
