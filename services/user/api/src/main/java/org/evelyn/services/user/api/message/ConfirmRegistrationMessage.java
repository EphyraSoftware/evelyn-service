package org.evelyn.services.user.api.message;

public class ConfirmRegistrationMessage {
    private String confirmKey;

    public String getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(String confirmKey) {
        this.confirmKey = confirmKey;
    }
}
