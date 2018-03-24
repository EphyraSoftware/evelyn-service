package org.evelyn.services.user.data.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRegistration {
    private Long expiry;
    private String confirmKey;
    private String email;
    private String userHandle;
    private String password;
}
