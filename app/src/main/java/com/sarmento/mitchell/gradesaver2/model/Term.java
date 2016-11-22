package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Term {
    private String termName;
    private boolean archived;
    private List<Section> sections;

    // constructor for create a new term
    public Term(String termName) {
        this.termName = termName;
        archived = false;
        sections = new ArrayList<>();
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

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Context context, Section section, int termPosition) {
        DBHelper db = new DBHelper(context);
        sections.add(section);
        db.addSection(section, termPosition+1);
    }

    public boolean isArchived() {
        return archived;
    }
}
