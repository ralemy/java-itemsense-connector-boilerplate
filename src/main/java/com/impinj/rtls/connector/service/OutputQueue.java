package com.impinj.rtls.connector.service;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import com.impinj.rtls.connector.actors.QueueProvider;
import com.impinj.rtls.connector.config.RabbitMqConfiguration;
import com.impinj.rtls.connector.resource.QueueParams;
import com.impinj.rtls.connector.resource.QueueResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to register and return a queue from downstream rabbitMQ server.
 */


public class OutputQueue {

    private final RabbitMqConfiguration config;
    private static final Logger log = LoggerFactory.getLogger(OutputQueue.class);
    private final ActorSystem system;

    @Inject
    public OutputQueue(ActorSystem system, RabbitMqConfiguration config){
        this.config = config;
        this.system = system;

    }

    public QueueResponse register(String areaId, int heartbeat) throws IOException, TimeoutException {
        log.info("registering {} on {} with {} heartbeat", areaId,config.getHost(),heartbeat);
        String queue = createRabbitActor(areaId,heartbeat);
        return new QueueResponse(config.getHost(),queue);
    }

    private String createRabbitActor(String areaId, int heartbeat) throws IOException, TimeoutException {
        QueueProvider.StartQueue queue = new QueueProvider.StartQueue(areaId,heartbeat);
        system.actorSelection("akka://ImpinjRTLSActors/user/RTLSQueue").tell(queue,null);
        return queue.getQueueId();
    }

    public QueueResponse register(QueueParams params) throws IOException, TimeoutException {
        return register(params.getAreaId(), params.getHeartbeat());
    }
}
