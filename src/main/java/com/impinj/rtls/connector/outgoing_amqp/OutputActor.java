package com.impinj.rtls.connector.outgoing_amqp;

import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impinj.rtls.connector.app.config.ConnectorConfiguration;
import com.impinj.rtls.connector.app.config.RabbitMqConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ralemy on 8/10/16.
 * The responsibility of this actor is to broker all active output queues
 */
public class OutputActor extends UntypedActor {

    private final RabbitMqConfiguration config;
    private final Channel channel;
    private final ObjectMapper mapper;
    private String queueId;

    @Inject
    public OutputActor(ConnectorConfiguration config) throws IOException, TimeoutException {
        this.config = config.getRabbitMqConfiguration();
        this.channel = createChannel();
        this.mapper = new ObjectMapper();
    }

    private Channel createChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.getHost());
        factory.setUsername(config.getUser());
        factory.setPassword(config.getPassword());
        Connection broker = factory.newConnection();
        return broker.createChannel();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof StartQueue)
            processMsg((StartQueue) message);
        if(message instanceof OutputModel)
            processMsg((OutputModel) message);
        else
            unhandled(message);
    }

    private void processMsg(OutputModel message) throws IOException {
        if(this.queueId != null)
            channel.basicPublish("",this.queueId,null,mapper.writeValueAsBytes(message));
    }

    private void processMsg(StartQueue message) throws IOException {
        this.queueId = message.getQueueId();
        channel.queueDeclare(message.getQueueId(),false,false,false,null);
    }

    public static class StartQueue{
        private final int heartbeat;
        private final String areaId;
        private final String queueId;

        public StartQueue(String areaId, int heartbeat){
            this.areaId = areaId;
            this.heartbeat = heartbeat;
            String area = areaId == null ? "HierarchyRoot" : areaId;
            this.queueId = String.format("ImpinjRTLSQueue%s%d",area,heartbeat);
        }

        public String getAreaId() {
            return areaId;
        }

        public int getHeartbeat() {
            return heartbeat;
        }
        public String getQueueId(){
            return queueId;
        }
    }
}
