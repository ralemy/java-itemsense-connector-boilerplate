package com.impinj.rtls.connector;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.impinj.rtls.connector.config.ConnectorConfiguration;
import com.impinj.rtls.connector.service.ItemsenseApi;
import com.impinj.rtls.connector.service.OutputQueue;

import javax.inject.Inject;

/**
 * Created by ralemy on 8/8/16.
 * Module to inject dependencies using Guice
 */
public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {

    }


    @Provides
    @Singleton
    public ActorSystem amqpSystem(){
        return ActorSystem.create("ImpinjRTLSActors");
    }

}
