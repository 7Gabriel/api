package br.com.gabriel.api.dto;

import br.com.gabriel.api.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRoleDTO {

    private Role role;
}
