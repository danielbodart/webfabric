package org.webfabric.sitemesh;

import javax.servlet.http.HttpServletRequest;

public class PathInfo implements Path {
    private final String value;

    public PathInfo(HttpServletRequest request) {
        this.value = request.getPathInfo();
    }

    public PathInfo(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
