package org.webfabric.io;

import java.io.*;

public class Streams {
    public static String asString(InputStream stream) throws IOException {
        return asString(new InputStreamReader(stream));
    }

    public static String asString(Reader reader) throws IOException {
        BufferedReader buffered = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[512];
        int read = 0;
        while( (read = buffered.read(buffer) ) > 0 ){
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }
}
