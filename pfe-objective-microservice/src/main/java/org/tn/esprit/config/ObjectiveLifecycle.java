package org.tn.esprit.config;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tn.esprit.client.ConsulRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class ObjectiveLifecycle {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ObjectiveLifecycle.class);

    String instanceId;

    @ConfigProperty(name = "quarkus.application.name")
    String appName;

    @ConfigProperty(name = "vertx-consul-client.url")
    String consulUri;

    @ConfigProperty(name = "objective-service.address")
    String appAddress;




    @Inject
    @RestClient
    ConsulRestClient consulRestClient;

    void onStart(@Observes StartupEvent ev) {
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulUri);
            ConsulClient client = ConsulClient.create(Vertx.vertx(),options);

            instanceId = appName + "-" ;
            ServiceOptions opts = new ServiceOptions()
                    .setName(appName)
                    .setId(instanceId)
                    .setTags(Arrays.asList("objective", "ms"))
                    .setAddress(appAddress)
                    .setPort(8084);
            client.registerService(opts, res -> {
                if (res.succeeded()) {
                    System.out.println("Service successfully registered");
                } else {
                    res.cause().printStackTrace();
                    System.out.println("Service ezfsdfsdfsdfsdfdsfdsfdsffsd registered");
                }
            });
        }, 5000, TimeUnit.MILLISECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        consulRestClient.serviceDeregister(instanceId);
        LOGGER.info("Instance de-registered: id={}", instanceId);
    }
}
