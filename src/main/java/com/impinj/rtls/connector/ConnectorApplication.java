package com.impinj.rtls.connector;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.impinj.auth.ApiAuthenticator;
import com.impinj.auth.ApiAuthorizer;
import com.impinj.auth.ApiUser;
import com.impinj.actor.GuiceInjectedActor;
import com.impinj.rtls.connector.incoming_amqp.QueueConsumer;
import com.impinj.rtls.connector.app.config.ConnectorConfiguration;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ralemy on 8/8/16.
 * Main application for connector
 */
public class ConnectorApplication extends Application<ConnectorConfiguration> {
    public static final Logger log = LoggerFactory.getLogger(ConnectorApplication.class);
    GuiceBundle<ConnectorConfiguration> bundle;

    public static void main(final String[] args) throws Exception {
        new ConnectorApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ConnectorConfiguration> bootstrap) {
        bundle = GuiceBundle.<ConnectorConfiguration>newBuilder()
                .addModule(new GuiceModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ConnectorConfiguration.class)
                .build();
        bootstrap.addBundle(bundle);
    }

    @Override
    public void run(final ConnectorConfiguration config, final Environment env) throws Exception {
        registerAuthentication(env.jersey());
        ActorSystem system = bundle.getInjector().getInstance(ActorSystem.class);
        ActorRef consumer = system.actorOf(GuiceInjectedActor.props(bundle.getInjector(), QueueConsumer.class),"ItemSenseConsumer");
        consumer.tell(new QueueConsumer.Start(),null);

    }

    private void registerAuthentication(JerseyEnvironment jersey) {
        jersey.register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<ApiUser>()
                .setAuthenticator(new ApiAuthenticator())
                .setAuthorizer(new ApiAuthorizer())
                .setRealm("IMPINJ RTLS Connector")
                .buildAuthFilter()
        ));
        jersey.register(RolesAllowedDynamicFeature.class);
        jersey.register(new AuthValueFactoryProvider.Binder<>(ApiUser.class));
    }


}
