package com.company.productname.dto;

import com.company.productname.constants.MessageType;

/**
 * Created by darrell-shofstall on 8/10/17.
 */
public class MessageDTO {

    private String message;
    private MessageType type;

    public MessageDTO() {
        super();
    }

    public MessageDTO(MessageType type, String message) {
        super();
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
