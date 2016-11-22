package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_term, null));

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // get relevant views
                EditText termNameEntry = (EditText) getDialog().findViewById(R.id.term_entry);

                // get user input
                String termName = termNameEntry.getText().toString();

                // create new term
                Term term = new Term(termName);

                // add new term
                Academics.getInstance().addTerm(getActivity(), term);

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
