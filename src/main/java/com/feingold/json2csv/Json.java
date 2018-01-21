package com.feingold.json2csv;

import org.json.simple.JSONObject;

import java.util.TreeMap;

/**
 * That's an extra object that holds our json object sorted by key
 *
 * Created by karanikasg@gmail.com.
 */
public class Json extends TreeMap {

    public Json(JSONObject jsonObject) {
        super(jsonObject);
    }

}
