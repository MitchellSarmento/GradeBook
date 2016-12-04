package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.activities.DueDatesActivity;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class OptionsDialogFragment extends DialogFragment implements Dialog.OnClickListener {
    public static final String ITEM_TYPE  = "itemType";

    public static final int TERM       = 0;
    public static final int SECTION    = 1;
    public static final int ASSIGNMENT = 2;
    public static final int DUE_DATE   = 3;

    public static final String EDITING = "editing";

    private static class TermOptions {
        private static final int EDIT    = 0;
        private static final int DELETE  = 1;
        private static final int ARCHIVE = 2;
    }

    private static class SectionOptions {
        private static final int EDIT            = 0;
        private static final int DELETE          = 1;
        private static final int SET_FINAL_GRADE = 2;
    }

    private static class AssignmentOptions {
        private static final int EDIT   = 0;
        private static final int DELETE = 1;
    }

    private static class DueDateOptions {
        private static final int EDIT   = 0;
        private static final int DELETE = 1;
    }

    private Activity context;
    private int itemType;
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private int dueDatePosition;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        context = getActivity();
        itemType = getArguments().getInt(ITEM_TYPE);
        getPositions();
        String[] options = getOptions();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, this);

        return builder.create();
    }

    private void getPositions() {
        switch (itemType) {
            case TERM:
                termPosition = getArguments().getInt(Academics.TERM_POSITION);
                break;
            case SECTION:
                termPosition    = getArguments().getInt(Academics.TERM_POSITION);
                sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);
                break;
            case ASSIGNMENT:
                termPosition       = getArguments().getInt(Academics.TERM_POSITION);
                sectionPosition    = getArguments().getInt(Academics.SECTION_POSITION);
                assignmentPosition = getArguments().getInt(Academics.ASSIGNMENT_POSITION);
                break;
            case DUE_DATE:
                termPosition    = getArguments().getInt(Academics.TERM_POSITION);
                sectionPosition = getArguments().getInt(Academics.SECTION_POSITION);
                dueDatePosition = getArguments().getInt(Academics.DUE_DATE_POSITION);
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int option) {
        DialogFragment editDialog = null;
        Bundle bundle = new Bundle();
        DialogInterface.OnClickListener confirmAction = null;
        boolean editing = false;
        boolean showConfirmationDialog = false;

        switch (itemType) {
            case TERM:
                switch (option) {
                    case TermOptions.EDIT:
                        editing = true;
                        editDialog = new TermDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        break;
                    case TermOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Academics.getInstance().removeTerm(context, termPosition);
                                ((TermsActivity) context).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                    case TermOptions.ARCHIVE:
                        break;
                }
                break;
            case SECTION:
                switch (option) {
                    case SectionOptions.EDIT:
                        editing = true;
                        editDialog = new SectionDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                        break;
                    case SectionOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Academics.getInstance().getCurrentTerms().get(termPosition)
                                        .removeSection(context, termPosition, sectionPosition);
                                ((SectionsActivity) context).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                    case SectionOptions.SET_FINAL_GRADE:
                        break;
                }
                break;
            case ASSIGNMENT:
                switch (option) {
                    case AssignmentOptions.EDIT:
                        editing = true;
                        editDialog = new AssignmentDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                        bundle.putInt(Academics.ASSIGNMENT_POSITION, assignmentPosition);
                        break;
                    case AssignmentOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Academics.getInstance().getCurrentTerms().get(termPosition)
                                        .getSections().get(sectionPosition)
                                        .removeAssignment(context, termPosition,
                                                sectionPosition, assignmentPosition);
                                ((AssignmentsActivity) context).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                }
                break;
            case DUE_DATE:
                switch (option) {
                    case DueDateOptions.EDIT:
                        editing = true;
                        editDialog = new DueDateDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                        bundle.putInt(Academics.DUE_DATE_POSITION, dueDatePosition);
                        break;
                    case DueDateOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Academics.getInstance().getCurrentTerms().get(termPosition)
                                        .getSections().get(sectionPosition)
                                        .removeDueDate(context, termPosition,
                                                sectionPosition, dueDatePosition);
                                ((DueDatesActivity) context).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                }
                break;
        }

        if (showConfirmationDialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.prompt_delete);
            builder.setNegativeButton(getString(R.string.cancel), new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(getString(R.string.confirm), confirmAction);
            builder.create();
            builder.show();
        } else if (editing) {
            bundle.putBoolean(EDITING, true);
            editDialog.setArguments(bundle);
            editDialog.show(getFragmentManager(), getString(R.string.options));
        }
    }

    private String[] getOptions() {
        switch (itemType) {
            case TERM:
                return getResources().getStringArray(R.array.options_term);
            case SECTION:
                return getResources().getStringArray(R.array.options_section);
            case ASSIGNMENT:
                return getResources().getStringArray(R.array.options_assignment);
            case DUE_DATE:
                return getResources().getStringArray(R.array.options_due_date);
            default:
                return null;
        }
    }
}
