package com.impinj.rtls.connector.resource;

import com.codahale.metrics.annotation.Timed;
import com.impinj.rtls.auth.ApiUser;
import com.impinj.rtls.connector.config.ConnectorConfiguration;
import com.impinj.rtls.connector.config.RabbitMqConfiguration;
import com.impinj.rtls.connector.service.ItemsenseApi;
import com.impinj.rtls.connector.service.OutputQueue;
import com.rabbitmq.client.QueueingConsumer;
import io.dropwizard.auth.Auth;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to implement /rtls/register endpoint.
 */

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)

public class QueueResource {
    private final Logger log = LoggerFactory.getLogger(QueueResource.class);
    private final RabbitMqConfiguration config;
    private final OutputQueue rabbitServer;
    private final ItemsenseApi itemsenseApi;

    @Inject
    public QueueResource(ConnectorConfiguration config, OutputQueue rabbitServer, ItemsenseApi itemsenseApi){
        this.config = config.getRabbitMqConfiguration();
        this.rabbitServer = rabbitServer;
        this.itemsenseApi = itemsenseApi;
    }

    @GET
    @Timed
    public Response registerQueue(@QueryParam("heartbeat") String heartbeat, @QueryParam("areaid") String areaId) {
        try {
            QueueParams params = new QueueParams(heartbeat,areaId);
            return Response.ok(rabbitServer.register(params)).build();
        } catch (ImpinjRTLSApiException e) {
            return Response.status(e.status).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @GET
    @Timed
    @Path("/secure")
    @RolesAllowed("Manager")
    public Response secureEndpoint(@Auth ApiUser user){
        return Response.ok().build();
    }

    @GET
    @Timed
    @Path("/registerToItemSense")
    public Response protectedEndpoint(@Auth ApiUser user){
        try {
            QueueResponse resp = itemsenseApi.doPost("itemsense/data/v1/messageQueues/zoneTransition/configure",new StringEntity("{}"), QueueResponse.class);
            return Response.ok(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Timed
    @Path("/super")
    @RolesAllowed("Admin")
    public Response superSecureEndpoint(@Auth ApiUser user){
        return Response.ok().build();
    }

}
