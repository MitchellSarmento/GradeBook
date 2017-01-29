package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;

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
        db.updateTerm(updateValues, termPosition);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Context context, Section section, int termPosition, int sectionPosition) {
        sections.add(section);

        // update the Term in the database
        DBHelper db = new DBHelper(context);
        db.addSection(section, termPosition, sectionPosition);
    }

    public void removeSection(Context context, int termPosition, int sectionPosition) {
        List<Assignment> assignments = sections.get(sectionPosition).getAssignments();
        for (int i = 0; i < assignments.size(); i++) {
            assignments.get(i).deleteAllImages();
        }
        sections.remove(sectionPosition);

        // update the Term in the database
        DBHelper db = new DBHelper(context);
        db.removeSection(termPosition, sectionPosition);
    }

    public void updateSection(Context context, String sectionName,
                              SparseArray<Double> assignmentWeights,
                              SparseArray<Double> gradeThresholds, int termPosition,
                              int sectionPosition) {
        // get the Section to be updated
        Section section = sections.get(sectionPosition);

        // update the Section information
        section.setSectionName(sectionName);
        section.setAssignmentWeights(assignmentWeights);
        section.setGradeThresholds(gradeThresholds);

        // recalculate the overall Section grade
        section.calculateSectionGrade();

        // recalculate the grades of Assignments belonging to this Section
        List<Assignment> assignments = section.getAssignments();
        for (Assignment assignment : assignments) {
            assignment.setGrade(section.calculateAssignmentGrade(assignment.getScore(),
                    assignment.getMaxScore()));
        }

        // update the Section in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_SECTIONS_NAME, sectionName);
        SparseArray<Double> scores    = section.getScores();
        SparseArray<Double> maxScores = section.getMaxScores();
        for (Section.AssignmentType type : Section.AssignmentType.values()) {
            int typeValue = type.getValue();
            updateValues.put(DBHelper.KEY_SECTIONS_WEIGHTS[typeValue],
                    assignmentWeights.get(typeValue));
            updateValues.put(DBHelper.KEY_SECTIONS_SCORES[typeValue*2],
                    scores.get(typeValue));
            updateValues.put(DBHelper.KEY_SECTIONS_SCORES[typeValue*2+1],
                    maxScores.get(typeValue));
        }
        for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
            int thresholdValue = threshold.getValue();
            updateValues.put(DBHelper.KEY_SECTIONS_GRADE_THRESHOLDS[thresholdValue],
                    gradeThresholds.get(thresholdValue));
        }
        db.updateSection(updateValues, termPosition, sectionPosition);
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
