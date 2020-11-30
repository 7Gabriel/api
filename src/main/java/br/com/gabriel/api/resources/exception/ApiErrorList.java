package br.com.gabriel.api.resources.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ApiErrorList extends ApiError{

    private List<String> errors;
    public ApiErrorList(int code, String msg, LocalDateTime date, List<String> errors){
        super(code, msg, date);
        this.errors = errors;
    }

}
