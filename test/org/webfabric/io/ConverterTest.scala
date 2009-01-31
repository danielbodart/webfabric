package org.webfabric.io

import org.junit.Test
import org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

class ConverterTest {
    @Test
    def convertsInputStreamToString{
        // setup
        val value = "foo";
        val stream = new ByteArrayInputStream(value.getBytes());

        // execute
        val all = Converter.asString(stream);

        // verify
        assertEquals(all, value);
    }

    @Test
    def convertsReaderToString{
        // setup
        val value = "foo";
        val reader = new StringReader(value);

        // execute
        val all = Converter.asString(reader);

        // verify
        assertEquals(all, value);
    }

}
