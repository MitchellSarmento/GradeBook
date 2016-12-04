package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class SectionDialogFragment extends DialogFragment {
    private int termPosition;
    private int sectionPosition;

    private Section section;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Academics academics = Academics.getInstance();
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments.containsKey(OptionsDialogFragment.EDITING);

        termPosition = arguments.getInt(Academics.TERM_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_section, null);
        builder.setView(dialogView);

        // get relevant views
        final EditText sectionNameEntry    = (EditText) dialogView.findViewById(R.id.section_entry);
        final EditText weightHomeworkEntry = (EditText) dialogView.findViewById(R.id.weight_homework);
        final EditText weightQuizzesEntry  = (EditText) dialogView.findViewById(R.id.weight_quizzes);
        final EditText weightMidtermEntry  = (EditText) dialogView.findViewById(R.id.weight_midterm);
        final EditText weightFinalEntry    = (EditText) dialogView.findViewById(R.id.weight_final);
        final EditText weightProjectEntry  = (EditText) dialogView.findViewById(R.id.weight_project);
        final EditText weightOtherEntry    = (EditText) dialogView.findViewById(R.id.weight_other);
        final EditText highAEntry          = (EditText) dialogView.findViewById(R.id.high_a);
        final EditText lowAEntry           = (EditText) dialogView.findViewById(R.id.low_a);
        final EditText highBEntry          = (EditText) dialogView.findViewById(R.id.high_b);
        final EditText lowBEntry           = (EditText) dialogView.findViewById(R.id.low_b);
        final EditText highCEntry          = (EditText) dialogView.findViewById(R.id.high_c);
        final EditText lowCEntry           = (EditText) dialogView.findViewById(R.id.low_c);
        final EditText highDEntry          = (EditText) dialogView.findViewById(R.id.high_d);
        final EditText lowDEntry           = (EditText) dialogView.findViewById(R.id.low_d);
        final EditText highFEntry          = (EditText) dialogView.findViewById(R.id.high_f);
        final EditText lowFEntry           = (EditText) dialogView.findViewById(R.id.low_f);

        // set fields if editing
        if (editing) {
            sectionPosition = arguments.getInt(Academics.SECTION_POSITION);
            section = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition);
            SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
            SparseArray<Double> gradeThresholds   = section.getGradeThresholds();

            sectionNameEntry.setText(section.getSectionName());
            weightHomeworkEntry.setText(String.valueOf(assignmentWeights.get(Section.HOMEWORK)));
            weightQuizzesEntry.setText(String.valueOf(assignmentWeights.get(Section.QUIZ)));
            weightMidtermEntry.setText(String.valueOf(assignmentWeights.get(Section.MIDTERM)));
            weightFinalEntry.setText(String.valueOf(assignmentWeights.get(Section.FINAL)));
            weightProjectEntry.setText(String.valueOf(assignmentWeights.get(Section.PROJECT)));
            weightOtherEntry.setText(String.valueOf(assignmentWeights.get(Section.OTHER)));
            highAEntry.setText(String.valueOf(gradeThresholds.get(Section.HIGH_A)));
            lowAEntry.setText(String.valueOf(gradeThresholds.get(Section.LOW_A)));
            highBEntry.setText(String.valueOf(gradeThresholds.get(Section.HIGH_B)));
            lowBEntry.setText(String.valueOf(gradeThresholds.get(Section.LOW_B)));
            highCEntry.setText(String.valueOf(gradeThresholds.get(Section.HIGH_C)));
            lowCEntry.setText(String.valueOf(gradeThresholds.get(Section.LOW_C)));
            highDEntry.setText(String.valueOf(gradeThresholds.get(Section.HIGH_D)));
            lowDEntry.setText(String.valueOf(gradeThresholds.get(Section.LOW_D)));
            highFEntry.setText(String.valueOf(gradeThresholds.get(Section.HIGH_F)));
            lowFEntry.setText(String.valueOf(gradeThresholds.get(Section.LOW_F)));
        }

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // get user input
                String sectionName = sectionNameEntry.getText().toString();

                SparseArray<Double> assignmentWeights = new SparseArray<>();
                assignmentWeights.put(Section.HOMEWORK,
                        Double.valueOf(weightHomeworkEntry.getText().toString()));
                assignmentWeights.put(Section.QUIZ,
                        Double.valueOf(weightQuizzesEntry.getText().toString()));
                assignmentWeights.put(Section.MIDTERM,
                        Double.valueOf(weightMidtermEntry.getText().toString()));
                assignmentWeights.put(Section.FINAL,
                        Double.valueOf(weightFinalEntry.getText().toString()));
                assignmentWeights.put(Section.PROJECT,
                        Double.valueOf(weightProjectEntry.getText().toString()));
                assignmentWeights.put(Section.OTHER,
                        Double.valueOf(weightOtherEntry.getText().toString()));

                SparseArray<Double> gradeThresholds = new SparseArray<>();
                gradeThresholds.put(Section.HIGH_A, Double.valueOf(highAEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_A, Double.valueOf(lowAEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_B, Double.valueOf(highBEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_B, Double.valueOf(lowBEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_C, Double.valueOf(highCEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_C, Double.valueOf(lowCEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_D, Double.valueOf(highDEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_D, Double.valueOf(lowDEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_F, Double.valueOf(highFEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_F, Double.valueOf(lowFEntry.getText().toString()));

                // check if editing
                if (editing) {
                    // edit existing Section
                    section.updateSection(activity, sectionName, assignmentWeights, gradeThresholds,
                            termPosition, sectionPosition);
                    ((SectionsActivity) activity).updateList();
                } else {
                    // create new Section
                    section = new Section(sectionName, assignmentWeights, gradeThresholds);

                    // add new Section
                    Term term = academics.getCurrentTerms().get(termPosition);
                    sectionPosition = term.getSections().size();
                    term.addSection(activity, section, termPosition, sectionPosition);
                    ((SectionsActivity) activity).updateList();
                }

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
