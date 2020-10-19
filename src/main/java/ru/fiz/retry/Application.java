package ru.fiz.retry;

import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fiz.retry.r_client.RClientException;
import ru.fiz.retry.r_client.RHttpClient;
import ru.fiz.retry.r_client.RHttpClientConfig;
import ru.fiz.retry.r_client.RHttpClientResponseModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class Application {

    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws MalformedURLException, RClientException {
        RHttpClientConfig config = RHttpClientConfig.builder()
                                                    .maxAttempts(2)
                                                    .waitDuration(1000)
                                                    .addRetryCodes(Set.of(401))
                                                    .build();

        RHttpClient httpClient = new RHttpClient(config);

        Request request = new Request.Builder()
                .url(new URL("http://localhost:8082/actuator/health1"))
                .get()
                .build();

        final RHttpClientResponseModel response = httpClient.execute(request);

        log.info("result is: {}", response);
    }
}
