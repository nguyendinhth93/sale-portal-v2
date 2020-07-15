package com.tp.dto.admin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hopnv on 12/06/2017.
 */
public class ResponseDTO {

    private List<String> messages;
    private Object data;

    public ResponseDTO(){

    }

    public ResponseDTO(String message) {
        this.messages = Arrays.asList(message);
    }

    public ResponseDTO(List<String> messages) {
        this.messages = messages;
    }

    public ResponseDTO(Object data) {
        this.data = data;
    }

    public ResponseDTO(String message, Object data) {
        this.messages = Arrays.asList(message);
        this.data = data;
    }

    public ResponseDTO(List<String> messages, Object data) {
        this.messages = messages;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
