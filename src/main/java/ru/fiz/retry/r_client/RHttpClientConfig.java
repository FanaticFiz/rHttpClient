package ru.fiz.retry.r_client;

import java.util.List;

public class RHttpClientConfig {

    private final int maxAttempts;
    private final long waitDuration;
    private final List<Integer> retryCodes;

    public RHttpClientConfig(int maxAttempts, long waitDuration, List<Integer> retryCodes) {
        this.maxAttempts = maxAttempts;
        this.waitDuration = waitDuration;
        this.retryCodes = retryCodes;
    }

    public RHttpClientConfig() {
        maxAttempts = 3;
        waitDuration = 3000L;
        retryCodes = List.of(408, 429, 500, 502, 503, 504);
    }

    public static RHttpClientConfigBuilder builder() {
        return new RHttpClientConfigBuilder();
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public long getWaitDuration() {
        return this.waitDuration;
    }

    public List<Integer> getRetryCodes() {
        return this.retryCodes;
    }

    public String toString() {
        return "RHttpClientConfig.(" +
                "maxAttempts=" + this.maxAttempts + ", " +
                "waitDuration=" + this.waitDuration + ", " +
                "retryCodes=" + this.retryCodes.toString() + ")";
    }

    public static class RHttpClientConfigBuilder {
        private int maxAttempts;
        private long waitDuration;
        private List<Integer> retryCodes;

        RHttpClientConfigBuilder() {
        }

        public RHttpClientConfig.RHttpClientConfigBuilder maxAttempts(int maxAttempts) {
            if (maxAttempts < 1) {
                throw new IllegalArgumentException("maxAttempts must be greater than or equal to 1");
            } else {
                this.maxAttempts = maxAttempts;
                return this;
            }
        }

        public RHttpClientConfig.RHttpClientConfigBuilder waitDuration(long waitDuration) {
            if (waitDuration < 1) {
                throw new IllegalArgumentException("waitDurationInOpenState must be a positive value");
            } else {
                this.waitDuration = waitDuration;
                return this;
            }
        }

        public RHttpClientConfig.RHttpClientConfigBuilder retryCodes(List<Integer> retryCodes) {
            this.retryCodes = retryCodes;
            return this;
        }

        public RHttpClientConfig build() {
            return new RHttpClientConfig(maxAttempts, waitDuration, retryCodes);
        }
    }
}
