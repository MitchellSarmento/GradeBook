package com.sarmento.mitchell.gradesaver2.dialogs;

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
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DueDateDialogFragment extends DialogFragment {
    private int termPosition;
    private int sectionPosition;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        termPosition = getArguments().getInt(Academics.TERM_POSITION);
        sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_due_date, null);
        builder.setView(dialogView);

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
                EditText dueDateEntry = (EditText) getDialog().findViewById(R.id.due_date_entry);
                DatePicker datePicker = (DatePicker) getDialog().findViewById(R.id.date);

                // get user input
                String dueDateName = dueDateEntry.getText().toString();
                int month = datePicker.getMonth();
                int day   = datePicker.getDayOfMonth();
                int year  = datePicker.getYear();

                // create new due date
                Calendar date = Calendar.getInstance();
                date.set(year, month, day);
                DueDate dueDate = new DueDate(dueDateName, false, date);

                // add new due date
                Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
                        .getSections().get(sectionPosition);
                int dueDatePosition = section.getDueDates().size();
                section.addDueDate(getActivity(), dueDate, termPosition, sectionPosition,
                        dueDatePosition);
                ((DueDatesActivity) getActivity()).updateList();

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
