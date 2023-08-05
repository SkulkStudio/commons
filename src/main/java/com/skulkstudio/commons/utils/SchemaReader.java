package com.skulkstudio.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class SchemaReader {
    private SchemaReader() {}

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