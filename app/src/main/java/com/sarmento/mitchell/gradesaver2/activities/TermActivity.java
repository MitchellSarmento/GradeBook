package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.SectionAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.SectionDialogFragment;
import com.sarmento.mitchell.gradesaver2.dialogs.TermDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermActivity extends AppCompatActivity {
    int termPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        termPosition = getIntent().getIntExtra("termPosition", -1);
        Term term = Academics.getInstance().getCurrentTerms().get(termPosition);

        ListView sections = (ListView) findViewById(R.id.sections);
        sections.setAdapter(new SectionAdapter(getApplicationContext(), term.getSections()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_section:
                // TODO - SectionDialogFragment().setArguments(Bundle ...) with the termPosition.
                new SectionDialogFragment().show(getFragmentManager(), getString(R.string.action_new_section));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
