package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

public class OriginalPathInfo implements Path {
    private final String value;

    public OriginalPathInfo(HttpServletRequest request) {
        this.value = (String) request.getAttribute("javax.servlet.include.servlet_info");
    }

    public OriginalPathInfo(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
