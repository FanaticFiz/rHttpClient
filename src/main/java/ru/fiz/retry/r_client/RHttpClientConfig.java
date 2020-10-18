package ru.fiz.retry.r_client;

public class RHttpClientConfig {

    private final int maxAttempts;
    private final long waitDuration;
    private int[] successCodes;
    private int[] retryCodes;

    public RHttpClientConfig(int maxAttempts, long waitDuration, int[] successCodes, int[] retryCodes) {
        this.maxAttempts = maxAttempts;
        this.waitDuration = waitDuration;
        this.successCodes = successCodes;
        this.retryCodes = retryCodes;
    }

    public RHttpClientConfig() {
        maxAttempts = 3;
        waitDuration = 3000L;
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

    public int[] getSuccessCodes() {
        return this.successCodes;
    }

    public int[] getRetryCodes() {
        return this.retryCodes;
    }

    public static class RHttpClientConfigBuilder {
        private int maxAttempts;
        private long waitDuration;
        private int[] successCodes;
        private int[] retryCodes;

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

        public RHttpClientConfig.RHttpClientConfigBuilder successCodes(int[] successCodes) {
            this.successCodes = successCodes;
            return this;
        }

        public RHttpClientConfig.RHttpClientConfigBuilder retryCodes(int[] retryCodes) {
            this.retryCodes = retryCodes;
            return this;
        }

        public RHttpClientConfig build() {
            return new RHttpClientConfig(maxAttempts, waitDuration, successCodes, retryCodes);
        }

        public String toString() {
            return "RHttpClientConfig.RHttpClientConfigBuilder(maxAttempts=" + this.maxAttempts + ", " +
                    "waitDuration=" + this.waitDuration + ", " +
                    "successCodes=" + java.util.Arrays.toString(this.successCodes) + ", " +
                    "retryCodes=" + java.util.Arrays.toString(this.retryCodes) + ")";
        }
    }
}
