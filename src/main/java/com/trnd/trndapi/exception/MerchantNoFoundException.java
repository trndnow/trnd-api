package com.trnd.trndapi.exception;

public class MerchantNoFoundException extends MerchantException{

    public MerchantNoFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MerchantNoFoundException(String msg) {
        super(msg);
    }
}
