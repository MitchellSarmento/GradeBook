package com.sarmento.mitchell.gradesaver2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentImagesActivity;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.activities.DueDatesActivity;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.widgets.DueDatesWidgetProvider;

public class OptionsDialogFragment extends DialogFragment implements Dialog.OnClickListener {
    public static final String ITEM_TYPE  = "itemType";

    public static final int TERM             = 0;
    public static final int TERM_ARCHIVED    = 1;
    public static final int SECTION          = 2;
    public static final int ASSIGNMENT       = 3;
    public static final int ASSIGNMENT_IMAGE = 4;
    public static final int DUE_DATE         = 5;

    public static final String EDITING = "editing";

    private static class TermOptions {
        private static final int EDIT    = 0;
        private static final int DELETE  = 1;
        private static final int ARCHIVE = 2;
    }

    private static class TermArchivedOptions {
        private static final int DELETE    = 0;
        private static final int UNARCHIVE = 1;
    }

    private static class SectionOptions {
        private static final int EDIT            = 0;
        private static final int DELETE          = 1;
        private static final int SET_FINAL_GRADE = 2;
    }

    private static class AssignmentOptions {
        private static final int EDIT           = 0;
        private static final int DELETE         = 1;
    }

    private static class AssignmentImageOptions {
        private static final int DELETE     = 0;
    }

    private static class AssignmentImageArchiveOptions {
        private static final int PROPERTIES = 0;
    }

    private static class DueDateOptions {
        private static final int EDIT   = 0;
        private static final int DELETE = 1;
    }

    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private int assignmentImagePosition;
    private int dueDatePosition;

    private Activity activity;
    private Bundle arguments;
    private int itemType;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        activity  = getActivity();
        arguments = getArguments();
        itemType  = arguments.getInt(ITEM_TYPE);

        getPositions();
        String[] options = getOptions();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(options, this);

        return builder.create();
    }

    private void getPositions() {
        switch (itemType) {
            case TERM:
                termPosition = arguments.getInt(Academics.TERM_POSITION);
                break;
            case TERM_ARCHIVED:
                termPosition = arguments.getInt(Academics.TERM_POSITION);
                break;
            case SECTION:
                termPosition    = arguments.getInt(Academics.TERM_POSITION);
                sectionPosition = arguments.getInt(Academics.SECTION_POSITION);
                break;
            case ASSIGNMENT:
                termPosition       = arguments.getInt(Academics.TERM_POSITION);
                sectionPosition    = arguments.getInt(Academics.SECTION_POSITION);
                assignmentPosition = arguments.getInt(Academics.ASSIGNMENT_POSITION);
                break;
            case ASSIGNMENT_IMAGE:
                termPosition            = arguments.getInt(Academics.TERM_POSITION);
                sectionPosition         = arguments.getInt(Academics.SECTION_POSITION);
                assignmentPosition      = arguments.getInt(Academics.ASSIGNMENT_POSITION);
                assignmentImagePosition = arguments.getInt(Academics.ASSIGNMENT_IMAGE_POSITION);
                break;
            case DUE_DATE:
                termPosition    = arguments.getInt(Academics.TERM_POSITION);
                sectionPosition = arguments.getInt(Academics.SECTION_POSITION);
                dueDatePosition = arguments.getInt(Academics.DUE_DATE_POSITION);
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int option) {
        boolean editing                               = false;
        DialogFragment editDialog                     = null;
        boolean showConfirmationDialog                = false;
        DialogInterface.OnClickListener confirmAction = null;
        Bundle bundle                                 = new Bundle();

        switch (itemType) {
            case TERM:
                switch (option) {
                    case TermOptions.EDIT:
                        editing    = true;
                        editDialog = new TermDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        break;
                    case TermOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                academics.removeTerm(activity, termPosition, false);
                                ((TermsActivity) activity).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                    case TermOptions.ARCHIVE:
                        academics.setTermIsArchived(activity, true, termPosition);
                        ((TermsActivity) activity).updateList();
                        break;
                }
                break;
            case TERM_ARCHIVED:
                switch (option) {
                    case TermArchivedOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                academics.removeTerm(activity, termPosition, true);
                                ((TermsActivity) activity).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                    case TermArchivedOptions.UNARCHIVE:
                        academics.setTermIsArchived(activity, false, termPosition);
                        ((TermsActivity) activity).updateList();
                        break;
                }
                break;
            case SECTION:
                switch (option) {
                    case SectionOptions.EDIT:
                        editing    = true;
                        editDialog = new SectionDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                        break;
                    case SectionOptions.DELETE:
                        showConfirmationDialog = true;
                        confirmAction = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                academics.getCurrentTerms().get(termPosition)
                                        .removeSection(activity, termPosition, sectionPosition);
                                ((SectionsActivity) activity).updateList();
                                dialog.dismiss();
                                DueDatesWidgetProvider.updateWidget(activity, false);
                            }
                        };
                        break;
                    case SectionOptions.SET_FINAL_GRADE:
                        editing    = true;
                        editDialog = new FinalGradeDialogFragment();
                        bundle.putInt(Academics.TERM_POSITION, termPosition);
                        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
                        break;
                }
                break;
            case ASSIGNMENT:
                switch (option) {
                    case AssignmentOptions.EDIT:
                        editing    = true;
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
                                academics.getCurrentTerms().get(termPosition)
                                        .getSections().get(sectionPosition)
                                        .removeAssignment(activity, termPosition,
                                                sectionPosition, assignmentPosition);
                                ((AssignmentsActivity) activity).updateList();
                                dialog.dismiss();
                            }
                        };
                        break;
                }
                break;
            case ASSIGNMENT_IMAGE:
                if (academics.inArchive()) {
                    switch (option) {
                        case AssignmentImageArchiveOptions.PROPERTIES:
                            break;
                    }
                } else {
                    switch (option) {
                        case AssignmentImageOptions.DELETE:
                            showConfirmationDialog = true;
                            confirmAction = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    academics.getCurrentTerms().get(termPosition)
                                            .getSections().get(sectionPosition)
                                            .getAssignments().get(assignmentPosition)
                                            .removeImagePath(activity, assignmentImagePosition,
                                                    termPosition, sectionPosition, assignmentPosition);
                                    ((AssignmentImagesActivity) activity)
                                            .deleteView(assignmentImagePosition);
                                }
                            };
                            break;
                    }
                }
                break;
            case DUE_DATE:
                switch (option) {
                    case DueDateOptions.EDIT:
                        editing    = true;
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
                                academics.getCurrentTerms().get(termPosition)
                                        .getSections().get(sectionPosition)
                                        .removeDueDate(activity, termPosition,
                                                sectionPosition, dueDatePosition);
                                ((DueDatesActivity) activity).updateList();
                                dialog.dismiss();
                                DueDatesWidgetProvider.updateWidget(activity, false);
                            }
                        };
                        break;
                }
                break;
        }

        if (showConfirmationDialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
            case TERM_ARCHIVED:
                return getResources().getStringArray(R.array.options_term_archived);
            case SECTION:
                return getResources().getStringArray(R.array.options_section);
            case ASSIGNMENT:
                return getResources().getStringArray(R.array.options_assignment);
            case ASSIGNMENT_IMAGE:
                if (academics.inArchive()) {
                    return getResources().getStringArray(R.array.options_assignment_image_archived);
                } else {
                    return getResources().getStringArray(R.array.options_assignment_image);
                }
            case DUE_DATE:
                return getResources().getStringArray(R.array.options_due_date);
            default:
                return null;
        }
    }
}
