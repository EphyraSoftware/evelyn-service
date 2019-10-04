package org.evelyn.services.profile.messaging.api.model;

import java.io.Serializable;

public class UserCreatedMessage implements Serializable {
    public String email;
    public String confirmKey;
}
