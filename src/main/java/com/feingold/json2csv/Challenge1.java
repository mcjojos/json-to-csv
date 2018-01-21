package com.feingold.json2csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Main class for challenge #1.
 * @author karanikasg@gmail.com
 */
public class Challenge1 {

    public static void main(String[] args) {
        Challenge1 challenge1 = new Challenge1();
        try {
            challenge1.start();
        } catch (IOException e) {
            System.out.println("Unable to complete. Exception thrown: " + e.getMessage());
        }
    }

    /**
     * Since the expected input was described to be HUGE the code below will try to work in a streaming context.
     * We will have to combine each json object in one file, reserving one line for each.
     *
     * We'll have to first combine the headers though so we can't avoid going through all the json objects, thus
     * implementing a two step solution.
     *
     * Taking that as granted we will assume that all distinct headers are finite and can be stored in memory
     * in one run. We will also assume that each json object alone can fit in memory.
     * Those were assumptions I had to make as the requirements of the challenge were a bit vague.
     *
     * While iterating over the json objects we will store the headers in an ordered set AND append
     * the objects in a temp file letting each object occupy one line at a time in the following format:
     * header1:value1, header2:value2, ...
     *
     * As a second step, we will go over each line of the temp file (representing one json object)
     * and fill in our resulting file.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        Set<String> headers = new TreeSet<>();
        Client client = new MockClient();

        Cursor cur = client.query();

        Path tmpPath = Paths.get("tmp.csv");

        // that's the first step of the procedure,
        // ie storing each json in one line of a temp file AND keeping track of the unique headers
        try (BufferedWriter writer = Files.newBufferedWriter(tmpPath)) {
            while (cur.hasNext()) {

                Json json = cur.next();
                json.forEach((k, v) -> {
                    headers.add((String) k);
                    try {
                        writer.write(k + ":" + v + ",");
                    } catch (IOException e) {
                        System.out.println(String.format(
                                "Unable to write %s:%s of json '%s' into file. Reason is %s ",
                                k, v, json.toString(), e.getMessage()));
                    }
                });
                writer.newLine();
            }
        }
        // first step is over

        System.out.println("Headers: " + headers.stream().collect(Collectors.joining(", ", "{", "}")));
        Path path = Paths.get("output.csv");

        // and that will be the second step of the procedure.
        try (BufferedWriter writer = Files.newBufferedWriter(path); BufferedReader reader = Files.newBufferedReader(tmpPath)) {
            String line;
            writer.newLine();

            // at this point we have all the headers in one ordered set. We should just spit it out as the first line
            for (String header : headers) {
                // first line should contain the Headers
                writer.write(header+",");
            }
            writer.newLine();

            // continue with the json objects
            while( (line = reader.readLine()) != null) {
                Map<String, String> headerNames = parseLine(line);
                headers.forEach(header -> {
                    try {
                        if (headerNames.keySet().contains(header)) { // they are both sorted so we can help ourselves here
                            writer.write(headerNames.get(header));
                        }
                        writer.write(",");
                    } catch (IOException e) {
                        System.out.println("this should never happen. writing for " + header);
                    }
                });
                writer.newLine();
            }
        } // second step is over

        System.out.println("Data written in output.csv");

        // delete the temporary file
        Files.delete(tmpPath);
    }

    /**
     * Parses a line of the following format:
     * header1:value1,header2:value2,...
     * and return a Map of Header-Values combinations
     * @param line the target line
     * @return map containing each header-value
     */
    private Map<String, String> parseLine(String line) {
        Map<String, String> result = new HashMap<>();
        List<String> elements = Arrays.asList(line.split("\\s*,\\s*"));
        for (String element : elements) {
            List<String> headerValueList = Arrays.asList(element.split("\\s*:\\s*"));
            if (headerValueList.size() != 2) {
                continue;
            }
            // at this point we know we have two elements. Construct the header-values;
            result.put(headerValueList.get(0), headerValueList.get(1));
        }
        return result;
    }
}
