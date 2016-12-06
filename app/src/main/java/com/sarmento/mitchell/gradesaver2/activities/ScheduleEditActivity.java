package com.sarmento.mitchell.gradesaver2.activities;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class ScheduleEditActivity extends AppCompatActivity {
    private ScheduleEditAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        int termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);

        Term term = Academics.getInstance().getCurrentTerms().get(termPosition);
        setTitle(term.getTermName());

        RecyclerView schedules = (RecyclerView) findViewById(R.id.schedules);
        schedules.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScheduleEditAdapter(this, term.getSections(), termPosition);
        schedules.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        adapter.closeAll();
        super.onBackPressed();
    }

    public void pickTime(final View v) {
        final boolean is24HourFormat = DateFormat.is24HourFormat(this);

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                ((EditText) v).setText(Schedule.timeToString(hour, minute, is24HourFormat));
            }
        }, 0, 0, is24HourFormat).show();
    }
}
