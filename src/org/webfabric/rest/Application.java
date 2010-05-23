package org.webfabric.rest;

public interface Application {
    void handle(Request request, Response response);
}
