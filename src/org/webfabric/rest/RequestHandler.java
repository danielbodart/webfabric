package org.webfabric.rest;

public interface RequestHandler {
    void handle(Request request, Response response);
}
