package com.impinj.rtls.connector.health;

import com.codahale.metrics.health.HealthCheck;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import com.impinj.rtls.connector.config.ConnectorConfiguration;
import com.impinj.rtls.connector.config.ItemsenseConfig;

import javax.inject.Inject;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to check that Upstream Itemsense is available
 */
public class UpstreamHealthCheck extends InjectableHealthCheck {
    private final ItemsenseConfig config;

    @Inject
    public UpstreamHealthCheck(ConnectorConfiguration config){
        this.config = config.getItemsenseConfig();
    }

    @Override
    protected Result check() throws Exception {
        try{
            (new Socket(config.getBaseUrl(),80)).close();
            return Result.healthy();
        }catch(Exception e){
            return Result.unhealthy("ItemSense is not listening on "+config.getBaseUrl());
        }
    }

    @Override
    public String getName() {
        return "upstreamItemSense";
    }
}
