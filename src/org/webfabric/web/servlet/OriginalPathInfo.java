package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class OriginalPathInfo implements Path {
    private final String value;

    public OriginalPathInfo(String value) {
        this.value = value;
    }

    public static OriginalPathInfo create(HttpServletRequest request) {
        return new OriginalPathInfo((String) request.getAttribute("javax.servlet.include.servlet_info"));
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
