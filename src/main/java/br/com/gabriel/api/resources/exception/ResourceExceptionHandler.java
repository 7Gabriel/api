package br.com.gabriel.api.resources.exception;

import br.com.gabriel.api.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex){
        ApiError error = ApiError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .msg(ex.getMessage())
                .date(LocalDateTime.now()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){
        ApiError error = ApiError.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .msg(ex.getMessage())
                .date(LocalDateTime.now()).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
        ApiError error = ApiError.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .msg(ex.getMessage())
                .date(LocalDateTime.now()).build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        ApiError error = ApiError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .msg(ex.getMessage())
                .date(LocalDateTime.now()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        ApiErrorList error = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), "Invalid Fields", LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
