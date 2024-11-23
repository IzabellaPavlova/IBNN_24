package org.ibmm.ibmm24.dto.response;

import java.io.Serializable;

public class CreateOffersOutput implements Serializable {
    private String message;

    public CreateOffersOutput(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
