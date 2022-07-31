package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class ImpossibleCaseException extends BaseException{
    public ImpossibleCaseException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
