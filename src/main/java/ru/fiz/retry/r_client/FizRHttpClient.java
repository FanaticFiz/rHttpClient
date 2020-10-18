package ru.fiz.retry.r_client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class FizRHttpClient {

    public static final Logger log = LoggerFactory.getLogger(FizRHttpClient.class);

    private final OkHttpClient client = new OkHttpClient();
    private final RHttpClientConfig config;

    public FizRHttpClient() {
        this.config = new RHttpClientConfig();
    }

    public FizRHttpClient(RHttpClientConfig config) {
        this.config = config;
    }

    public void execute(Request request) throws FizRClientException {
        int currentAttempt = 0;
        do {
            log.debug("Attempt: {}", currentAttempt);
            currentAttempt++;

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    log.info("Request successfully executed");

                    RHttplClientResponseModel model = RHttplClientResponseModel
                            .builder()
                            .code(response.code())
                            .body(response.body().string())
                            .build();
                    log.debug("Request success: {}", model.toString());

                    return;
                }

                RHttplClientResponseModel model = RHttplClientResponseModel
                        .builder()
                        .code(response.code())
                        .body(response.body().string())
                        .build();
                log.warn("Request failed: {}", model.toString());

                sleep(config.getWaitDuration());
            } catch (IOException | InterruptedException e) {
                log.error("Something went wrong: {}", e.getMessage());
            }
        } while (currentAttempt < config.getMaxAttempts());

        throw new FizRClientException("Request not successful. Retry count: " + config.getMaxAttempts());
    }
}
