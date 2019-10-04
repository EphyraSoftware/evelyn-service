package org.evelyn.services.profile.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProfileDocument {
    @Id
    public String id;

    public String principalName;
    public String nickname;
}
