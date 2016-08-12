package com.impinj.rtls.connector;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
