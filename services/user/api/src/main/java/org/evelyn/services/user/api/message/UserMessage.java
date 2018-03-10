package org.evelyn.services.user.api.message;

import java.io.Serializable;

public class UserMessage implements Serializable {
    private String id;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
