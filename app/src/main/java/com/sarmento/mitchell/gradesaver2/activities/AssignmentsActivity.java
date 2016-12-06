package com.sarmento.mitchell.gradesaver2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.AssignmentAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.AssignmentDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.views.SectionHeader;

public class AssignmentsActivity extends AppCompatActivity {
    private int termPosition;
    private int sectionPosition;
    private AssignmentAdapter adapter;
    private SectionHeader header;
    private Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);

        section = Academics.getInstance().getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);
        setTitle(section.getSectionName());

        header = (SectionHeader) findViewById(R.id.header_section);
        header.init(section);

        RecyclerView assignments = (RecyclerView) findViewById(R.id.assignments);
        assignments.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentAdapter(section.getAssignments(), termPosition, sectionPosition);
        assignments.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
        header.update(section);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_assignments, menu);
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
            case R.id.action_due_dates:
                Intent intent = new Intent(this, DueDatesActivity.class);
                intent.putExtra(Academics.TERM_POSITION, termPosition);
                intent.putExtra(Academics.SECTION_POSITION, sectionPosition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
