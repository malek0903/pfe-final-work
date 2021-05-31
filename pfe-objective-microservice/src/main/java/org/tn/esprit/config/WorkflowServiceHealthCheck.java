package org.tn.esprit.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Provider;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Liveness
@ApplicationScoped
public class WorkflowServiceHealthCheck implements HealthCheck {

    @ConfigProperty(name = "workflow-service.url", defaultValue = "false")
    Provider<String> workflowServiceUrl;

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder =
                HealthCheckResponse.named("Workflow Service connection health check");

        try {
            workflowServiceConnectionVerification();
            responseBuilder.up();
        } catch (IllegalStateException e) {
            responseBuilder.down().withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }

    private void workflowServiceConnectionVerification() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(3000))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(workflowServiceUrl.get() + "/health"))
                .build();

        HttpResponse<String> response = null;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("IOException", e);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
            Thread.currentThread().interrupt();
        }

        if (response == null || response.statusCode() != 200) {
            throw new IllegalStateException("Cannot contact workflow Service");
        }
    }
}
