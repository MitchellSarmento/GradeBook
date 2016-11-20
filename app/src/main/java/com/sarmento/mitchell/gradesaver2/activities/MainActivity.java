package com.sarmento.mitchell.gradesaver2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.TermAdapter;
import com.sarmento.mitchell.gradesaver2.dialogs.TermDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;

public class MainActivity extends AppCompatActivity {
    Academics academics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        academics = Academics.getInstance();

        ListView currentTerms = (ListView) findViewById(R.id.current_terms);
        currentTerms.setAdapter(new TermAdapter(getApplicationContext(), academics.getCurrentTerms()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_term:
                new TermDialogFragment().show(getFragmentManager(), getString(R.string.action_new_term));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
