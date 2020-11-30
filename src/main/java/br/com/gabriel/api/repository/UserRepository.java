package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u WHERE email = ?1 AND password = ?2")
    public Optional<User> login(String email, String password);

    @Modifying
    @Transactional(readOnly = false)
    @Query("UPDATE user SET role = 2?, HERE id = 1?")
    public int updateRole(Long id, Role role);
}
