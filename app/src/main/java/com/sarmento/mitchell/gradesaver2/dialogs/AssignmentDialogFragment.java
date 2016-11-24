package com.sarmento.mitchell.gradesaver2.dialogs;

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
import com.sarmento.mitchell.gradesaver2.activities.SectionActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDialogFragment extends DialogFragment {
    int termPosition;
    int sectionPosition;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        termPosition = getArguments().getInt(Academics.TERM_POSITION);
        sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_assignment, null);
        builder.setView(dialogView);

        // set the spinner data to relevant assignment types
        Spinner assignmentTypes = (Spinner) dialogView.findViewById(R.id.assignment_type);
        final Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
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
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, types);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignmentTypes.setAdapter(typesAdapter);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // get relevant Views
                EditText assignmentNameEntry = (EditText) getDialog().findViewById(R.id.assignment_entry);
                EditText myScoreEntry        = (EditText) getDialog().findViewById(R.id.my_score);
                EditText maxScoreEntry       = (EditText) getDialog().findViewById(R.id.max_score);
                Spinner assignmentTypeEntry  = (Spinner) getDialog().findViewById(R.id.assignment_type);

                // get user input
                String assignmentName = assignmentNameEntry.getText().toString();
                double myScore        = Double.valueOf(myScoreEntry.getText().toString());
                double maxScore       = Double.valueOf(maxScoreEntry.getText().toString());
                String assignmentType = String.valueOf(assignmentTypeEntry.getSelectedItem());

                // create new assignment
                Assignment assignment = new Assignment(assignmentName, assignmentType,
                        myScore, maxScore, section.calculateAssignmentGrade(myScore, maxScore));

                // add new assignment
                section.addAssignment(getActivity(), assignment, sectionPosition);
                ((SectionActivity) getActivity()).updateList();

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
