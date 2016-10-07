package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.activities.TermActivity;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermButton extends Button implements View.OnClickListener {
    private Context context;
    private int position;

    public TermButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Term term, final int position) {
        this.position = position;
        this.setText(term.getTermName());
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, TermActivity.class);
        intent.putExtra("termPosition", position);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
