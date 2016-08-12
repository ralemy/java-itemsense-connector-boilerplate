package com.impinj.auth;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to represent API errors in RTLS interface.
 */

public class ImpinjApiException extends Exception {
    public int status;

    public ImpinjApiException(int status, String message) {
        super(message);
        this.status = status;
    }

    public ImpinjApiException(Response.Status status, String message) {
        this(status.getStatusCode(), message);
    }

    public ImpinjApiException(CloseableHttpResponse response) throws IOException {
        this(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
    }
}
