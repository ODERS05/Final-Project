package kg.itacademy.sewerfactory.exception;

import org.springframework.http.HttpStatus;

public class UserSignInException extends BaseException{
    public UserSignInException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
