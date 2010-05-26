package org.webfabric.rest;

public interface Matcher<T> {
    boolean isMatch(T t);
}
