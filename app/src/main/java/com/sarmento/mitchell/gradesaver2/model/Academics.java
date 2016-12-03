package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Academics {
    public static final String TERM_POSITION       = "termPosition";
    public static final String SECTION_POSITION    = "sectionPosition";
    public static final String ASSIGNMENT_POSITION = "assignmentPosition";
    public static final String DUE_DATE_POSITION   = "dueDatePosition";

    private static Academics instance = null;
    private boolean loaded = false;
    private List<Term> currentTerms;
    private List<Term> archivedTerms;

    private Academics() {
        currentTerms  = new ArrayList<>();
        archivedTerms = new ArrayList<>();
    }

    /*
     * Get an instance of Academics and load from the database if data has not already been
     * loaded during this session.
     * This method should be called once when the application starts, after which getInstance()
     * should be used.
     */
    public static Academics getInstance(Context context) {
        if (instance == null) {
            instance = new Academics();
        }
        instance.loadData(context);
        return instance;
    }

    // get an instance of Academics without loading from the database
    public static Academics getInstance() {
        if (instance == null) {
            instance = new Academics();
        }
        return instance;
    }

    // load from the database
    private void loadData(Context context) {
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

    public void addTerm(Context context, Term term, int termPosition) {
        DBHelper db = new DBHelper(context);
        currentTerms.add(term);
        db.addTerm(term, termPosition);
    }

    public void removeTerm(Context context, int termPosition) {
        DBHelper db = new DBHelper(context);
        currentTerms.remove(termPosition);
        db.removeTerm(termPosition);
    }
}
