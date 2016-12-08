package com.sarmento.mitchell.gradesaver2.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentImagesActivity;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class ScrollImageView extends ImageView implements View.OnClickListener,
        View.OnLongClickListener {
    private Context context;
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private int imagePosition;

    public ScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(int termPosition, int sectionPosition,
                     int assignmentPosition, int imagePosition) {
        this.termPosition       = termPosition;
        this.sectionPosition    = sectionPosition;
        this.assignmentPosition = assignmentPosition;
        this.imagePosition      = imagePosition;

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((AssignmentImagesActivity) context).setImageMain(imagePosition);
    }

    @Override
    public boolean onLongClick(View v) {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Academics.TERM_POSITION, termPosition);
        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
        bundle.putInt(Academics.ASSIGNMENT_POSITION, assignmentPosition);
        bundle.putInt(Academics.ASSIGNMENT_IMAGE_POSITION, imagePosition);
        bundle.putInt(OptionsDialogFragment.ITEM_TYPE, OptionsDialogFragment.ASSIGNMENT_IMAGE);
        dialog.setArguments(bundle);
        dialog.show(((Activity)context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}
