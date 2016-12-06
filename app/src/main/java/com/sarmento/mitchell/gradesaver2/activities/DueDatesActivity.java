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
import com.sarmento.mitchell.gradesaver2.model.Section;

public class DueDatesActivity extends AppCompatActivity {
    private int termPosition;
    private int sectionPosition;
    private DueDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_dates);

        termPosition    = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);

        Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);
        setTitle(section.getSectionName());

        RecyclerView dueDates = (RecyclerView) findViewById(R.id.due_dates);
        dueDates.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DueDateAdapter(section.getDueDates(), termPosition, sectionPosition);
        dueDates.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_due_dates, menu);
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
