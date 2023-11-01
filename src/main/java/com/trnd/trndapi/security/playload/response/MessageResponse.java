package com.trnd.trndapi.security.playload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse<T> {
    private String message;
    private T data;
    public MessageResponse(String message){
        this.message = message;
    }
}
