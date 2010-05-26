package org.webfabric.properties;

public interface Presenter<Request, Response> {
    void present(Request request, Response response);
}
