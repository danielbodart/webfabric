package org.webfabric.rest;

public interface RequestExtractor<T> extends Extractor<Request, T>, Matcher<Request> {
}
