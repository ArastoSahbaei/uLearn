package se.robasto.Exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    String msg = "user already exists";

    public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}