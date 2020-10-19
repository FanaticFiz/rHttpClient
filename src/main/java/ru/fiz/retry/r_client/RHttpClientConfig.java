package ru.fiz.retry.r_client;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RHttpClientConfig {

    private final int maxAttempts;
    private final long waitDuration;
    private final Set<Integer> retryCodes;

    RHttpClientConfig(int maxAttempts, long waitDuration, Set<Integer> retryCodes) {
        this.maxAttempts = maxAttempts;
        this.waitDuration = waitDuration;

        this.retryCodes = Objects.requireNonNullElseGet(retryCodes, () -> Set.of(408, 429, 500, 502, 503, 504));
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

    public Set<Integer> getRetryCodes() {
        return this.retryCodes;
    }

    public String toString() {
        return "RHttpClientConfig(" +
                "maxAttempts=" + this.getMaxAttempts() + ", " +
                "waitDuration=" + this.getWaitDuration() + ", " +
                "retryCodes=" + this.getRetryCodes() + ")";
    }

    public static class RHttpClientConfigBuilder {
        private int maxAttempts;
        private long waitDuration;
        private Set<Integer> retryCodes = new HashSet<>();

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

        /**
         * Задает новый набор кодов.
         *
         * @param retryCodes Список http кодов
         */
        public RHttpClientConfig.RHttpClientConfigBuilder defineRetryCodes(@NotNull Set<Integer> retryCodes) {
            this.retryCodes = retryCodes;

            return this;
        }

        /**
         * Добавляет коды к существующему набору.
         *
         * @param retryCodes Список http кодов
         */
        public RHttpClientConfig.RHttpClientConfigBuilder addRetryCodes(@NotNull Set<Integer> retryCodes) {
            this.retryCodes.addAll(retryCodes);

            return this;
        }

        public RHttpClientConfig build() {
            return new RHttpClientConfig(maxAttempts, waitDuration, retryCodes);
        }
    }
}
