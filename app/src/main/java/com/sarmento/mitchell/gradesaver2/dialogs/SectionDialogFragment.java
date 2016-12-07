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
import android.widget.Button;
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
        final EditText sectionNameEntry  = (EditText) dialogView.findViewById(R.id.section_entry);
        final EditText[] weightEntries   = {
                (EditText) dialogView.findViewById(R.id.weight_homework),
                (EditText) dialogView.findViewById(R.id.weight_quizzes),
                (EditText) dialogView.findViewById(R.id.weight_midterm),
                (EditText) dialogView.findViewById(R.id.weight_final),
                (EditText) dialogView.findViewById(R.id.weight_project),
                (EditText) dialogView.findViewById(R.id.weight_other)
        };
        final EditText[] thresholdEntries = {
                (EditText) dialogView.findViewById(R.id.high_a),
                (EditText) dialogView.findViewById(R.id.low_a),
                (EditText) dialogView.findViewById(R.id.high_b),
                (EditText) dialogView.findViewById(R.id.low_b),
                (EditText) dialogView.findViewById(R.id.high_c),
                (EditText) dialogView.findViewById(R.id.low_c),
                (EditText) dialogView.findViewById(R.id.high_d),
                (EditText) dialogView.findViewById(R.id.low_d),
                (EditText) dialogView.findViewById(R.id.high_f),
                (EditText) dialogView.findViewById(R.id.low_f)
        };

        // set fields if editing
        if (editing) {
            sectionPosition = arguments.getInt(Academics.SECTION_POSITION);
            section = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition);
            sectionNameEntry.setText(section.getSectionName());

            SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
            for (Section.AssignmentType type : Section.AssignmentType.values()) {
                int typeValue = type.getValue();
                weightEntries[typeValue].setText(
                        String.valueOf(assignmentWeights.get(typeValue)));
            }

            SparseArray<Double> gradeThresholds   = section.getGradeThresholds();
            for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
                int thresholdValue = threshold.getValue();
                thresholdEntries[thresholdValue].setText(
                        String.valueOf(gradeThresholds.get(thresholdValue)));
            }
        }

        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.confirm, null);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get user input
                        String sectionName = sectionNameEntry.getText().toString();

                        SparseArray<Double> assignmentWeights = new SparseArray<>();
                        for (Section.AssignmentType type : Section.AssignmentType.values()) {
                            int typeValue = type.getValue();
                            assignmentWeights.put(typeValue,
                                    Double.valueOf(weightEntries[typeValue].getText().toString()));
                        }

                        SparseArray<Double> gradeThresholds = new SparseArray<>();
                        for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
                            int thresholdValue = threshold.getValue();
                            gradeThresholds.put(thresholdValue,
                                    Double.valueOf(thresholdEntries[thresholdValue].getText().toString()));
                        }

                        InputCheck inputCheck = validateInput(sectionName, assignmentWeights, gradeThresholds);
                        if (inputCheck == InputCheck.VALID) {
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
                        } else if (inputCheck == InputCheck.ERROR_NO_NAME) {

                        } else if (inputCheck == InputCheck.ERROR_ASSIGNMENT_WEIGHTS) {

                        } else if (inputCheck == InputCheck.ERROR_GRADE_THRESHOLDS) {

                        }
                    }
                });
            }
        });

        return dialog;
    }

    /*private enum InputCheck {
        VALID, ERROR_NO_NAME;
    }

    private InputCheck validateInput(String termName) {
        if (termName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        } else {
            return InputCheck.VALID;
        }
    }*/

    private enum InputCheck {
        VALID, ERROR_NO_NAME, ERROR_ASSIGNMENT_WEIGHTS, ERROR_GRADE_THRESHOLDS;
    }

    private InputCheck validateInput(String sectionName, SparseArray<Double> assignmentWeights,
                                     SparseArray<Double> gradeThresholds) {
        if (sectionName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        }

        return InputCheck.VALID;
    }
}
