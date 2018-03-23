package org.evelyn.services.user.api.message;

import java.io.Serializable;

public class UserMessage implements Serializable {
    private String id;
    private String email;
    private String handle;

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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
