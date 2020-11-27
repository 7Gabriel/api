package br.com.gabriel.api.resources.exception;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ApiError implements Serializable {

    private int code;
    private String msg;
    private LocalDateTime date;

}
