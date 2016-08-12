package com.impinj.rtls.connector.app.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by ralemy on 8/8/16.
 * The responsibility of this object is to represent downstream rabbitMQ server.
 */
public class RabbitMqConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String host="localhost";

    @NotEmpty
    @JsonProperty
    private String user = "guest";

    @NotEmpty
    @JsonProperty
    private String password = "guest";

    @JsonProperty
    private int port = 5672;

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
