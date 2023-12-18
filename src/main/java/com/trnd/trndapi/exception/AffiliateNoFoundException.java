package com.trnd.trndapi.exception;

public class AffiliateNoFoundException extends AffiliateException{
    public AffiliateNoFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AffiliateNoFoundException(String msg) {
        super(msg);
    }
}
