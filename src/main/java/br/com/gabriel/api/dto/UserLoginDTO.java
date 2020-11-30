package br.com.gabriel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password required")
    private String password;
}
