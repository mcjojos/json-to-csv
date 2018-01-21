package com.feingold.json2csv;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The cursor class is implementing the {@link Iterator} interface
 * by simply replaying the calls to {@link Iterator#hasNext()} and {@link Iterator#next()} to the underlying list.
 *
 * @author karanikasg@gmail.com
 */
public class Cursor implements Iterator<Json> {

    private final List<Json> jsonObjects;
    private final Iterator<Json> iterator;

    /**
     * Constructor of the cursor object.
     * @param fileList a list of files to be loaded from the resources.
     */
    public Cursor(List<String> fileList) {
        this.jsonObjects = new ArrayList<>();
        try {
            loadOrFail(fileList);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("failed to load json files. Exiting...");
            throw new IllegalStateException("failed to load json files. " + e.getMessage());
        }
        this.iterator = jsonObjects.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Json next() {
        return iterator.next();
    }

    /**
     * Loads the files and creates JSON objects out of them.
     * @param fileList the files to be parsed and loaded into JSON objects.
     * @throws ParseException will be thrown if for any reason one of the files cannot be loaded
     */
    private void loadOrFail(List<String> fileList) throws ParseException {
        List<InputStream> inputStreams = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        for (String fileName : fileList) {
            InputStream is = classLoader.getResourceAsStream(fileName);
            inputStreams.add(is);
        }

        for (InputStream is : inputStreams) {
            StringBuilder result = new StringBuilder("");
            try (Scanner scanner = new Scanner(is)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    result.append(line).append("\n");
                }
                scanner.close();
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result.toString());

            Json json = new Json(jsonObject);
            jsonObjects.add(json);
        }
    }
}
