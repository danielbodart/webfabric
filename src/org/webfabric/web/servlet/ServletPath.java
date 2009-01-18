package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class ServletPath implements Path {
    private final String value;

    public ServletPath(String value) {
        this.value = value;
    }

    public static ServletPath create(HttpServletRequest request) {
        return new ServletPath(request.getServletPath());
    }

    public String value() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
