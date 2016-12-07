package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

public class FinalGradeDialogFragment extends DialogFragment {
    private int termPosition;
    private int sectionPosition;

    private Section section;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Academics academics = Academics.getInstance();
        final Activity activity = getActivity();
        final Bundle arguments  = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_final_grade, null);
        builder.setView(dialogView);

        // get relevant Views
        final EditText finalGradeEntry = (EditText) dialogView.findViewById(R.id.final_grade_entry);

        // set fields
        termPosition    = arguments.getInt(Academics.TERM_POSITION);
        sectionPosition = arguments.getInt(Academics.SECTION_POSITION);

        section = academics.getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);

        finalGradeEntry.setText(section.getFinalGrade());

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
                String finalGrade = finalGradeEntry.getText().toString();

                // set final grade
                section.setFinalGrade(activity, finalGrade, termPosition, sectionPosition);

                ((SectionsActivity) activity).updateList();

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}