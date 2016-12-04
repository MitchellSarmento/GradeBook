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
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermDialogFragment extends DialogFragment {
    private int termPosition;

    private Term term;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Academics academics = Academics.getInstance();
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments != null && arguments.containsKey(OptionsDialogFragment.EDITING);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_term, null);
        builder.setView(dialogView);

        // get relevant views
        final EditText termNameEntry = (EditText) dialogView.findViewById(R.id.term_entry);

        // set fields if editing
        if (editing) {
            termPosition = arguments.getInt(Academics.TERM_POSITION);
            term = academics.getCurrentTerms().get(termPosition);

            termNameEntry.setText(term.getTermName());
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
                String termName = termNameEntry.getText().toString();

                // check if editing
                if (editing) {
                    // edit existing Term
                    term.setTermName(activity, termName, termPosition);
                    ((TermsActivity) activity).updateList();
                } else {
                    // create new Term
                    term = new Term(termName);

                    // add new Term
                    termPosition = academics.getCurrentTerms().size();
                    academics.addTerm(activity, term, termPosition);
                }

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
