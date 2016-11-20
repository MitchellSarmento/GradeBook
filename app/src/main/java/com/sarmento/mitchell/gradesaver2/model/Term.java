package com.sarmento.mitchell.gradesaver2.model;


import java.util.ArrayList;
import java.util.List;

public class Term {
    private String termName;
    private List<Section> sections;

    public Term(String termName) {
        this.termName = termName;
        sections = new ArrayList<>();
    }

    public String getTermName() {
        return termName;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }
}
