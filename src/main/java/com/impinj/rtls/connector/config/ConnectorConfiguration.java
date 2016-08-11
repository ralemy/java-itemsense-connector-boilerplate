package com.impinj.rtls.connector.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by ralemy on 8/8/16.
 * The responsibility of this object is to represent external configuration for the connector
 */
public class ConnectorConfiguration extends Configuration {
    @JsonProperty
    private ItemsenseConfig itemsenseConfig;

    @JsonProperty
    private RabbitMqConfiguration rabbitMqConfiguration;

    public ItemsenseConfig getItemsenseConfig() {
        return itemsenseConfig;
    }

    public RabbitMqConfiguration getRabbitMqConfiguration() {
        return rabbitMqConfiguration;
    }

}
