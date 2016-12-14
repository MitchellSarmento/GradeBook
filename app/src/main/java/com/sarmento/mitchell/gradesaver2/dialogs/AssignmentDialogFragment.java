package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDialogFragment extends DialogFragment {
    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    private Assignment assignment;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments.containsKey(OptionsDialogFragment.EDITING);

        termPosition    = arguments.getInt(Academics.TERM_POSITION);
        sectionPosition = arguments.getInt(Academics.SECTION_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView         = inflater.inflate(R.layout.dialog_assignment, null);
        builder.setView(dialogView);

        // get relevant Views
        final EditText assignmentNameEntry = (EditText) dialogView.findViewById(R.id.assignment_entry);
        final EditText myScoreEntry        = (EditText) dialogView.findViewById(R.id.my_score);
        final EditText maxScoreEntry       = (EditText) dialogView.findViewById(R.id.max_score);
        final Spinner assignmentTypeEntry  = (Spinner) dialogView.findViewById(R.id.assignment_type);

        // get error Views
        final TextView[] errorViews = {
                (TextView) dialogView.findViewById(R.id.error_assignment_no_name),
                (TextView) dialogView.findViewById(R.id.error_assignment_no_my_score),
                (TextView) dialogView.findViewById(R.id.error_assignment_no_max_score),
                (TextView) dialogView.findViewById(R.id.error_assignment_zero_max_score)
        };

        // set the spinner data to relevant assignment types
        final Section section = academics.getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);
        List<Integer> relevantTypes = section.getRelevantAssignmentTypes();

        int[] stringIds = {R.string.homework, R.string.quiz, R.string.midterm,
                R.string.string_final, R.string.project, R.string.other};

        List<String> types = new ArrayList<>();
        for (int type : relevantTypes) {
            types.add(getString(stringIds[type]));
        }

        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_spinner_item, types);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignmentTypeEntry.setAdapter(typesAdapter);

        // set fields if editing
        if (editing) {
            assignmentPosition = arguments.getInt(Academics.ASSIGNMENT_POSITION);
            assignment         = section.getAssignments().get(assignmentPosition);

            assignmentNameEntry.setText(assignment.getAssignmentName());
            myScoreEntry.setText(String.valueOf(assignment.getScore()));
            maxScoreEntry.setText(String.valueOf(assignment.getMaxScore()));
            assignmentTypeEntry.setSelection(typesAdapter.getPosition(assignment.getAssignmentType()));
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
                        String assignmentName = assignmentNameEntry.getText().toString();
                        String myScoreString  = myScoreEntry.getText().toString();
                        String maxScoreString = maxScoreEntry.getText().toString();
                        String assignmentType = String.valueOf(assignmentTypeEntry.getSelectedItem());

                        InputCheck inputCheck = validateInput(assignmentName,
                                myScoreString, maxScoreString);
                        if (inputCheck == InputCheck.VALID) {
                            double myScore  = Double.valueOf(myScoreString);
                            double maxScore = Double.valueOf(maxScoreString);

                            // check if editing
                            if (editing) {
                                // editing existing Assignment
                                section.updateAssignment(activity, assignmentName, myScore, maxScore,
                                        assignmentType, termPosition, sectionPosition, assignmentPosition);
                                ((AssignmentsActivity) activity).updateList();
                            } else {
                                // create new assignment
                                assignment = new Assignment(assignmentName, assignmentType,
                                        myScore, maxScore, section.calculateAssignmentGrade(myScore, maxScore));

                                // add new assignment
                                assignmentPosition = section.getAssignments().size();
                                section.addAssignment(activity, assignment, termPosition,
                                        sectionPosition, assignmentPosition);
                                ((AssignmentsActivity) activity).updateList();
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
        VALID(-1), ERROR_NO_NAME(0), ERROR_NO_MY_SCORE(1), ERROR_NO_MAX_SCORE(2),
        ERROR_ZERO_MAX_SCORE(3);

        private int value;

        InputCheck(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private InputCheck validateInput(String assignmentName, String myScore, String maxScore) {
        // check for missing Assignment name
        if (assignmentName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        }

        // check for missing My Score
        if (!myScore.matches(".*\\d+.*")) {
            return InputCheck.ERROR_NO_MY_SCORE;
        }

        // check for missing Max Score
        if (!maxScore.matches(".*\\d+.*")) {
            return InputCheck.ERROR_NO_MAX_SCORE;
        }

        // check for zero Max Score
        if (Double.valueOf(maxScore) == 0) {
            return InputCheck.ERROR_ZERO_MAX_SCORE;
        }

        return InputCheck.VALID;
    }
}
