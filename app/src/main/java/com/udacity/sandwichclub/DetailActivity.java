package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnowAsTextview;
    private TextView ingredientsTextview;
    private TextView placeOfOriginTextview;
    private TextView descriptionTextview;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        alsoKnowAsTextview = findViewById(R.id.also_known_tv);
        String temp = "";
        List<String> knownas = sandwich.getAlsoKnownAs();
        for (String i : knownas) {
            temp = temp + i + "\n";
        }
        alsoKnowAsTextview.setText(temp);

        ingredientsTextview = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        temp = "";
        for (String i : ingredients) {
            temp = temp + i + "\n";
        }
        ingredientsTextview.setText(temp);

        placeOfOriginTextview = findViewById(R.id.origin_tv);
        placeOfOriginTextview.setText(sandwich.getPlaceOfOrigin());

        descriptionTextview = findViewById(R.id.description_tv);
        descriptionTextview.setText(sandwich.getDescription());

    }
}
