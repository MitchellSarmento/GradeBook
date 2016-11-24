package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Academics;

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
        builder.setView(inflater.inflate(R.layout.dialog_assignment, null));

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
