package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestStage;
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
public class RequestStageRepositoryTests {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Test
    @DisplayName("Salve the Request Stages")
    public void save(){
        User owner = User.builder().id(3L).build();
        Request request = Request.builder().id(1L).build();
        RequestStage stage = RequestStage.builder()
                .id(null)
                .description("Comprado um novo laptop HP")
                .realizationDate(LocalDateTime.now())
                .state(RequestState.CLOSED)
                .request(request)
                .owner(owner)
                .build();

        RequestStage requestStage = requestStageRepository.save(stage);

        assertThat(requestStage.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Find Request Stages by Id")
    public void getById(){
        Optional<RequestStage> byId = requestStageRepository.findById(1L);
        RequestStage stage = byId.get();

        assertThat(stage.getDescription()).isEqualTo("Comprado um novo laptop HP");
    }

    @Test
    @DisplayName("List All Request Stages")
    public void list(){
        List<RequestStage> stages = requestStageRepository.findAll();

        assertThat(stages.size()).isEqualTo(1);
    }
}
