package br.com.gabriel.api.dto;

import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestUpdateDTO {

    @NotBlank(message = "Subject required")
    private String subject;
    private String description;
    @NotNull(message = "State required")
    private RequestState state;
    @NotNull(message = "Owner required")
    private User owner;
    private List<RequestStage> stages = new ArrayList<>();
}
