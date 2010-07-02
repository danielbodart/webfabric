package org.webfabric.rest;

import com.googlecode.yadic.Container;

public interface Application extends RequestHandler{
    Container createRequestScope();
    Application add(Module module);
    Application addInstance(Object instance);

}
