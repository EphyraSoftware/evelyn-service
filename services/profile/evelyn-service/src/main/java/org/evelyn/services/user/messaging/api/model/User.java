package org.evelyn.services.user.messaging.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String email;
    private Date dateCreated;
    private String handle;
    private String password;
}
