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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.DueDatesActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.DueDate;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Calendar;

public class DueDateDialogFragment extends DialogFragment {
    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int dueDatePosition;

    private DueDate dueDate;

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
        View dialogView         = inflater.inflate(R.layout.dialog_due_date, null);
        builder.setView(dialogView);

        // get relevant Views
        final EditText dueDateEntry = (EditText) dialogView.findViewById(R.id.due_date_entry);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date);

        // get error views
        final TextView[] errorViews = {
                (TextView) dialogView.findViewById(R.id.error_due_date_no_name)
        };

        // set fields if editing
        if (editing) {
            dueDatePosition = arguments.getInt(Academics.DUE_DATE_POSITION);
            dueDate = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition)
                    .getDueDates().get(dueDatePosition);
            Calendar due = dueDate.getDate();

            dueDateEntry.setText(dueDate.getDueDateName());
            datePicker.init(due.get(Calendar.YEAR), due.get(Calendar.MONTH),
                    due.get(Calendar.DAY_OF_MONTH), null);
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
                        String dueDateName = dueDateEntry.getText().toString();
                        int month          = datePicker.getMonth();
                        int day            = datePicker.getDayOfMonth();
                        int year           = datePicker.getYear();

                        Calendar date = Calendar.getInstance();
                        date.set(year, month, day);

                        InputCheck inputCheck = validateInput(dueDateName);
                        if (inputCheck == InputCheck.VALID) {
                            // check if editing
                            if (editing) {
                                // edit existing DueDate
                                dueDate.updateDueDate(activity, dueDateName, date, termPosition, sectionPosition,
                                        dueDatePosition);
                                ((DueDatesActivity) activity).updateList();
                            } else {
                                // create new due date
                                DueDate dueDate = new DueDate(dueDateName, false, date);

                                // add new due date
                                Section section = academics.getCurrentTerms().get(termPosition)
                                        .getSections().get(sectionPosition);
                                int dueDatePosition = section.getDueDates().size();
                                section.addDueDate(activity, dueDate, termPosition, sectionPosition,
                                        dueDatePosition);
                                ((DueDatesActivity) activity).updateList();
                                dialog.dismiss();
                            }
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

    private InputCheck validateInput(String dueDateName) {
        // check for missing Term name
        if (dueDateName.trim().equals("")) {
            return InputCheck.ERROR_NO_NAME;
        }

        return InputCheck.VALID;
    }
}
