package org.webfabric.io;

import java.io.InputStream;

public class RelativeResourceLoader {
    private final Class aClass;

    public RelativeResourceLoader(Class aClass) {
        this.aClass = aClass;
    }

    public InputStream load(String relativeFilename) {
        String name = aClass.getPackage().getName();
        name = name.replace(".", "/");
        String resource = name + "/" + relativeFilename;
        return aClass.getClassLoader().getResourceAsStream(resource);
    }
}
