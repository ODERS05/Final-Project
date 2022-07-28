package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class NotUniqueCustomer extends BaseException{
    public NotUniqueCustomer(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
