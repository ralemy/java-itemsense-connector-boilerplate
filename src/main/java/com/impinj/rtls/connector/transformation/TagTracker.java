package com.impinj.rtls.connector.transformation;

import akka.actor.UntypedActor;
import com.impinj.rtls.connector.incoming_amqp.InputModel;
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
        if(message instanceof InputModel)
            processMessage((InputModel) message);
        else
            unhandled(message);
    }

    private void processMessage(InputModel message) {
        log.info(message.toString());
        log.info(context().system().actorSelection("akka://ImpinjRTLSActors/user/RTLSQueue").toString());
    }

}
