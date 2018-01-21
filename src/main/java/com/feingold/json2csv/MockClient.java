package com.feingold.json2csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock query to execute a query and return a {@link Cursor} object.
 *
 * @author karanikasg@gmail.com
 */
public class MockClient implements Client {

    /**
     * As part of a mock object it will request the same files each time to be parsed.
     * @return a {@link Cursor} object.
     */
    @Override
    public Cursor query() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("document_" + i + ".txt");
        }
        return new Cursor(list);
    }
}
