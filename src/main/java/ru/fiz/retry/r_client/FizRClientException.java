package ru.fiz.retry.r_client;

public class FizRClientException extends Exception {

    FizRClientException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    FizRClientException(String msg) {
        super(msg);
    }
}
