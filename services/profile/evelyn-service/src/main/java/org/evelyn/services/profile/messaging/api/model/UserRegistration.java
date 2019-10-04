package org.evelyn.services.profile.messaging.api.model;

import lombok.Data;

@Data
public class UserRegistration {
    private Long expiry;
    private String confirmKey;
    private String email;
    private String userHandle;
    private String password;
}
