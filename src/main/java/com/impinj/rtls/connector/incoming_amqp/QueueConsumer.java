package com.impinj.rtls.connector.incoming_amqp;

import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.impinj.actor.GuiceInjectedActor;
import com.impinj.rtls.connector.transformation.TagTracker;
import com.impinj.rtls.connector.app.config.ConnectorConfiguration;
import com.impinj.rtls.connector.register_queue.QueueResponse;
import com.impinj.rtls.connector.service.ItemsenseApi;
import com.rabbitmq.client.*;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this actor is to receive messages from Itemsense
 */
public class QueueConsumer extends UntypedActor {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final Connection broker;
    private final Channel channel;
    private final Injector injector;
    private ObjectMapper mapper;

    @Inject
    public QueueConsumer(ItemsenseApi api, ConnectorConfiguration config, Injector injector) throws Exception {
        this.mapper = new ObjectMapper();
        this.injector = injector;
        QueueResponse r = api.doPost(ItemsenseApi.REGISTER_QUEUE, new StringEntity("{}"), QueueResponse.class);
        this.broker = getAmqpBroker(r.getServerUrl(), config.getItemsenseConfig().getUsername(), config.getItemsenseConfig().getPassword());
        this.channel = broker.createChannel();
        addMessageListener(channel, r.getQueue());
        log.info("----->>>>>>> {}", self().path().toString());
    }

    private void addMessageListener(Channel channel, String queue) throws IOException {
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) {
                log.info("message received");
                try {
                    InputModel msg = mapper.readValue(body, InputModel.class);
                    process(msg);
                } catch (Exception e) {
                    log.info("Exception in message: {}" , e.getMessage());
                }
            }
        });
    }

    private void process(InputModel msg) {
        try {
            context().actorOf(GuiceInjectedActor.props(injector, TagTracker.class), msg.getEpc());
            log.info("created for epc {}",msg.getEpc());
        } catch (Exception e) {
            log.info("exists. just send epc {}", msg.getEpc());
        }
        context().actorSelection(msg.getEpc()).tell(msg, self());
    }

    private Connection getAmqpBroker(String url, String username, String password) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory.newConnection();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("received message {} instanceof {}", message.toString(), message.getClass().getName());
        if (message instanceof Start)
            processMessage((Start) message);
        else
            unhandled(message);
    }

    private void processMessage(Start message) {
        log.info("actor queue consumer received : {}", message);
    }

    public static class Start {
    }

}
