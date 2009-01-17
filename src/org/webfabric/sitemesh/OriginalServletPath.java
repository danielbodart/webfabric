package org.webfabric.sitemesh;

import javax.servlet.http.HttpServletRequest;

class OriginalServletPath implements Path {
    private final String value;

    public OriginalServletPath(HttpServletRequest request) {
        this.value = (String) request.getAttribute("javax.servlet.include.servlet_path");
    }

    public OriginalServletPath(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
