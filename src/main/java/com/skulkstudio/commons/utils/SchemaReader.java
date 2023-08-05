package com.skulkstudio.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * Read a schema file
 * @since v1.0-SNAPSHOT
 */
public final class SchemaReader {
    private SchemaReader() {}

    /**
     * Gets the statements present in a schema file
     * @param is The resource {@link InputStream} of the schema file
     * @return The list of statements present in the schema file.
     * @throws IOException Thrown if the schema file cannot be read.
     */
    public static List<String> getStatements(InputStream is) throws IOException {
        List<String> queries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }

                sb.append(line);

                // check for end of declaration
                if(line.endsWith(";")) {
                    String result = sb.toString().trim();
                    if(!result.isEmpty()) {
                        queries.add(result);
                    }

                    // reset
                    sb = new StringBuilder();
                }
            }
        }

        return queries;
    }
}
