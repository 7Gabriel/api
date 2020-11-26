package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u WHERE email = ?1 AND password = ?2")
    public Optional<User> login(String email, String password);
}
