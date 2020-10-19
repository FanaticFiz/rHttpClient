package ru.fiz.retry;

import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fiz.retry.r_client.RClientException;
import ru.fiz.retry.r_client.RHttpClient;
import ru.fiz.retry.r_client.RHttpClientResponseModel;

import java.net.MalformedURLException;
import java.net.URL;

public class Application {

    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws MalformedURLException, RClientException {
        RHttpClient httpClient = new RHttpClient();

        Request request = new Request.Builder()
                .url(new URL("http://localhost:8082/actuator/health"))
                .get()
                .build();

        final RHttpClientResponseModel response = httpClient.execute(request);

        log.info("result is: {}", response);
    }
}
