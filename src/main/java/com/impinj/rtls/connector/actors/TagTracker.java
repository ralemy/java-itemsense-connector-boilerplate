package com.impinj.rtls.connector.actors;

import akka.actor.UntypedActor;
import com.impinj.rtls.connector.service.QueueInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this actor is to process information about a single EPC
 */
public class TagTracker extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(TagTracker.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof QueueInput)
            processMessage((QueueInput) message);
        else
            unhandled(message);
    }

    private void processMessage(QueueInput message) {
        log.info(message.toString());
        log.info(context().system().actorSelection("akka://ImpinjRTLSActors/user/RTLSQueue").toString());
    }

}
