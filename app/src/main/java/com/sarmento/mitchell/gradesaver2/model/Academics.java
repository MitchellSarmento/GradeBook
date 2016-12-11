package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Academics {
    public static final String TERM_POSITION             = "termPosition";
    public static final String SECTION_POSITION          = "sectionPosition";
    public static final String ASSIGNMENT_POSITION       = "assignmentPosition";
    public static final String ASSIGNMENT_IMAGE_POSITION = "assignmentImagePosition";
    public static final String DUE_DATE_POSITION         = "dueDatePosition";

    private static Academics instance = null;
    private boolean loaded = false;
    private boolean inArchive = false;
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
            DBHelper db   = new DBHelper(context);
            currentTerms  = db.getTerms(inArchive);
            inArchive     = !inArchive;
            db            = new DBHelper(context);
            archivedTerms = db.getTerms(inArchive);
            inArchive     = !inArchive;
            loaded        = true;
        }
    }

    public List<Term> getCurrentTerms() {
        return currentTerms;
    }

    public List<Term> getArchivedTerms() {
        return archivedTerms;
    }

    public void addTerm(Context context, Term term, int termPosition) {
        currentTerms.add(term);

        DBHelper db = new DBHelper(context);
        db.addTerm(term, termPosition);
    }

    public void removeTerm(Context context, int termPosition, boolean archived) {
        if (archived) {
            archivedTerms.remove(termPosition);
        } else {
            currentTerms.remove(termPosition);
        }

        DBHelper db = new DBHelper(context);
        db.removeTerm(termPosition, archived);
    }

    public boolean inArchive() {
        return inArchive;
    }

    public void setInArchive(boolean inArchive) {
        this.inArchive = inArchive;
    }

    public void setTermIsArchived(Context context, boolean archiving, int termPosition) {
        DBHelper db = new DBHelper(context);
        ContentValues updateValues;
        Term term;
        int newPosition;

        if (archiving) {
            term        = currentTerms.get(termPosition);
            newPosition = archivedTerms.size();

            currentTerms.remove(termPosition);
            archivedTerms.add(term);
        } else {
            term        = archivedTerms.get(termPosition);
            newPosition = currentTerms.size();

            archivedTerms.remove(termPosition);
            currentTerms.add(term);
        }

        term.setArchived(archiving);
        updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_TERMS_ARCHIVED, archiving);
        updateValues.put(DBHelper.KEY_TERMS_ID, newPosition);
        db.updateTerm(updateValues, termPosition);
    }
}
