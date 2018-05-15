package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonBody = null;
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        try {
            jsonBody = new JSONObject(json);
            JSONObject nameJsonObject = jsonBody.getJSONObject("name");
            mainName = nameJsonObject.getString("mainName");
            alsoKnownAs = makelist(nameJsonObject.getJSONArray("alsoKnownAs"));
            placeOfOrigin = jsonBody.getString("placeOfOrigin");
            description = jsonBody.getString("description");
            image = jsonBody.getString("image");
            ingredients = makelist(jsonBody.getJSONArray("ingredients"));
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON", e);
        }

        return null;

    }

    private static List<String> makelist(JSONArray arr) {
        List<String> temp = new LinkedList<>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                temp.add(arr.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }


}
