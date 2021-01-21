package be.about.coding.codequality;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(
        IllegalArgumentException ex, WebRequest request) {

        ApiError error = new ApiError("Bad request", "The request failed validation", ex.getMessage());
        return handleExceptionInternal(ex, error,
                                       new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
