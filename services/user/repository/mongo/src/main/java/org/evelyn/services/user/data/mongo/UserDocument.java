package org.evelyn.services.user.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class UserDocument {
    @Id
    public String id;

    public String userId;
    public String handle;
    public String email;
    public Date dateCreated;
    public String password;

    @Override
    public String toString() {
        return "UserDocument{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", handle='" + handle + '\'' +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
