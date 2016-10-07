package com.sarmento.mitchell.gradesaver2.model;


import java.util.ArrayList;
import java.util.List;

public class Academics {
    private static Academics instance = null;
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

    public List<Term> getCurrentTerms() {
        return currentTerms;
    }
}
