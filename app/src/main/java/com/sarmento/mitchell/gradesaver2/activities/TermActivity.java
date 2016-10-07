package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.SectionAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermActivity extends AppCompatActivity {
    Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        int termPosition = getIntent().getIntExtra("termPosition", -1);
        term = Academics.getInstance().getCurrentTerms().get(termPosition);

        ListView sections = (ListView) findViewById(R.id.sections);
        sections.setAdapter(new SectionAdapter(getApplicationContext(), term.getSections()));
    }
}
