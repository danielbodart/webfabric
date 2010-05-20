package org.webfabric.rest;

public interface Extractor<T,S> {
    S extract(T t);
}
