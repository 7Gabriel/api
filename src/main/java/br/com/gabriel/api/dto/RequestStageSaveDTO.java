package br.com.gabriel.api.dto;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestStageSaveDTO {

    private String description;
    @NotNull(message = "State required")
    private RequestState state;
    @NotNull(message = "Request required")
    private Request request;
    @NotNull(message = "Owner required")
    private User owner;
}
