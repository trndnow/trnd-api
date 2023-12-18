package com.trnd.trndapi.exception;

public class CampaignNotFoundException extends CampaignException{
    public CampaignNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public CampaignNotFoundException(String msg) {
        super(msg);
    }
}
