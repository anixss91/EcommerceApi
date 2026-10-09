package com.Eapi.scalable.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandller {

    //catches 404 not foung
    @ExceptionHandler(ResourceNotFountException.class)
    public ResponseEntity<ErrorResponse> handleRessourcrNotFound(ResourceNotFountException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()

        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Catch 400 - Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity <ErrorResponse> handleBadRequest (BadRequestException ex){
        ErrorResponse error =new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    // Catch-all for any other unhandled exceptions (500 Internal Server Error)

    @ExceptionHandler(Exception.class)
    public ResponseEntity <ErrorResponse> handleGlobalException(Exception ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
       String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ":" + error.getDefaultMessage())
               .reduce((msg1 , msg2) -> msg1 + "," +msg2 )
               .orElse("validation failled");

       ErrorResponse error = new ErrorResponse(
               HttpStatus.BAD_REQUEST.value(),
               "Bad request",
               errorMessage
       );
       return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }

}