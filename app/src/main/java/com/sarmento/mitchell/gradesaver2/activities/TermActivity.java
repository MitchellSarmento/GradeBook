package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.SectionAdapter;
import com.sarmento.mitchell.gradesaver2.adapters.TermAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.SectionDialogFragment;
import com.sarmento.mitchell.gradesaver2.dialogs.TermDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.DBHelper;
import com.sarmento.mitchell.gradesaver2.model.Term;

public class TermActivity extends AppCompatActivity {
    private int termPosition;
    private SectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        termPosition = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        Term term = Academics.getInstance().getCurrentTerms().get(termPosition);

        RecyclerView sections = (RecyclerView) findViewById(R.id.sections);
        sections.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SectionAdapter(term.getSections());
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
        inflater.inflate(R.menu.menu_term, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
