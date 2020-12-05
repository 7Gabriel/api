package br.com.gabriel.api.security;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LoginResponse implements Serializable {

    private String token;
    private Long expireIn;
    private String tokenProvider;
}
