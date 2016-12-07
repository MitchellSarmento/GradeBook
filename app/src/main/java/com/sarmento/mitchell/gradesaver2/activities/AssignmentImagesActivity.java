package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;

public class AssignmentImagesActivity extends AppCompatActivity {
    Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_images);

        termPosition       = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition    = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);
        assignmentPosition = getIntent().getIntExtra(Academics.ASSIGNMENT_POSITION, -1);


        if (academics.inArchive()) {
            assignment = academics.getArchivedTerms().get(termPosition)
                    .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
        } else {
            assignment = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
        }
        setTitle(assignment.getAssignmentName());

        ImageView mainImage = (ImageView) findViewById(R.id.image_main);
        mainImage.setImageBitmap(assignment.getImages().get(0));
    }
}
