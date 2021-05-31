package org.tn.esprit.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class Error extends java.lang.Error {

    private int code;
     private String message;
     private String description;



    public Error(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
