package com.impinj.rtls.connector.app.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by ralemy on 8/8/16.
 * The responisibitliy of this object is to represent Configuration for upstream itemsense instance.
 */

public class ItemsenseConfig {
    @NotEmpty
    @JsonProperty
    private String baseUrl;

    @NotEmpty
    @JsonProperty
    private Integer basePort=80;

    @NotEmpty
    @JsonProperty
    private String username;

    @NotEmpty
    @JsonProperty
    private String password;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getBasePort() {
        return basePort;
    }
}
