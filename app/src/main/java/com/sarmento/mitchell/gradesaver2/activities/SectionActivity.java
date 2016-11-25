package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.AssignmentAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.AssignmentDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

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

        setHeader(section);

        RecyclerView assignments = (RecyclerView) findViewById(R.id.assignments);
        assignments.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentAdapter(section.getAssignments());
        assignments.setAdapter(adapter);
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    private void setHeader(Section section) {
        SparseArray<Double> gradeThresholds = section.getGradeThresholds();

        TextView gradeThresholdA = (TextView) findViewById(R.id.grade_threshold_a);
        TextView gradeThresholdB = (TextView) findViewById(R.id.grade_threshold_b);
        TextView gradeThresholdC = (TextView) findViewById(R.id.grade_threshold_c);
        TextView gradeThresholdD = (TextView) findViewById(R.id.grade_threshold_d);
        TextView gradeThresholdF = (TextView) findViewById(R.id.grade_threshold_f);

        gradeThresholdA.setText(getString(R.string.a) + " " +
                gradeThresholds.get(Section.HIGH_A) + " - " + gradeThresholds.get(Section.LOW_A));
        gradeThresholdB.setText(getString(R.string.b) + " " +
                gradeThresholds.get(Section.HIGH_B) + " - " + gradeThresholds.get(Section.LOW_B));
        gradeThresholdC.setText(getString(R.string.c) + " " +
                gradeThresholds.get(Section.HIGH_C) + " - " + gradeThresholds.get(Section.LOW_C));
        gradeThresholdD.setText(getString(R.string.d) + " " +
                gradeThresholds.get(Section.HIGH_D) + " - " + gradeThresholds.get(Section.LOW_D));
        gradeThresholdF.setText(getString(R.string.f) + " " +
                gradeThresholds.get(Section.HIGH_F) + " - " + gradeThresholds.get(Section.LOW_F));

        SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
        SparseArray<Double> scores            = section.getScores();
        SparseArray<Double> maxScores         = section.getMaxScores();

        TextView scoreHomework = (TextView) findViewById(R.id.score_homework);
        TextView scoreQuiz     = (TextView) findViewById(R.id.score_quiz);
        TextView scoreMidterm  = (TextView) findViewById(R.id.score_midterm);
        TextView scoreFinal    = (TextView) findViewById(R.id.score_final);
        TextView scoreProject  = (TextView) findViewById(R.id.score_project);
        TextView scoreOther    = (TextView) findViewById(R.id.score_other);

        String scoreString;

        scoreString = section.scoreToString(scores.get(Section.HOMEWORK),
                maxScores.get(Section.HOMEWORK), Section.HOMEWORK);
        scoreHomework.setText(scoreString + "/" + assignmentWeights.get(Section.HOMEWORK) + "%");

        scoreString = section.scoreToString(scores.get(Section.QUIZ),
                maxScores.get(Section.QUIZ), Section.QUIZ);
        scoreQuiz.setText(scoreString + "/" + assignmentWeights.get(Section.QUIZ) + "%");

        scoreString = section.scoreToString(scores.get(Section.MIDTERM),
                maxScores.get(Section.MIDTERM), Section.MIDTERM);
        scoreMidterm.setText(scoreString + "/" + assignmentWeights.get(Section.MIDTERM) + "%");

        scoreString = section.scoreToString(scores.get(Section.FINAL),
                maxScores.get(Section.FINAL), Section.FINAL);
        scoreFinal.setText(scoreString + "/" + assignmentWeights.get(Section.FINAL) + "%");

        scoreString = section.scoreToString(scores.get(Section.PROJECT),
                maxScores.get(Section.PROJECT), Section.PROJECT);
        scoreProject.setText(scoreString + "/" + assignmentWeights.get(Section.PROJECT) + "%");

        scoreString = section.scoreToString(scores.get(Section.OTHER),
                maxScores.get(Section.OTHER), Section.OTHER);
        scoreOther.setText(scoreString + "/" + assignmentWeights.get(Section.OTHER) + "%");

        TextView grade = (TextView) findViewById(R.id.grade);

        double scorePercent = section.getTotalScore() / section.getMaxScore() * 100;

        if (Double.isNaN(scorePercent)) {
            scoreString = "";
        } else {
            scoreString = String.format(Locale.getDefault(), "%.2f", scorePercent) + "% " +
                    section.getGrade();
        }
        grade.setText(getText(R.string.total) + " " + scoreString);
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
