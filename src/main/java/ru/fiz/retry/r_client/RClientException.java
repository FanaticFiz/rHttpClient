package ru.fiz.retry.r_client;

public class RClientException extends Exception {

    RClientException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    RClientException(String msg) {
        super(msg);
    }
}
