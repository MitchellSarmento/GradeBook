package com.sarmento.mitchell.gradesaver2.buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermButton extends Button implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private int termPosition;

    public TermButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Term term, int termPosition) {
        this.termPosition = termPosition;

        String termName = term.getTermName();

        setText(termName);
        setAllCaps(false);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, SectionsActivity.class);
        intent.putExtra(Academics.TERM_POSITION, termPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Academics.TERM_POSITION, termPosition);
        bundle.putInt(OptionsDialogFragment.ITEM_TYPE, OptionsDialogFragment.TERM);
        dialog.setArguments(bundle);
        dialog.show(((Activity) context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}
