package com.impinj.rtls.auth;

import io.dropwizard.auth.Authorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this class is to decide if a user is authorized to access a resource
 * limited for a role.
 */
public class ApiAuthorizer implements Authorizer<ApiUser> {
    private final static Logger log = LoggerFactory.getLogger(ApiAuthorizer.class);
    @Override
    public boolean authorize(ApiUser principal, String role) {
        log.info("Authorizing {} for {}", principal.getName(),role);
        //Todo: replace with real implementation of authorization.
        return true;
    }
}
