package org.webfabric.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

public class StreamsTest {
    @Test
    public void convertsInputStreamToString() throws Exception{
        // setup
        String value = "foo";
        InputStream stream = new ByteArrayInputStream(value.getBytes());

        // execute
        String all = Streams.asString(stream);

        // verify
        assertEquals(all, value);
    }

    @Test
    public void convertsReaderToString() throws Exception{
        // setup
        String value = "foo";
        StringReader reader = new StringReader(value);

        // execute
        String all = Streams.asString(reader);

        // verify
        assertEquals(all, value);
    }

}
