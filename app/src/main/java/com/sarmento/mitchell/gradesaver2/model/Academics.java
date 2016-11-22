package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Academics {
    private static Academics instance = null;
    private boolean loaded = false;
    private List<Term> currentTerms;
    private List<Term> archivedTerms;

    private Academics() {
        currentTerms  = new ArrayList<>();
        archivedTerms = new ArrayList<>();
    }

    public static Academics getInstance() {
        if (instance == null) {
            instance = new Academics();
        }
        return instance;
    }

    // load from the database
    public void loadData(Context context) {
        if (!loaded) {
            DBHelper db = new DBHelper(context);
            currentTerms = db.getTerms(false);
            archivedTerms = db.getTerms(true);
            loaded = true;
        }
    }

    public List<Term> getCurrentTerms() {
        return currentTerms;
    }

    public void addTerm(Context context, Term term) {
        DBHelper db = new DBHelper(context);
        currentTerms.add(term);
        db.addTerm(term);
    }
}
