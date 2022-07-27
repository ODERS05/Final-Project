package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException{
    public OrderNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
