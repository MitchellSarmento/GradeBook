package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.DueDateAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.DueDateDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.DueDate;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Collections;
import java.util.List;

public class DueDatesActivity extends AppCompatActivity {
    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;

    private DueDateAdapter adapter;
    private List<DueDate> dueDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_dates);

        termPosition    = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);

        Section section = (academics.inArchive()) ?
                academics.getArchivedTerms().get(termPosition).getSections().get(sectionPosition) :
                academics.getCurrentTerms().get(termPosition).getSections().get(sectionPosition);
        setTitle(section.getSectionName());

        dueDates = section.getDueDates();
        Collections.sort(dueDates);

        RecyclerView dueDatesView = (RecyclerView) findViewById(R.id.due_dates);
        dueDatesView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DueDateAdapter(dueDates, termPosition, sectionPosition);
        dueDatesView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        Collections.sort(dueDates);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!academics.inArchive()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_due_dates, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_due_date:
                DueDateDialogFragment dialog = new DueDateDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Academics.TERM_POSITION, termPosition);
                bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), getString(R.string.action_new_due_date));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
