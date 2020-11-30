package br.com.gabriel.api.dto;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserSaveDTO {

    @NotBlank(message = "Name required")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @Size(min = 7, max = 99, message = "Password must be between 7 and 99 chars")
    private String password;
    @NotNull
    private Role role;
    private List<Request> requests = new ArrayList<>();
    private List<RequestStage> stages = new ArrayList<>();
}
