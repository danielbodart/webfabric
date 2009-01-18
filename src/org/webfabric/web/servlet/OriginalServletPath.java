package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class OriginalServletPath implements Path {
    private final String value;

    public OriginalServletPath(String value) {
        this.value = value;
    }

    public static OriginalServletPath create(HttpServletRequest request) {
        return new OriginalServletPath((String) request.getAttribute("javax.servlet.include.servlet_path"));
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}
