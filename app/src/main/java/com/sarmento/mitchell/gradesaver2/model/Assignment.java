package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Assignment {
    private String assignmentName;
    private String assignmentType;
    private double score;
    private double maxScore;
    private String grade;
    private List<String> imagePaths;

    // constructor for creating a new Assignment
    public Assignment(String assignmentName, String assignmentType, double score, double maxScore,
                      String grade) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.score          = score;
        this.maxScore       = maxScore;
        this.grade          = grade;
        imagePaths          = new LinkedList<>();
    }

    // constructor for loading an existing Assignment
    public Assignment(String assignmentName, String assignmentType, double score, double maxScore,
                      String grade, String imagePaths) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.score          = score;
        this.maxScore       = maxScore;
        this.grade          = grade;

        if (imagePaths != null) {
            String[] paths = imagePaths.split(", ");
            this.imagePaths = new LinkedList<>(Arrays.asList(paths));
        } else {
            this.imagePaths = new LinkedList<>();
        }
    }

    public void updateAssignment(Context context, String assignmentName,
                                 double score, double maxScore, String assignmentType,
                                 int termPosition, int sectionPosition, int assignmentPosition) {
        this.assignmentName = assignmentName;
        this.score          = score;
        this.maxScore       = maxScore;
        this.assignmentType = assignmentType;

        // update the Assignment in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_NAME, assignmentName);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_SCORE, score);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_MAX_SCORE, maxScore);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_TYPE, assignmentType);
        db.updateAssignment(updateValues, termPosition, sectionPosition, assignmentPosition);
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public double getScore() {
        return score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public String getGrade() {
        return grade;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void addImagePath(Context context, String path, int termPosition, int sectionPosition,
                             int assignmentPosition) {
        imagePaths.add(path);

        // update the assignment in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        String[] paths = imagePaths.toArray(new String[0]);
        String pathsString = Arrays.toString(paths);
        pathsString = pathsString.substring(1, pathsString.length()-1);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_IMAGE_PATHS, pathsString);
        db.updateAssignment(updateValues, termPosition, sectionPosition, assignmentPosition);
    }

    public void removeImagePath(Context context, int imagePosition, int termPosition,
                                int sectionPosition, int assignmentPosition) {
        // delete the picture from the device
        File imageToDelete = new File(imagePaths.get(imagePosition));
        imageToDelete.delete();
        
        // delete the path
        imagePaths.remove(imagePosition);

        // update the assignment in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        String[] paths = imagePaths.toArray(new String[0]);
        String pathsString = Arrays.toString(paths);
        pathsString = pathsString.substring(1, pathsString.length()-1);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_IMAGE_PATHS, pathsString);
        db.updateAssignment(updateValues, termPosition, sectionPosition, assignmentPosition);
    }
}
