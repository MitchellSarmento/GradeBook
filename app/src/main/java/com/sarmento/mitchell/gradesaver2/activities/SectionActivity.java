package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

public class SectionActivity extends AppCompatActivity {
    private int termPosition;
    private int sectionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);

        Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);

        RecyclerView assignments = (RecyclerView) findViewById(R.id.assignments);
        assignments.setLayoutManager(new LinearLayoutManager(this));
    }
}
