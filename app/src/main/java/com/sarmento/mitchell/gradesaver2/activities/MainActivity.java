package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.TermAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class MainActivity extends AppCompatActivity {
    Academics academics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        academics = Academics.getInstance();

        ListView currentTerms = (ListView) findViewById(R.id.current_terms);
        currentTerms.setAdapter(new TermAdapter(getApplicationContext(), academics.getCurrentTerms()));
    }
}
