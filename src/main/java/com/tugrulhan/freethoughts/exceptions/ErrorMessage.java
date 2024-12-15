package com.tugrulhan.freethoughts.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Data
public class ErrorMessage {

    private MessageType messageType;
    private String ofStatic;

    public ErrorMessage(String message, HttpStatus httpStatus) {
    }

    public String prepareErrorMessage() {
        StringBuilder builder=new StringBuilder();
        builder.append(messageType.getMessage());
        if (ofStatic!=null) {
            builder.append(" "+ofStatic);
        }
        return builder.toString();
    }
    
   
}
