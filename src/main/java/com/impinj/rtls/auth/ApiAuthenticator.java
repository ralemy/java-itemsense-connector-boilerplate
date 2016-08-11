package com.impinj.rtls.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by ralemy on 8/9/16.
 *
 * basic authentication for api.
 * The responsibility of this class is to provide a Principal if the authentication succeeds.
 */
public class ApiAuthenticator implements Authenticator<BasicCredentials,ApiUser> {

    static final Logger log = LoggerFactory.getLogger(ApiAuthenticator.class);
    @Override
    public Optional<ApiUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        log.info("Attempting authentication with {} , {}", credentials.getUsername(), credentials.getPassword());
        //ToDo: implement here the service to check the username and password.
        return "admin".equals(credentials.getPassword())
                ? Optional.empty()
                : Optional.of(new ApiUser(credentials.getUsername(),credentials.hashCode()));
    }
}
