package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.DueDatesActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.DueDate;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Calendar;

public class DueDateDialogFragment extends DialogFragment {
    private int termPosition;
    private int sectionPosition;
    private int dueDatePosition;

    private DueDate dueDate;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Academics academics = Academics.getInstance();
        final Activity activity   = getActivity();
        final Bundle arguments    = getArguments();
        final boolean editing     = arguments.containsKey(OptionsDialogFragment.EDITING);

        termPosition    = getArguments().getInt(Academics.TERM_POSITION);
        sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_due_date, null);
        builder.setView(dialogView);

        // get relevant Views
        final EditText dueDateEntry = (EditText) dialogView.findViewById(R.id.due_date_entry);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date);

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
                String dueDateName = dueDateEntry.getText().toString();
                int month = datePicker.getMonth();
                int day   = datePicker.getDayOfMonth();
                int year  = datePicker.getYear();
                Calendar date = Calendar.getInstance();
                date.set(year, month, day);

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
                    Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
                            .getSections().get(sectionPosition);
                    int dueDatePosition = section.getDueDates().size();
                    section.addDueDate(getActivity(), dueDate, termPosition, sectionPosition,
                            dueDatePosition);
                    ((DueDatesActivity) getActivity()).updateList();
                }

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
