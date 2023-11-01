package com.trnd.trndapi.merchant.exception;

public abstract class MerchantException extends RuntimeException{

    public MerchantException(String msg, Throwable cause){
        super(msg, cause);
    }

    public MerchantException(String msg){
        super(msg);
    }
}
