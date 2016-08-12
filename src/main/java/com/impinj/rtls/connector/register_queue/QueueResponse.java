package com.impinj.rtls.connector.register_queue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to marshall Json response for queue creation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueResponse {
    private final static Logger log = LoggerFactory.getLogger(QueueResponse.class);
    @JsonProperty
    private String serverUrl;
    @JsonProperty
    private String queue;

    @JsonCreator
    public QueueResponse(@JsonProperty("serverUrl") String someUrl,
                         @JsonProperty("queue") String  somequeue) {
        this.serverUrl = someUrl;
        this.queue = somequeue;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
