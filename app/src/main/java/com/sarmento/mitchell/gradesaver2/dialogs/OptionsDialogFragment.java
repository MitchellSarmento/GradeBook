package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.sarmento.mitchell.gradesaver2.R;

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

    int itemType;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        itemType = getArguments().getInt(ITEM_TYPE);
        String[] options = getOptions();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int option) {
        switch (itemType) {
            case TERM:
                switch (option) {
                    case TermOptions.EDIT:
                        break;
                    case TermOptions.DELETE:
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
