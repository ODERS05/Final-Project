package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class NotFinishWorkException extends BaseException{
    public NotFinishWorkException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
