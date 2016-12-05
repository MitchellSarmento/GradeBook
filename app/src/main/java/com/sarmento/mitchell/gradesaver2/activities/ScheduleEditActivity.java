package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;
import com.sarmento.mitchell.gradesaver2.adapters.SectionAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class ScheduleEditActivity extends AppCompatActivity {
    private int termPosition;
    private ScheduleEditAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);

        Term term = Academics.getInstance().getCurrentTerms().get(termPosition);

        RecyclerView schedules = (RecyclerView) findViewById(R.id.schedules);
        schedules.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScheduleEditAdapter(term.getSections(), termPosition);
        schedules.setAdapter(adapter);
    }
}
