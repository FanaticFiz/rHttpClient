package ru.fiz.retry.r_client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class RHttpClient {

    public static final Logger log = LoggerFactory.getLogger(RHttpClient.class);

    private final OkHttpClient client = new OkHttpClient();
    private final RHttpClientConfig config;

    public RHttpClient() {
        this.config = new RHttpClientConfig();
    }

    public RHttpClient(RHttpClientConfig config) {
        this.config = config;
    }

    public RHttpClientResponseModel execute(Request request) throws RClientException {
        RHttpClientResponseModel responseModel;

        int currentAttempt = 0;
        do {
            log.debug("Attempt: {}", currentAttempt);

            currentAttempt++;
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    log.debug("Request successfully executed");

                    return new RHttpClientResponseModel(response);
                }

                if (config.getRetryCodes().contains(response.code())) {
                    responseModel = new RHttpClientResponseModel(response);

                    log.debug("Request failed: {}", responseModel.toString());

                    sleep(config.getWaitDuration());
                } else {
                    return new RHttpClientResponseModel(response);
                }
            } catch (IOException | InterruptedException e) {
                // Restore interrupted state...
                Thread.currentThread().interrupt();

                throw new RClientException("Something went wrong: {}" + e.getMessage());
            }
        } while (currentAttempt < config.getMaxAttempts());

        return responseModel;
    }
}
