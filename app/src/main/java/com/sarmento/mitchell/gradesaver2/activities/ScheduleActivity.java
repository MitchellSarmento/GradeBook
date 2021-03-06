package com.sarmento.mitchell.gradesaver2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;
import com.sarmento.mitchell.gradesaver2.views.ScheduleView;

public class ScheduleActivity extends AppCompatActivity {
    private Academics academics = Academics.getInstance();
    private int termPosition;

    private Term term;
    private ScheduleView scheduleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);

        term = (academics.inArchive()) ?
                academics.getArchivedTerms().get(termPosition) :
                academics.getCurrentTerms().get(termPosition);
        setTitle(getString(R.string.title_schedule) + term.getTermName());

        // initialize the ScheduleView
        scheduleView = (ScheduleView) findViewById(R.id.details_schedule);
        scheduleView.init(term);
    }

    @Override
    public void onResume() {
        scheduleView.init(term);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!academics.inArchive()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_schedule, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_schedule:
                Intent intent = new Intent(this, ScheduleEditActivity.class);
                intent.putExtra(Academics.TERM_POSITION, termPosition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
