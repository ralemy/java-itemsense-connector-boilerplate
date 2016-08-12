package com.impinj.rtls.connector.area;

import com.codahale.metrics.annotation.Timed;
import com.impinj.auth.ApiUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ralemy on 8/11/16.
 * The responsibility of this class is to implement the RESTFul endpoints
 * For CRUD operations on Locate Area hierarchy
 */

@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
public class AreaResource {
    @GET
    @Timed
    public Response getAreaModel(@Auth ApiUser user){

        return Response.ok("{}").build();
    }
}
