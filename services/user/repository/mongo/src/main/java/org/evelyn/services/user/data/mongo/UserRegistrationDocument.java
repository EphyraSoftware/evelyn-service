package org.evelyn.services.user.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserRegistrationDocument {
    @Id
    public String id;

    public Long expiry;
    public String confirmKey;
    public String email;
    public String userHandle;
    public String password;
}
