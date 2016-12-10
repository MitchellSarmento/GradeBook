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
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.model.Term;

import java.util.List;

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
        final View dialogView = inflater.inflate(R.layout.dialog_section, null);
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

        // get error views
        final TextView[] errorViews = {
                (TextView) dialogView.findViewById(R.id.error_section_no_name),
                (TextView) dialogView.findViewById(R.id.error_section_assignment_weights),
                (TextView) dialogView.findViewById(R.id.error_section_grade_thresholds),
                (TextView) dialogView.findViewById(R.id.error_section_weight_conflict)
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

                        InputCheck inputCheck = validateInput(editing, sectionName,
                                assignmentWeights, gradeThresholds);
                        if (inputCheck == InputCheck.VALID) {
                            // check if editing
                            if (editing) {
                                // edit existing Section
                                Term term = academics.getCurrentTerms().get(termPosition);
                                term.updateSection(activity, sectionName, assignmentWeights,
                                        gradeThresholds, termPosition, sectionPosition);
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
                        } else {
                            int inputCheckValue = inputCheck.getValue();

                            // display the appropriate error View
                            errorViews[inputCheckValue].setVisibility(View.VISIBLE);

                            // remove other error Views that may be visible
                            for (InputCheck check : InputCheck.values()) {
                                int checkValue = check.getValue();
                                if (checkValue != -1 && checkValue != inputCheckValue) {
                                    TextView errorView = errorViews[checkValue];
                                    if (errorView.getVisibility() == View.VISIBLE) {
                                        errorView.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        return dialog;
    }

    private enum InputCheck {
        VALID(-1), ERROR_NO_NAME(0), ERROR_ASSIGNMENT_WEIGHTS(1), ERROR_GRADE_THRESHOLDS(2),
        ERROR_WEIGHT_CONFLICT(3);

        private int value;

        InputCheck(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private InputCheck validateInput(boolean editing, String sectionName,
                                     SparseArray<Double> assignmentWeights,
                                     SparseArray<Double> gradeThresholds) {
        // check for missing Section name
        if (sectionName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        }

        // check for assignment weights totaling a value other than 100%
        double totalAssignmentWeights = 0;
        for (Section.AssignmentType type : Section.AssignmentType.values()) {
            int typeValue = type.getValue();
            totalAssignmentWeights += assignmentWeights.get(typeValue);
        }
        if (totalAssignmentWeights != 100) {
            return InputCheck.ERROR_ASSIGNMENT_WEIGHTS;
        }

        // check for grade threshold errors
        for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
            int thresholdValue = threshold.getValue();
            if (thresholdValue % 2 == 0) {
                // check for upper bound < lower bound
                if (gradeThresholds.get(thresholdValue) <= gradeThresholds.get(thresholdValue+1)) {
                    return InputCheck.ERROR_GRADE_THRESHOLDS;
                }
            } else {
                // check for lower bound of a higher grade < high bound of a lower grade
                if (threshold != Section.GradeThreshold.LOW_F) {
                    if (gradeThresholds.get(thresholdValue) <= gradeThresholds.get(thresholdValue + 1)) {
                        return InputCheck.ERROR_GRADE_THRESHOLDS;
                    }
                }
            }
        }

        // if editing, check that a weighting exists for all existing Assignments
        if (editing) {
            List<Assignment> assignments = Academics.getInstance().getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition).getAssignments();
            for (Assignment assignment : assignments) {
                int assignmentType = Section.convertAssignmentType(getActivity(),
                        assignment.getAssignmentType());
                if (assignmentWeights.get(assignmentType) == 0) {
                    return InputCheck.ERROR_WEIGHT_CONFLICT;
                }
            }
        }

        return InputCheck.VALID;
    }
}
