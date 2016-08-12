package com.impinj.rtls.connector.app.health;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import com.impinj.rtls.connector.app.config.ConnectorConfiguration;
import com.impinj.rtls.connector.app.config.RabbitMqConfiguration;

import javax.inject.Inject;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by ralemy on 8/9/16.
 * the responsibility of this object is to checks that downstream RabbitMQ is running on the specified port
 */

public class QueueHealthCheck extends InjectableHealthCheck {
    private final RabbitMqConfiguration config;

    @Inject
    public QueueHealthCheck(ConnectorConfiguration config){
        this.config = config.getRabbitMqConfiguration();
    }

    @Override
    protected Result check() throws Exception{
        try{
            (new Socket(config.getHost(),config.getPort())).close();
            return Result.healthy();
        }catch(SocketException e){
            return Result.unhealthy(String.format("RabbitMQ not listening on port %d of %s", config.getPort(),config.getHost()));
        }
    }

    @Override
    public String getName() {
        return "outputRabbitMq";
    }
}
