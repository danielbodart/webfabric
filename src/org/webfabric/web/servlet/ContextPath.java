package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class ContextPath implements Path {
    private final String value;

    public ContextPath(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ContextPath create(HttpServletRequest request) {
        return new ContextPath(request.getContextPath());
    }

    @Override
    public String toString() {
        return value;
    }
}
