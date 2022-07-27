package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class DepartmentNotFoundException extends BaseException{
    public DepartmentNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
