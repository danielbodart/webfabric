package org.webfabric.rest;

import com.googlecode.yadic.Container;

public interface Module {
    Module addPerRequestObjects(Container container);
    Module addPerApplicationObjects(Container container);
    Module addResources(RestEngine engine);
}
