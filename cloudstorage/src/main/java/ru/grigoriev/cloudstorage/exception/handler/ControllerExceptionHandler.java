package ru.grigoriev.cloudstorage.exception.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.grigoriev.cloudstorage.exception.NotFoundException;
import ru.grigoriev.cloudstorage.web.response.BaseWebResponse;

import java.sql.SQLException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<BaseWebResponse> handleSQLException(@NonNull final SQLException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseWebResponse> handleBadCredentialsException(@NonNull final BadCredentialsException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    private String createErrorMessage(Exception exception) {
        final String message = ExceptionHandlerUtils.buildErrorMessage(exception);
        log.error(message);
        return message;
    }
}
