package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Term {
    private String termName;
    private boolean archived;
    private List<Section> sections;

    // constructor for creating a new term
    public Term(String termName) {
        this.termName = termName;
        archived      = false;
        sections      = new ArrayList<>();
    }

    // constructor for loading an existing term
    public Term(String termName, boolean archived, List<Section> sections) {
        this.termName = termName;
        this.archived = archived;
        this.sections = sections;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(Context context, String termName, int termPosition) {
        this.termName = termName;

        // update the Term in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_TERMS_NAME, termName);
        db.updateTerm(updateValues, termPosition, archived);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Context context, Section section, int termPosition, int sectionPosition) {
        sections.add(section);

        DBHelper db = new DBHelper(context);
        db.addSection(section, termPosition, sectionPosition);
    }

    public void removeSection(Context context, int termPosition, int sectionPosition) {
        sections.remove(sectionPosition);

        DBHelper db = new DBHelper(context);
        db.removeSection(termPosition, sectionPosition);
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
