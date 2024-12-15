package com.tugrulhan.freethoughts.exceptions;

public class BaseException extends RuntimeException {
    public BaseException() {}
    public BaseException(ErrorMessage errorMessage) {//burda aldığımız değeri runtimeException'a veriyoruz
        super(errorMessage.prepareErrorMessage());
    }


}
