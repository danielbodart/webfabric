package org.webfabric.stringtemplate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.webfabric.io.RelativeResourceLoader;

import java.io.InputStream;

public class EnhancedStringTemplateGroupTest {
    @Test
    public void SupportsLoadingResources() throws Exception{
        // setup
        InputStream stream = new RelativeResourceLoader(EnhancedStringTemplateGroupTest.class).load("resource.st");

        assertNotNull(stream);
        assertTrue(stream.available() > 0);
        // expectations

        // execute

        // verify

    }

}
