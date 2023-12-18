package com.trnd.trndapi.exception;

public abstract class CampaignException extends RuntimeException{

    public CampaignException(String msg, Throwable cause){
        super(msg, cause);
    }

    public CampaignException(String msg){
        super(msg);
    }
}
