package com.impinj.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.actor.Props;
import com.google.inject.Injector;

/**
 * Created by ralemy on 8/9/16.
 * the responsibility of this object to allow DI to actors using Guice.
 */

public class GuiceInjectedActor implements IndirectActorProducer {
    final Injector injector;
    final Class<? extends Actor> actorClass;
    public GuiceInjectedActor(Injector injector, Class<? extends Actor> actorClass){
        this.injector = injector;
        this.actorClass = actorClass;
    }

    public static Props props(Injector injector, Class<? extends Actor> actorClass){
        return Props.create(GuiceInjectedActor.class,injector,actorClass);
    }

    @Override
    public Actor produce() {
        return injector.getInstance(actorClass);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }

}
