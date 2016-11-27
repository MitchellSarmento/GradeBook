package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class OptionsDialogFragment extends DialogFragment implements Dialog.OnClickListener {
    public static final String ITEM_TYPE  = "itemType";

    public static final int TERM       = 0;
    public static final int SECTION    = 1;
    public static final int ASSIGNMENT = 2;
    public static final int DUE_DATE   = 3;

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

    private int itemType;
    private Activity context;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        context = getActivity();
        itemType = getArguments().getInt(ITEM_TYPE);
        String[] options = getOptions();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int option) {
        DialogInterface.OnClickListener confirmAction = null;
        boolean showConfirmationDialog = false;

        switch (itemType) {
            case TERM:
                switch (option) {
                    case TermOptions.EDIT:
                        break;
                    case TermOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                int termPosition = getArguments().getInt(Academics.TERM_POSITION);
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
                        break;
                    case SectionOptions.DELETE:
                        break;
                    case SectionOptions.SET_FINAL_GRADE:
                        break;
                }
                break;
            case ASSIGNMENT:
                switch (option) {
                    case AssignmentOptions.EDIT:
                        break;
                    case AssignmentOptions.DELETE:
                        break;
                }
                break;
            case DUE_DATE:
                switch (option) {
                    case DueDateOptions.EDIT:
                        break;
                    case DueDateOptions.DELETE:
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
