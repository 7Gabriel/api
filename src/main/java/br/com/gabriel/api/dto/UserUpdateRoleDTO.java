package br.com.gabriel.api.dto;

import br.com.gabriel.api.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserUpdateRoleDTO {

    @NotNull(message = "Role required")
    private Role role;
}
