package org.evelyn.services.user.data.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRegistration {
    private Date expiry;
    private String confirmKey;
    private String email;
    private String userHandle;
}
