package org.webfabric.properties;

public interface Command<Request, Response> {
    void execute(Request request, Response response);
}
