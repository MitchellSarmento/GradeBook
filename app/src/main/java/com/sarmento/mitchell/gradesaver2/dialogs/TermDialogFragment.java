package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermDialogFragment extends DialogFragment {
    private Academics academics = Academics.getInstance();
    private int termPosition;

    private Term term;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments != null && arguments.containsKey(OptionsDialogFragment.EDITING);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView         = inflater.inflate(R.layout.dialog_term, null);
        builder.setView(dialogView);

        // get relevant views
        final EditText termNameEntry = (EditText) dialogView.findViewById(R.id.term_entry);

        // get error views
        final TextView[] errorViews = {
                (TextView) dialogView.findViewById(R.id.error_term_no_name)
        };

        // set fields if editing
        if (editing) {
            termPosition = arguments.getInt(Academics.TERM_POSITION);
            term         = academics.getCurrentTerms().get(termPosition);

            termNameEntry.setText(term.getTermName());
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
                        String termName = termNameEntry.getText().toString();

                        InputCheck inputCheck = validateInput(termName);
                        if (inputCheck == InputCheck.VALID) {
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
                        } else {
                            int inputCheckValue = inputCheck.getValue();

                            // display the appropriate error View
                            errorViews[inputCheckValue].setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        return dialog;
    }

    private enum InputCheck {
        VALID(-1), ERROR_NO_NAME(0);

        private int value;

        InputCheck(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private InputCheck validateInput(String termName) {
        // check for missing Term name
        if (termName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        }

        return InputCheck.VALID;
    }
}
