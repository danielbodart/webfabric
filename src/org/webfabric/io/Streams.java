package org.webfabric.io;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Streams {
    public static String asString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[512];
        int read = 0;
        while( (read = reader.read(buffer) ) > 0 ){
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }
}
