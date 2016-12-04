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
import android.widget.EditText;
import android.widget.Spinner;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDialogFragment extends DialogFragment {
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    private Assignment assignment;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Academics academics = Academics.getInstance();
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments.containsKey(OptionsDialogFragment.EDITING);

        termPosition = getArguments().getInt(Academics.TERM_POSITION);
        sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_assignment, null);
        builder.setView(dialogView);

        // get relevant Views
        final EditText assignmentNameEntry = (EditText) dialogView.findViewById(R.id.assignment_entry);
        final EditText myScoreEntry        = (EditText) dialogView.findViewById(R.id.my_score);
        final EditText maxScoreEntry       = (EditText) dialogView.findViewById(R.id.max_score);
        final Spinner assignmentTypeEntry  = (Spinner) dialogView.findViewById(R.id.assignment_type);

        // set the spinner data to relevant assignment types
        final Section section = academics.getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);
        List<Integer> relevantTypes = section.getRelevantAssignmentTypes();
        List<String> types = new ArrayList<>();
        for (int type : relevantTypes) {
            switch (type) {
                case Section.HOMEWORK:
                    types.add(getString(R.string.homework));
                    break;
                case Section.QUIZ:
                    types.add(getString(R.string.quiz));
                    break;
                case Section.MIDTERM:
                    types.add(getString(R.string.midterm));
                    break;
                case Section.FINAL:
                    types.add(getString(R.string.string_final));
                    break;
                case Section.PROJECT:
                    types.add(getString(R.string.project));
                    break;
                case Section.OTHER:
                    types.add(getString(R.string.other));
                    break;
                default:
                    break;
            }
        }
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_spinner_item, types);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignmentTypeEntry.setAdapter(typesAdapter);

        // set fields if editing
        if (editing) {
            assignmentPosition = arguments.getInt(Academics.ASSIGNMENT_POSITION);
            assignment = section.getAssignments().get(assignmentPosition);

            assignmentNameEntry.setText(assignment.getAssignmentName());
            myScoreEntry.setText(String.valueOf(assignment.getScore()));
            maxScoreEntry.setText(String.valueOf(assignment.getMaxScore()));
            assignmentTypeEntry.setSelection(typesAdapter.getPosition(assignment.getAssignmentType()));
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
                String assignmentName = assignmentNameEntry.getText().toString();
                double myScore        = Double.valueOf(myScoreEntry.getText().toString());
                double maxScore       = Double.valueOf(maxScoreEntry.getText().toString());
                String assignmentType = String.valueOf(assignmentTypeEntry.getSelectedItem());

                // check if editing
                if (editing) {
                    // editing existing Assignment
                    assignment.updateAssignment(activity, assignmentName, myScore, maxScore,
                            assignmentType, termPosition, sectionPosition, assignmentPosition);
                    ((AssignmentsActivity) activity).updateList();
                } else {
                    // create new assignment
                    assignment = new Assignment(assignmentName, assignmentType,
                            myScore, maxScore, section.calculateAssignmentGrade(myScore, maxScore));

                    // add new assignment
                    assignmentPosition = section.getAssignments().size();
                    section.addAssignment(getActivity(), assignment, termPosition,
                            sectionPosition, assignmentPosition);
                    ((AssignmentsActivity) getActivity()).updateList();
                }

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
