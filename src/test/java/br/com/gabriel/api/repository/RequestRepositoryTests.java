package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.enums.RequestState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RequestRepositoryTests {

    @Autowired
    private RequestRepository requestRepository;

    @Test
    @DisplayName("Save one Request")
    public void save(){
        User owner = User.builder().id(7L).build();
        Request request = Request.builder()
                .id(null)
                .description("Pretendo obter um novo")
                .subject("Novo Laptop HP")
                .creationDate(LocalDateTime.now())
                .state(RequestState.OPEN)
                .owner(owner)
                .stages(null).build();
        Request requestSave = requestRepository.save(request);

        assertThat(requestSave.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("Update one Request")
    public void update(){
        User owner = User.builder().id(7L).build();
        Request request = Request.builder()
                .id(null)
                .description("Pretendo obter um novo")
                .subject("Novo Laptop HP com 16 RAM")
                .state(RequestState.OPEN)
                .owner(owner)
                .stages(null).build();
        Request requestSave = requestRepository.save(request);

        assertThat(requestSave.getSubject()).isEqualTo("Novo Laptop HP com 16 RAM");
    }

    @Test
    @DisplayName("Find Request by Id")
    public void getById(){
        Optional<Request> request = requestRepository.findById(3L);
        Request request1 = request.get();

        assertThat(request1.getSubject()).isEqualTo("Novo Laptop HP com 16 RAM");
    }

    @Test
    @DisplayName("List all Request")
    public void list(){
        List<Request> all = requestRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Update Status")
    public void updateStatus(){
        int affectRows = requestRepository.updateStatus(3L, RequestState.CLOSED);

        assertThat(affectRows).isEqualTo(1);
    }
}
