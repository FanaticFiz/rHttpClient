package ru.fiz.retry.r_client;

import okhttp3.Response;

import java.io.IOException;

public class RHttpClientResponseModel {

    private int code;
    private String msg;
    private String body;
    private boolean successful;

    public RHttpClientResponseModel(Response response) throws IOException {
        this.body = response.body() != null ? response.body().string() : null;
        this.code = response.code();
        this.msg = response.message();
        this.successful = response.isSuccessful();
    }

    public RHttpClientResponseModel(int code, String msg, String body, boolean successful) {
        this.code = code;
        this.msg = msg;
        this.body = body;
        this.successful = successful;
    }

    public RHttpClientResponseModel() {
    }

    public static RHttpClientResponseModelBuilder builder() {
        return new RHttpClientResponseModelBuilder();
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getBody() {
        return this.body;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String toString() {
        return "RHttpClientResponseModel(code=" + this.getCode() +
                ", msg=" + this.getMsg() +
                ", body=" + this.getBody() +
                ", successful=" + this.isSuccessful() + ")";
    }

    public static class RHttpClientResponseModelBuilder {
        private int code;
        private String msg;
        private String body;
        private boolean successful;

        RHttpClientResponseModelBuilder() {
        }

        public RHttpClientResponseModel.RHttpClientResponseModelBuilder code(int code) {
            this.code = code;
            return this;
        }

        public RHttpClientResponseModel.RHttpClientResponseModelBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public RHttpClientResponseModel.RHttpClientResponseModelBuilder body(String body) {
            this.body = body;
            return this;
        }

        public RHttpClientResponseModel.RHttpClientResponseModelBuilder successful(boolean successful) {
            this.successful = successful;
            return this;
        }

        public RHttpClientResponseModel build() {
            return new RHttpClientResponseModel(code, msg, body, successful);
        }
    }
}
