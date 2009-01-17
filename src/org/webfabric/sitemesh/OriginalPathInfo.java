package org.webfabric.sitemesh;

import javax.servlet.http.HttpServletRequest;

class OriginalPathInfo implements Path {
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
