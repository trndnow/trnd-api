package com.trnd.trndapi.exception;

public abstract class AffiliateException extends RuntimeException{
    public AffiliateException(String msg, Throwable cause){
        super(msg, cause);
    }

    public AffiliateException(String msg){
        super(msg);
    }
}
