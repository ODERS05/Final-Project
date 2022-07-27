package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class NotUniqueDepartment extends BaseException{
    public NotUniqueDepartment(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
