package com.tuliohdev.myapplication.withoutrx;

public class ResponseCallback<T> {

    private T response;
    private Throwable error;

    public ResponseCallback(T response) {
        this.response = response;
    }

    public ResponseCallback(Throwable error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public Throwable getError() {
        return error;
    }
}
