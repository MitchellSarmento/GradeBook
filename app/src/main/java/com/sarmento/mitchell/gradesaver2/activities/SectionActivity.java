package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.AssignmentAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.AssignmentDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

public class SectionActivity extends AppCompatActivity {
    private int termPosition;
    private int sectionPosition;
    private AssignmentAdapter adapter;

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
        adapter = new AssignmentAdapter(section.getAssignments());
        assignments.setAdapter(adapter);
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_section, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_assignment:
                AssignmentDialogFragment dialog = new AssignmentDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Academics.TERM_POSITION, termPosition);
                bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), getString(R.string.action_new_assignment));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
