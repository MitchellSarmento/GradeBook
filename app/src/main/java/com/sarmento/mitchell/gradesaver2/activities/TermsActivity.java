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
import com.sarmento.mitchell.gradesaver2.adapters.TermAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.TermDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class TermsActivity extends AppCompatActivity {
    private Academics academics;

    private RecyclerView terms;
    private TermAdapter currentAdapter;
    private TermAdapter archiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        // load data
        academics = Academics.getInstance(this);

        terms = (RecyclerView) findViewById(R.id.current_terms);
        terms.setLayoutManager(new LinearLayoutManager(this));

        currentAdapter = new TermAdapter(academics.getCurrentTerms());
        archiveAdapter = new TermAdapter(academics.getArchivedTerms());

        assignAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onBackPressed() {
        if (academics.inArchive()) {
            academics.setInArchive(false);
            invalidateOptionsMenu();
            assignAdapter();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!academics.inArchive()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_terms, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_term:
                new TermDialogFragment().show(getFragmentManager(), getString(R.string.action_new_term));
                return true;
            case R.id.action_view_archive:
                academics.setInArchive(true);
                invalidateOptionsMenu();
                assignAdapter();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void assignAdapter() {
        if (academics.inArchive()) {
            terms.setAdapter(archiveAdapter);
            setTitle(getString(R.string.archive));
        } else {
            terms.setAdapter(currentAdapter);
            setTitle(getString(R.string.app_name));
        }
    }

    public void updateList() {
        if (academics.inArchive()) {
            archiveAdapter.notifyDataSetChanged();
        } else {
            currentAdapter.notifyDataSetChanged();
        }
    }
}
