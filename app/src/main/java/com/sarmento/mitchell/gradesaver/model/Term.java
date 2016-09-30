package com.sarmento.mitchell.gradesaver.model;


import java.util.ArrayList;
import java.util.List;

public class Term {
    private String termName;
    private List<Section> sections;

    public Term(String termName) {
        this.termName = termName;
        sections = new ArrayList<>();
    }
}
