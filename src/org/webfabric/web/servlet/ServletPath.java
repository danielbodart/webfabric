package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class ServletPath implements Path {
    private final String value;

    public ServletPath(HttpServletRequest request) {
        this.value = request.getServletPath();
    }

    public ServletPath(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
