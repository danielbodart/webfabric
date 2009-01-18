package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class PathInfo implements Path {
    private final String value;

    public PathInfo(String value) {
        this.value = value;
    }

    public static PathInfo create(HttpServletRequest request) {
        return new PathInfo(request.getPathInfo());
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}
