package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Database Information
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GradeSaverDB";

    // Tables
    private static final String TABLE_TERMS       = "Terms";
    private static final String TABLE_SECTIONS    = "Sections";
    private static final String TABLE_ASSIGNMENTS = "Assignments";
    private static final String TABLE_DUE_DATES    = "DueDates";

    // TABLE_TERMS columns
    private static final String KEY_TERMS_ID       = "id";
    private static final String KEY_TERMS_NAME     = "name";
    private static final String KEY_TERMS_ARCHIVED = "archived";

    // TABLE_SECTIONS columns
    private static final String KEY_SECTIONS_ID              = "id";
    private static final String KEY_SECTIONS_TERM_ID         = "termId";
    private static final String KEY_SECTIONS_NAME            = "name";
    private static final String KEY_SECTIONS_HIGH_A          = "highA";
    private static final String KEY_SECTIONS_LOW_A           = "lowA";
    private static final String KEY_SECTIONS_HIGH_B          = "highB";
    private static final String KEY_SECTIONS_LOW_B           = "lowB";
    private static final String KEY_SECTIONS_HIGH_C          = "highC";
    private static final String KEY_SECTIONS_LOW_C           = "lowC";
    private static final String KEY_SECTIONS_HIGH_D          = "highD";
    private static final String KEY_SECTIONS_LOW_D           = "lowD";
    private static final String KEY_SECTIONS_HIGH_F          = "highF";
    private static final String KEY_SECTIONS_LOW_F           = "lowF";
    private static final String KEY_SECTIONS_WEIGHT_HOMEWORK = "weightHomework";
    private static final String KEY_SECTIONS_WEIGHT_QUIZ     = "weightQuiz";
    private static final String KEY_SECTIONS_WEIGHT_MIDTERM  = "weightMidterm";
    private static final String KEY_SECTIONS_WEIGHT_FINAL    = "weightFinal";
    private static final String KEY_SECTIONS_WEIGHT_PROJECT  = "weightProject";
    private static final String KEY_SECTIONS_WEIGHT_OTHER    = "weightOther";
    private static final String KEY_SECTIONS_SCORE_HOMEWORK  = "scoreHomework";
    private static final String KEY_SECTIONS_SCORE_QUIZ      = "scoreQuiz";
    private static final String KEY_SECTIONS_SCORE_MIDTERM   = "scoreMidterm";
    private static final String KEY_SECTIONS_SCORE_FINAL     = "scoreFinal";
    private static final String KEY_SECTIONS_SCORE_PROJECT   = "scoreProject";
    private static final String KEY_SECTIONS_SCORE_OTHER     = "scoreOther";
    private static final String KEY_SECTIONS_TOTAL_SCORE     = "totalScore";
    private static final String KEY_SECTIONS_MAX_SCORE       = "maxScore";
    private static final String KEY_SECTIONS_GRADE           = "grade";
    private static final String KEY_SECTIONS_LOCATION        = "location";
    private static final String KEY_SECTIONS_ON_MONDAY       = "onMo";
    private static final String KEY_SECTIONS_ON_TUESDAY      = "onTu";
    private static final String KEY_SECTIONS_ON_WEDNESDAY    = "onWe";
    private static final String KEY_SECTIONS_ON_THURSDAY     = "onTh";
    private static final String KEY_SECTIONS_ON_FRIDAY       = "onFr";
    private static final String KEY_SECTIONS_ON_SATURDAY     = "onSa";
    private static final String KEY_SECTIONS_ON_SUNDAY       = "onSu";

    // TABLE_ASSIGNMENTS columns
    private static final String KEY_ASSIGNMENTS_ID         = "id";
    private static final String KEY_ASSIGNMENTS_SECTION_ID = "sectionId";
    private static final String KEY_ASSIGNMENTS_NAME       = "name";
    private static final String KEY_ASSIGNMENTS_TYPE       = "type";
    private static final String KEY_ASSIGNMENTS_SCORE      = "score";
    private static final String KEY_ASSIGNMENTS_MAX_SCORE  = "maxScore";
    private static final String KEY_ASSIGNMENTS_GRADE      = "grade";

    // TABLE_DUE_DATES columns
    private static final String KEY_DUE_DATES_ID         = "id";
    private static final String KEY_DUE_DATES_SECTION_ID = "sectionId";
    private static final String KEY_DUE_DATES_NAME       = "name";
    private static final String KEY_DUE_DATES_COMPLETE   = "complete";
    private static final String KEY_DUE_DATES_DATE       = "date";

    private static final int TRUE  = 1;
    private static final int FALSE = 0;

    //SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_TERMS = "CREATE TABLE " + TABLE_TERMS + "(" +
                KEY_TERMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TERMS_NAME + " TEXT," +
                KEY_TERMS_ARCHIVED + " TEXT)";

        final String CREATE_TABLE_SECTIONS = "CREATE TABLE " + TABLE_SECTIONS + "(" +
                KEY_SECTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SECTIONS_TERM_ID + " INTEGER," +
                KEY_SECTIONS_NAME + " TEXT," + KEY_SECTIONS_HIGH_A + " REAL," + KEY_SECTIONS_LOW_A + " REAL,"
                + KEY_SECTIONS_HIGH_B + " REAL," + KEY_SECTIONS_LOW_B + " REAL," + KEY_SECTIONS_HIGH_C + " REAL,"
                + KEY_SECTIONS_LOW_C + " REAL," + KEY_SECTIONS_HIGH_D + " REAL," + KEY_SECTIONS_LOW_D + " REAL,"
                + KEY_SECTIONS_HIGH_F + " REAL," + KEY_SECTIONS_LOW_F + " REAL," + KEY_SECTIONS_WEIGHT_HOMEWORK +
                " REAL," + KEY_SECTIONS_WEIGHT_QUIZ + " REAL," + KEY_SECTIONS_WEIGHT_MIDTERM + " REAL," +
                KEY_SECTIONS_WEIGHT_FINAL + " REAL," + KEY_SECTIONS_WEIGHT_PROJECT + " REAL," +
                KEY_SECTIONS_WEIGHT_OTHER + " REAL," + KEY_SECTIONS_SCORE_HOMEWORK + " REAL," +
                KEY_SECTIONS_SCORE_QUIZ + " REAL," + KEY_SECTIONS_SCORE_MIDTERM + " REAL," +
                KEY_SECTIONS_SCORE_FINAL + " REAL," + KEY_SECTIONS_SCORE_PROJECT + " REAL," +
                KEY_SECTIONS_SCORE_OTHER + " REAL," + KEY_SECTIONS_TOTAL_SCORE + " REAL," +
                KEY_SECTIONS_MAX_SCORE + " REAL," + KEY_SECTIONS_GRADE + " TEXT," + KEY_SECTIONS_LOCATION +
                " TEXT," + KEY_SECTIONS_ON_MONDAY + " INTEGER," + KEY_SECTIONS_ON_TUESDAY + " INTEGER," +
                KEY_SECTIONS_ON_WEDNESDAY + " INTEGER," + KEY_SECTIONS_ON_THURSDAY + " INTEGER," +
                KEY_SECTIONS_ON_FRIDAY + " INTEGER," + KEY_SECTIONS_ON_SATURDAY + " INTEGER," +
                KEY_SECTIONS_ON_SUNDAY + " INTEGER)";

        final String CREATE_TABLE_ASSIGNMENTS = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                KEY_ASSIGNMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ASSIGNMENTS_SECTION_ID +
                " INTEGER," + KEY_ASSIGNMENTS_NAME + " TEXT," + KEY_ASSIGNMENTS_TYPE + " TEXT," +
                KEY_ASSIGNMENTS_SCORE + " REAL," + KEY_ASSIGNMENTS_MAX_SCORE + " REAL," +
                KEY_ASSIGNMENTS_GRADE + " TEXT)";

        final String CREATE_TABLE_DUE_DATES = "CREATE TABLE " + TABLE_DUE_DATES + "(" +
                KEY_DUE_DATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DUE_DATES_SECTION_ID +
                " INTEGER," + KEY_DUE_DATES_NAME + " TEXT," + KEY_DUE_DATES_COMPLETE + " INTEGER," +
                KEY_DUE_DATES_DATE + " TEXT)";

        db.execSQL(CREATE_TABLE_TERMS);
        db.execSQL(CREATE_TABLE_SECTIONS);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(CREATE_TABLE_DUE_DATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TERMS_NAME, term.getTermName());
        if (term.isArchived()) {
            values.put(KEY_TERMS_ARCHIVED, TRUE);
        } else {
            values.put(KEY_TERMS_ARCHIVED, FALSE);
        }

        db.insert(TABLE_TERMS, null, values);
        db.close();
    }

    public void addSection(Section section, int termId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SECTIONS_TERM_ID, termId);
        values.put(KEY_SECTIONS_NAME, section.getSectionName());

        SparseArray<Double> gradeThresholds = section.getGradeThresholds();
        values.put(KEY_SECTIONS_HIGH_A, gradeThresholds.get(Section.HIGH_A));
        values.put(KEY_SECTIONS_LOW_A, gradeThresholds.get(Section.LOW_A));
        values.put(KEY_SECTIONS_HIGH_B, gradeThresholds.get(Section.HIGH_B));
        values.put(KEY_SECTIONS_LOW_B, gradeThresholds.get(Section.LOW_B));
        values.put(KEY_SECTIONS_HIGH_C, gradeThresholds.get(Section.HIGH_C));
        values.put(KEY_SECTIONS_LOW_C, gradeThresholds.get(Section.LOW_C));
        values.put(KEY_SECTIONS_HIGH_D, gradeThresholds.get(Section.HIGH_D));
        values.put(KEY_SECTIONS_LOW_D, gradeThresholds.get(Section.LOW_D));
        values.put(KEY_SECTIONS_HIGH_F, gradeThresholds.get(Section.HIGH_F));
        values.put(KEY_SECTIONS_LOW_F, gradeThresholds.get(Section.LOW_F));

        SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
        values.put(KEY_SECTIONS_WEIGHT_HOMEWORK, assignmentWeights.get(Section.HOMEWORK));
        values.put(KEY_SECTIONS_WEIGHT_QUIZ, assignmentWeights.get(Section.QUIZ));
        values.put(KEY_SECTIONS_WEIGHT_MIDTERM, assignmentWeights.get(Section.MIDTERM));
        values.put(KEY_SECTIONS_WEIGHT_FINAL, assignmentWeights.get(Section.FINAL));
        values.put(KEY_SECTIONS_WEIGHT_PROJECT, assignmentWeights.get(Section.PROJECT));
        values.put(KEY_SECTIONS_WEIGHT_OTHER, assignmentWeights.get(Section.OTHER));

        SparseArray<Double> scores = section.getScores();
        values.put(KEY_SECTIONS_SCORE_HOMEWORK, scores.get(Section.HOMEWORK));
        values.put(KEY_SECTIONS_SCORE_QUIZ, scores.get(Section.QUIZ));
        values.put(KEY_SECTIONS_SCORE_MIDTERM, scores.get(Section.MIDTERM));
        values.put(KEY_SECTIONS_SCORE_FINAL, scores.get(Section.FINAL));
        values.put(KEY_SECTIONS_SCORE_PROJECT, scores.get(Section.PROJECT));
        values.put(KEY_SECTIONS_SCORE_OTHER, scores.get(Section.OTHER));
        values.put(KEY_SECTIONS_TOTAL_SCORE, section.getTotalScore());
        values.put(KEY_SECTIONS_MAX_SCORE, section.getMaxScore());
        values.put(KEY_SECTIONS_GRADE, section.getGrade());

        db.insert(TABLE_SECTIONS, null, values);
        db.close();
    }

    public List<Term> getTerms(boolean archived) {
        SQLiteDatabase db = getReadableDatabase();
        List<Term> terms = new ArrayList<>();

        int isArchived = FALSE;
        if (archived) {
            isArchived = TRUE;
        }

        String query = "SELECT * FROM " + TABLE_TERMS + " WHERE " + KEY_TERMS_ARCHIVED + " = " + isArchived;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                int termId = cursor.getInt(0);
                String termName = cursor.getString(1);

                // get the sections belonging to this term
                List<Section> sections = getSections(termId);

                // create and add the term
                Term term = new Term(termName, archived, sections);
                terms.add(term);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return terms;
    }

    private List<Section> getSections(int termId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Section> sections = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SECTIONS + " WHERE " + KEY_SECTIONS_TERM_ID + " = " + termId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                int sectionId = cursor.getInt(0);
                String sectionName = cursor.getString(2);
                SparseArray<Double> gradeThresholds = new SparseArray<>();
                for (int i = 0; i < 10; i++) {
                    gradeThresholds.put(i, cursor.getDouble(i+3));
                }
                SparseArray<Double> assignmentWeights = new SparseArray<>();
                for (int i = 0; i < 6; i++) {
                    assignmentWeights.put(i, cursor.getDouble(i+13));
                }
                SparseArray<Double> scores = new SparseArray<>();
                for (int i = 0; i < 6; i++) {
                    scores.put(i, cursor.getDouble(i+19));
                }
                double totalScore = cursor.getDouble(25);
                double maxScore = cursor.getDouble(26);
                String grade = cursor.getString(27);

                // get the assignments belonging to this section
                List<Assignment> assignments = getAssignments(sectionId);

                // get the due dates belonging to this section
                List<DueDate> dueDates = getDueDates(sectionId);

                // create and add the section
                Section section = new Section(sectionName, gradeThresholds, assignmentWeights, scores, totalScore,
                        maxScore, grade, assignments, dueDates);
                sections.add(section);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sections;
    }

    private List<Assignment> getAssignments(int sectionId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Assignment> assignments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE " + KEY_ASSIGNMENTS_SECTION_ID +
                " = " + sectionId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                String assignmentName = cursor.getString(2);
                String assignmentType = cursor.getString(3);
                double score = cursor.getDouble(4);
                double maxScore = cursor.getDouble(5);
                String grade = cursor.getString(6);

                // create and add the assignment
                Assignment assignment = new Assignment(assignmentName, assignmentType, score, maxScore, grade);
                assignments.add(assignment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return assignments;
    }

    private List<DueDate> getDueDates(int sectionId) {
        SQLiteDatabase db = getReadableDatabase();
        List<DueDate> dueDates = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DUE_DATES + " WHERE " + KEY_DUE_DATES_SECTION_ID +
                " = " + sectionId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information form the database
                String dueDateName = cursor.getString(2);
                boolean complete = false;
                if (cursor.getInt(3) == TRUE) {
                    complete = true;
                }
                String dateString = cursor.getString(4);

                // convert dateString to Date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // create and add the due date
                DueDate dueDate = new DueDate(dueDateName, complete, date);
                dueDates.add(dueDate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dueDates;
    }
}
