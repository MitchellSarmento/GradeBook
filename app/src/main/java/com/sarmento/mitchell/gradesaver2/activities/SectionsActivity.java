package com.sarmento.mitchell.gradesaver2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.SectionAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.SectionDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class SectionsActivity extends AppCompatActivity {
    private int termPosition;
    private SectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);

        Term term = Academics.getInstance().getCurrentTerms().get(termPosition);
        setTitle(term.getTermName());

        RecyclerView sections = (RecyclerView) findViewById(R.id.sections);
        sections.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SectionAdapter(term.getSections(), termPosition);
        sections.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    public int getTermPosition() {
        return termPosition;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_section:
                SectionDialogFragment dialog = new SectionDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Academics.TERM_POSITION, termPosition);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), getString(R.string.action_new_section));
                return true;
            case R.id.action_schedule:
                Intent intent = new Intent(this, ScheduleActivity.class);
                intent.putExtra(Academics.TERM_POSITION, termPosition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
