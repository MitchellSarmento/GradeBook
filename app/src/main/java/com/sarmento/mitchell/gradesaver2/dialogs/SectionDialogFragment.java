package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.TermActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class SectionDialogFragment extends DialogFragment {
    int termPosition;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        termPosition = getArguments().getInt(Academics.TERM_POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_section, null));

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
                EditText sectionNameEntry = (EditText) getDialog().findViewById(R.id.section_entry);
                EditText weightHomeworkEntry = (EditText) getDialog().findViewById(R.id.weight_homework);
                EditText weightQuizzesEntry = (EditText) getDialog().findViewById(R.id.weight_quizzes);
                EditText weightMidtermEntry = (EditText) getDialog().findViewById(R.id.weight_midterm);
                EditText weightFinalEntry = (EditText) getDialog().findViewById(R.id.weight_final);
                EditText weightProjectEntry = (EditText) getDialog().findViewById(R.id.weight_project);
                EditText weightOtherEntry = (EditText) getDialog().findViewById(R.id.weight_other);
                EditText highAEntry = (EditText) getDialog().findViewById(R.id.high_a);
                EditText lowAEntry = (EditText) getDialog().findViewById(R.id.low_a);
                EditText highBEntry = (EditText) getDialog().findViewById(R.id.high_b);
                EditText lowBEntry = (EditText) getDialog().findViewById(R.id.low_b);
                EditText highCEntry = (EditText) getDialog().findViewById(R.id.high_c);
                EditText lowCEntry = (EditText) getDialog().findViewById(R.id.low_c);
                EditText highDEntry = (EditText) getDialog().findViewById(R.id.high_d);
                EditText lowDEntry = (EditText) getDialog().findViewById(R.id.low_d);
                EditText highFEntry = (EditText) getDialog().findViewById(R.id.high_f);
                EditText lowFEntry = (EditText) getDialog().findViewById(R.id.low_f);

                // get user input
                String sectionName = sectionNameEntry.getText().toString();

                SparseArray<Double> assignmentWeights = new SparseArray<>();
                assignmentWeights.put(Section.HOMEWORK,
                        Double.parseDouble(weightHomeworkEntry.getText().toString()));
                assignmentWeights.put(Section.QUIZ,
                        Double.parseDouble(weightQuizzesEntry.getText().toString()));
                assignmentWeights.put(Section.MIDTERM,
                        Double.parseDouble(weightMidtermEntry.getText().toString()));
                assignmentWeights.put(Section.FINAL,
                        Double.parseDouble(weightFinalEntry.getText().toString()));
                assignmentWeights.put(Section.PROJECT,
                        Double.parseDouble(weightProjectEntry.getText().toString()));
                assignmentWeights.put(Section.OTHER,
                        Double.parseDouble(weightOtherEntry.getText().toString()));

                SparseArray<Double> gradeThresholds = new SparseArray<>();
                gradeThresholds.put(Section.HIGH_A, Double.parseDouble(highAEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_A, Double.parseDouble(lowAEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_B, Double.parseDouble(highBEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_B, Double.parseDouble(lowBEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_C, Double.parseDouble(highCEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_C, Double.parseDouble(lowCEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_D, Double.parseDouble(highDEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_D, Double.parseDouble(lowDEntry.getText().toString()));
                gradeThresholds.put(Section.HIGH_F, Double.parseDouble(highFEntry.getText().toString()));
                gradeThresholds.put(Section.LOW_F, Double.parseDouble(lowFEntry.getText().toString()));

                // create new section
                Section section = new Section(sectionName, assignmentWeights, gradeThresholds);

                // add new section
                Academics.getInstance().getCurrentTerms().get(termPosition).addSection(getActivity(),
                        section, termPosition);
                ((TermActivity) getActivity()).updateData();

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
