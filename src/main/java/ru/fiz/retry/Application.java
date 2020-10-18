package ru.fiz.retry;

import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import okhttp3.Request;
import ru.fiz.retry.r_client.FizRClientException;
import ru.fiz.retry.r_client.FizRHttpClient;
import ru.fiz.retry.r_client.RHttpClientConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Application {

    public static void main(String[] args) throws MalformedURLException, FizRClientException {
        RHttpClientConfig clientConfig = RHttpClientConfig.builder()
                                                          .maxAttempts(5)
                                                          .waitDuration(3000)
                                                          .build();

        FizRHttpClient httpClient = new FizRHttpClient(clientConfig);

        Request request = new Request.Builder()
                .url(new URL("http://localhost:8082/actuator/health3"))
                .get()
                .build();

        httpClient.execute(request);



// Create a RetryRegistry with a custom global configuration
        RetryConfig config = RetryConfig.custom()
                                        .maxAttempts(3)
                                        .waitDuration(Duration.ofMillis(100))
                                        .build();
        RetryRegistry registry = RetryRegistry.of(config);
    }
}
