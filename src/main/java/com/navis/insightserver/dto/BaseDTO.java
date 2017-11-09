package com.navis.insightserver.dto;

import java.util.List;

/**
 * Created by darrell-shofstall on 8/15/17.
 */
public class BaseDTO  implements java.io.Serializable {


   private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
