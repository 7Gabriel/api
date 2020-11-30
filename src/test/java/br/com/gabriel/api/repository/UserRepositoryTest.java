package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Salva um usuario")
    public void save(){
        User user = User.builder()
                .id(null)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .role(Role.ADMINISTRATOR)
                .password("123")
                .requests(null)
                .requestsStage(null)
                .build();

        User createUser = repository.save(user);

        assertThat(createUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("Atualiza um usuario")
    public void update(){
        User user = User.builder()
                .id(1L)
                .name("Gabriel Oliveira")
                .email("gabriel@gmail.com")
                .role(Role.ADMINISTRATOR)
                .password("123")
                .requests(null)
                .requestsStage(null)
                .build();
        User updateUser = repository.save(user);
        assertThat(updateUser.getName()).isEqualTo("Gabriel Oliveira");
    }

    @Test
    @DisplayName("Recupera um usuario pelo id")
    public void getById(){
        Optional<User> result = repository.findById(1L);
        User user = result.get();

        assertThat(user.getPassword()).isEqualTo("123");
    }

    @Test
    @DisplayName("Lista todos os usuarios")
    public void list(){
        List<User> users = repository.findAll();

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Tenta logar um usuario com sucesso")
    public void login(){
        Optional<User> user = repository.login("gabriel@gmail.com", "123");

        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("Deleta um usuario")
    public void delete(){
        Optional<User> user = repository.findById(1L);
        repository.delete(user.get());
        Optional<User> user2 = repository.findById(1L);
        assertThat(user2).isNull();
    }

    @Test
    @DisplayName("Update Role of one user")
    public void updateRole3(){
        int affectedRows = repository.updateRole(7L, Role.SIMPLE);
        assertThat(affectedRows).isEqualTo(1);
    }

}
