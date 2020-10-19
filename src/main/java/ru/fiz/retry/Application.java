package ru.fiz.retry;

import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fiz.retry.r_client.RClientException;
import ru.fiz.retry.r_client.RHttpClient;
import ru.fiz.retry.r_client.RHttpClientConfig;
import ru.fiz.retry.r_client.RHttpClientResponseModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Application {

    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws MalformedURLException, RClientException {
        RHttpClientConfig clientConfig = RHttpClientConfig.builder()
                                                          .maxAttempts(3)
                                                          .waitDuration(1000)
                                                          .build();

        RHttpClient httpClient = new RHttpClient(clientConfig);

        Request request = new Request.Builder()
                .url(new URL("http://localhost:8082/actuator/health3"))
                .get()
                .build();

        final RHttpClientResponseModel response = httpClient.execute(request);

        log.info("result is: {}", response);

        // Create a RetryRegistry with a custom global configuration
        RetryConfig config = RetryConfig.custom()
                                        .maxAttempts(3)
                                        .waitDuration(Duration.ofMillis(100))
                                        .build();
        RetryRegistry registry = RetryRegistry.of(config);
    }
}
