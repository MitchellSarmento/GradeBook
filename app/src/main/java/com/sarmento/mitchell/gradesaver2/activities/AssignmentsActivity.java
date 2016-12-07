package com.sarmento.mitchell.gradesaver2.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.AssignmentAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.AssignmentDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.views.SectionHeader;

public class AssignmentsActivity extends AppCompatActivity {
    public static final int IMAGE_CAPTURE = 0;

    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private AssignmentAdapter adapter;
    private SectionHeader header;
    private Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        termPosition    = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);

        if (academics.inArchive()) {
            section = academics.getArchivedTerms().get(termPosition)
                    .getSections().get(sectionPosition);
        } else {
            section = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition);
        }
        setTitle(section.getSectionName());

        header = (SectionHeader) findViewById(R.id.header_section);
        header.init(section);

        RecyclerView assignments = (RecyclerView) findViewById(R.id.assignments);
        assignments.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentAdapter(this, section.getAssignments(), termPosition, sectionPosition);
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
        if (!academics.inArchive()) {
            inflater.inflate(R.menu.menu_assignments, menu);
        } else {
            inflater.inflate(R.menu.menu_assignments_archive, menu);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            Assignment assignment;
            if (academics.inArchive()) {
                assignment = academics.getArchivedTerms().get(termPosition)
                        .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
            } else {
                assignment = academics.getCurrentTerms().get(termPosition)
                        .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
            }
            assignment.addImage(imageBitmap);
        }
    }

    public void setAssignmentPosition(int assignmentPosition) {
        this.assignmentPosition = assignmentPosition;
    }
}
