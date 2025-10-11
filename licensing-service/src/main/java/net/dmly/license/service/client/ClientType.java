package net.dmly.license.service.client;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClientType {
    FEIGN, REST, DISCOVERY;

    @JsonCreator
    public static ClientType fromString(String clientType) {
        for (ClientType type : ClientType.values()) {
            if (type.toString().equalsIgnoreCase(clientType)) {
                return type;
            }
        }
        return null;
    }

}
