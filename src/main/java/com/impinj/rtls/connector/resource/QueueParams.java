package com.impinj.rtls.connector.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to represent input to the /rtls/register endpoint.
 */
public class QueueParams {
        @JsonProperty
        private int heartbeat;
        @JsonProperty
        private String areaId;

        public QueueParams(String heartbeat, String areaId) throws ImpinjRTLSApiException {
            if (heartbeat != null)
                try {
                    this.heartbeat = Integer.parseInt(heartbeat);
                } catch (NumberFormatException e) {
                    throw new ImpinjRTLSApiException(Response.Status.BAD_REQUEST, "Heartbeat is not an integer");
                }
            else
                this.heartbeat = 0;
            this.areaId = areaId;
        }

        public QueueParams() throws ImpinjRTLSApiException {
            this(null,null);
        }

    public int getHeartbeat() {
        return heartbeat;
    }

    public String getAreaId() {
        return areaId;
    }
}
