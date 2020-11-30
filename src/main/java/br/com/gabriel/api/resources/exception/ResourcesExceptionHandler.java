package br.com.gabriel.api.resources.exception;

import br.com.gabriel.api.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex){
        ApiError error = ApiError.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .msg(ex.getMessage())
            .date(LocalDateTime.now()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String defaultMessage =  ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiError error = ApiError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .msg(defaultMessage)
                .date(LocalDateTime.now())
                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
