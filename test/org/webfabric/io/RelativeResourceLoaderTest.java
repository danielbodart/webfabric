package org.webfabric.io;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.webfabric.io.Streams.asString;

import java.io.InputStream;

public class RelativeResourceLoaderTest {
    @Test
    public void supportsLoadingAsStream() throws Exception{
        // setup
        RelativeResourceLoader resourceLoader = new RelativeResourceLoader(RelativeResourceLoaderTest.class);

        // execute
        InputStream stream = resourceLoader.load("relative.file");

        // verify
        assertNotNull(stream);
        assertEquals("test file", asString(stream));
    }
}
