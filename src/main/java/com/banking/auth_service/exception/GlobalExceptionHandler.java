    package com.banking.auth_service.exception;

    import com.banking.auth_service.DTO.ErrorResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.*;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(UserAlreadyExistException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public ErrorResponse handleUserAlreadyExistException(UserAlreadyExistException exception) {
            return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
        }

        @ExceptionHandler(UserNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
            return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }

        @ExceptionHandler(FileStorageException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleFileStorageException(FileStorageException exception) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleValidationErrorException(MethodArgumentNotValidException exception) {
            System.out.println("Validation Error: " + exception.toString());

            String message = exception.getBindingResult().getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("Invalid request");

            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        }


        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleGenericException(Exception exception) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error: " + exception.getMessage());
        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException exception) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
