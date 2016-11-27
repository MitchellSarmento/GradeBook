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
import java.util.Calendar;
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
    public static final String KEY_TERMS_ID       = "id";
    public static final String KEY_TERMS_NAME     = "name";
    public static final String KEY_TERMS_ARCHIVED = "archived";

    // TABLE_SECTIONS columns
    public static final String KEY_SECTIONS_ID                 = "id";
    public static final String KEY_SECTIONS_TERM_ID            = "termId";
    public static final String KEY_SECTIONS_NAME               = "name";
    public static final String KEY_SECTIONS_HIGH_A             = "highA";
    public static final String KEY_SECTIONS_LOW_A              = "lowA";
    public static final String KEY_SECTIONS_HIGH_B             = "highB";
    public static final String KEY_SECTIONS_LOW_B              = "lowB";
    public static final String KEY_SECTIONS_HIGH_C             = "highC";
    public static final String KEY_SECTIONS_LOW_C              = "lowC";
    public static final String KEY_SECTIONS_HIGH_D             = "highD";
    public static final String KEY_SECTIONS_LOW_D              = "lowD";
    public static final String KEY_SECTIONS_HIGH_F             = "highF";
    public static final String KEY_SECTIONS_LOW_F              = "lowF";
    public static final String KEY_SECTIONS_WEIGHT_HOMEWORK    = "weightHomework";
    public static final String KEY_SECTIONS_WEIGHT_QUIZ        = "weightQuiz";
    public static final String KEY_SECTIONS_WEIGHT_MIDTERM     = "weightMidterm";
    public static final String KEY_SECTIONS_WEIGHT_FINAL       = "weightFinal";
    public static final String KEY_SECTIONS_WEIGHT_PROJECT     = "weightProject";
    public static final String KEY_SECTIONS_WEIGHT_OTHER       = "weightOther";
    public static final String KEY_SECTIONS_SCORE_HOMEWORK     = "scoreHomework";
    public static final String KEY_SECTIONS_MAX_SCORE_HOMEWORK = "maxScoreHomework";
    public static final String KEY_SECTIONS_SCORE_QUIZ         = "scoreQuiz";
    public static final String KEY_SECTIONS_MAX_SCORE_QUIZ     = "maxScoreQuiz";
    public static final String KEY_SECTIONS_SCORE_MIDTERM      = "scoreMidterm";
    public static final String KEY_SECTIONS_MAX_SCORE_MIDTERM  = "maxScoreMidterm";
    public static final String KEY_SECTIONS_SCORE_FINAL        = "scoreFinal";
    public static final String KEY_SECTIONS_MAX_SCORE_FINAL    = "maxScoreFinal";
    public static final String KEY_SECTIONS_SCORE_PROJECT      = "scoreProject";
    public static final String KEY_SECTIONS_MAX_SCORE_PROJECT  = "maxScoreProject";
    public static final String KEY_SECTIONS_SCORE_OTHER        = "scoreOther";
    public static final String KEY_SECTIONS_MAX_SCORE_OTHER    = "maxScoreOther";
    public static final String KEY_SECTIONS_SCORE_TOTAL        = "scoreTotal";
    public static final String KEY_SECTIONS_MAX_SCORE_TOTAL    = "maxScoreTotal";
    public static final String KEY_SECTIONS_GRADE              = "grade";
    public static final String KEY_SECTIONS_LOCATION           = "location";
    public static final String KEY_SECTIONS_ON_MONDAY          = "onMo";
    public static final String KEY_SECTIONS_ON_TUESDAY         = "onTu";
    public static final String KEY_SECTIONS_ON_WEDNESDAY       = "onWe";
    public static final String KEY_SECTIONS_ON_THURSDAY        = "onTh";
    public static final String KEY_SECTIONS_ON_FRIDAY          = "onFr";
    public static final String KEY_SECTIONS_ON_SATURDAY        = "onSa";
    public static final String KEY_SECTIONS_ON_SUNDAY          = "onSu";

    // TABLE_ASSIGNMENTS columns
    public static final String KEY_ASSIGNMENTS_ID         = "id";
    public static final String KEY_ASSIGNMENTS_TERM_ID    = "termId";
    public static final String KEY_ASSIGNMENTS_SECTION_ID = "sectionId";
    public static final String KEY_ASSIGNMENTS_NAME       = "name";
    public static final String KEY_ASSIGNMENTS_TYPE       = "type";
    public static final String KEY_ASSIGNMENTS_SCORE      = "score";
    public static final String KEY_ASSIGNMENTS_MAX_SCORE  = "maxScore";
    public static final String KEY_ASSIGNMENTS_GRADE      = "grade";

    // TABLE_DUE_DATES columns
    public static final String KEY_DUE_DATES_ID         = "id";
    public static final String KEY_DUE_DATES_TERM_ID    = "termId";
    public static final String KEY_DUE_DATES_SECTION_ID = "sectionId";
    public static final String KEY_DUE_DATES_NAME       = "name";
    public static final String KEY_DUE_DATES_COMPLETE   = "complete";
    public static final String KEY_DUE_DATES_YEAR       = "year";
    public static final String KEY_DUE_DATES_MONTH      = "month";
    public static final String KEY_DUE_DATES_DAY        = "day";

    private static final int TRUE  = 1;
    private static final int FALSE = 0;

    //SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_TERMS = "CREATE TABLE " + TABLE_TERMS + "(" +
                KEY_TERMS_ID + " INTEGER," + KEY_TERMS_NAME + " TEXT," +
                KEY_TERMS_ARCHIVED + " TEXT)";

        final String CREATE_TABLE_SECTIONS = "CREATE TABLE " + TABLE_SECTIONS + "(" +
                KEY_SECTIONS_ID + " INTEGER," + KEY_SECTIONS_TERM_ID +
                " INTEGER," + KEY_SECTIONS_NAME + " TEXT," +
                KEY_SECTIONS_HIGH_A + " REAL," + KEY_SECTIONS_LOW_A + " REAL," +
                KEY_SECTIONS_HIGH_B + " REAL," + KEY_SECTIONS_LOW_B + " REAL," +
                KEY_SECTIONS_HIGH_C + " REAL," + KEY_SECTIONS_LOW_C + " REAL," +
                KEY_SECTIONS_HIGH_D + " REAL," + KEY_SECTIONS_LOW_D + " REAL," +
                KEY_SECTIONS_HIGH_F + " REAL," + KEY_SECTIONS_LOW_F + " REAL," +
                KEY_SECTIONS_WEIGHT_HOMEWORK + " REAL," + KEY_SECTIONS_WEIGHT_QUIZ + " REAL," +
                KEY_SECTIONS_WEIGHT_MIDTERM + " REAL," + KEY_SECTIONS_WEIGHT_FINAL + " REAL," +
                KEY_SECTIONS_WEIGHT_PROJECT + " REAL," + KEY_SECTIONS_WEIGHT_OTHER + " REAL," +
                KEY_SECTIONS_SCORE_HOMEWORK + " REAL," +
                KEY_SECTIONS_MAX_SCORE_HOMEWORK + " REAL," +
                KEY_SECTIONS_SCORE_QUIZ + " REAL," +
                KEY_SECTIONS_MAX_SCORE_QUIZ + " REAL," +
                KEY_SECTIONS_SCORE_MIDTERM + " REAL," +
                KEY_SECTIONS_MAX_SCORE_MIDTERM + " REAL," +
                KEY_SECTIONS_SCORE_FINAL + " REAL," +
                KEY_SECTIONS_MAX_SCORE_FINAL + " REAL," +
                KEY_SECTIONS_SCORE_PROJECT + " REAL," +
                KEY_SECTIONS_MAX_SCORE_PROJECT + " REAL," +
                KEY_SECTIONS_SCORE_OTHER + " REAL," +
                KEY_SECTIONS_MAX_SCORE_OTHER + " REAL," +
                KEY_SECTIONS_SCORE_TOTAL + " REAL," +
                KEY_SECTIONS_MAX_SCORE_TOTAL + " REAL," +
                KEY_SECTIONS_GRADE + " TEXT," + KEY_SECTIONS_LOCATION + " TEXT," +
                KEY_SECTIONS_ON_MONDAY + " INTEGER," + KEY_SECTIONS_ON_TUESDAY + " INTEGER," +
                KEY_SECTIONS_ON_WEDNESDAY + " INTEGER," + KEY_SECTIONS_ON_THURSDAY + " INTEGER," +
                KEY_SECTIONS_ON_FRIDAY + " INTEGER," + KEY_SECTIONS_ON_SATURDAY + " INTEGER," +
                KEY_SECTIONS_ON_SUNDAY + " INTEGER)";

        final String CREATE_TABLE_ASSIGNMENTS = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                KEY_ASSIGNMENTS_ID + " INTEGER," +
                KEY_ASSIGNMENTS_TERM_ID + " INTEGER," + KEY_ASSIGNMENTS_SECTION_ID + " INTEGER," +
                KEY_ASSIGNMENTS_NAME + " TEXT," + KEY_ASSIGNMENTS_TYPE + " TEXT," +
                KEY_ASSIGNMENTS_SCORE + " REAL," + KEY_ASSIGNMENTS_MAX_SCORE + " REAL," +
                KEY_ASSIGNMENTS_GRADE + " TEXT)";

        final String CREATE_TABLE_DUE_DATES = "CREATE TABLE " + TABLE_DUE_DATES + "(" +
                KEY_DUE_DATES_ID + " INTEGER," +
                KEY_DUE_DATES_TERM_ID + " INTEGER," + KEY_DUE_DATES_SECTION_ID + " INTEGER," +
                KEY_DUE_DATES_NAME + " TEXT," + KEY_DUE_DATES_COMPLETE + " INTEGER," +
                KEY_DUE_DATES_YEAR + " INTEGER," + KEY_DUE_DATES_MONTH + " INTEGER," +
                KEY_DUE_DATES_DAY + " INTEGER)";

        db.execSQL(CREATE_TABLE_TERMS);
        db.execSQL(CREATE_TABLE_SECTIONS);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(CREATE_TABLE_DUE_DATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addTerm(Term term, int termId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TERMS_ID, termId);
        values.put(KEY_TERMS_NAME, term.getTermName());
        if (term.isArchived()) {
            values.put(KEY_TERMS_ARCHIVED, TRUE);
        } else {
            values.put(KEY_TERMS_ARCHIVED, FALSE);
        }

        db.insert(TABLE_TERMS, null, values);
        db.close();
    }

    public void addSection(Section section, int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SECTIONS_TERM_ID, termId);
        values.put(KEY_SECTIONS_ID, sectionId);
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

        SparseArray<Double> maxScores = section.getMaxScores();
        values.put(KEY_SECTIONS_MAX_SCORE_HOMEWORK, maxScores.get(Section.HOMEWORK));
        values.put(KEY_SECTIONS_MAX_SCORE_QUIZ, maxScores.get(Section.QUIZ));
        values.put(KEY_SECTIONS_MAX_SCORE_MIDTERM, maxScores.get(Section.MIDTERM));
        values.put(KEY_SECTIONS_MAX_SCORE_FINAL, maxScores.get(Section.FINAL));
        values.put(KEY_SECTIONS_MAX_SCORE_PROJECT, maxScores.get(Section.PROJECT));
        values.put(KEY_SECTIONS_MAX_SCORE_OTHER, maxScores.get(Section.OTHER));

        values.put(KEY_SECTIONS_SCORE_TOTAL, section.getTotalScore());
        values.put(KEY_SECTIONS_MAX_SCORE_TOTAL, section.getMaxScore());
        values.put(KEY_SECTIONS_GRADE, section.getGrade());

        db.insert(TABLE_SECTIONS, null, values);
        db.close();
    }

    public void addAssignment(Assignment assignment, int termId, int sectionId, int assignmentId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ASSIGNMENTS_TERM_ID, termId);
        values.put(KEY_ASSIGNMENTS_SECTION_ID, sectionId);
        values.put(KEY_ASSIGNMENTS_ID, assignmentId);
        values.put(KEY_ASSIGNMENTS_NAME, assignment.getAssignmentName());
        values.put(KEY_ASSIGNMENTS_TYPE, assignment.getAssignmentType());
        values.put(KEY_ASSIGNMENTS_SCORE, assignment.getScore());
        values.put(KEY_ASSIGNMENTS_MAX_SCORE, assignment.getMaxScore());
        values.put(KEY_ASSIGNMENTS_GRADE, assignment.getGrade());

        db.insert(TABLE_ASSIGNMENTS, null, values);
        db.close();
    }

    public void addDueDate(DueDate dueDate, int termId, int sectionId, int dueDateId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DUE_DATES_TERM_ID, termId);
        values.put(KEY_DUE_DATES_SECTION_ID, sectionId);
        values.put(KEY_DUE_DATES_ID, dueDateId);
        values.put(KEY_DUE_DATES_NAME, dueDate.getDueDateName());
        values.put(KEY_DUE_DATES_COMPLETE, dueDate.isComplete());
        Calendar date = dueDate.getDate();
        values.put(KEY_DUE_DATES_YEAR, date.get(Calendar.YEAR));
        values.put(KEY_DUE_DATES_MONTH, date.get(Calendar.MONTH));
        values.put(KEY_DUE_DATES_DAY, date.get(Calendar.DAY_OF_MONTH));

        db.insert(TABLE_DUE_DATES, null, values);
        db.close();
    }

    public void updateSection(ContentValues values, int termId, int sectionId) {
        String where = KEY_SECTIONS_ID + " = " + sectionId + " AND " +
                KEY_SECTIONS_TERM_ID + " = " + termId;
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_SECTIONS, values, where, null);
        db.close();
    }

    public void updateDueDate(ContentValues values, int termId, int sectionId, int dueDateId) {
        String where = KEY_DUE_DATES_ID + " = " + dueDateId + " AND " +
                KEY_DUE_DATES_SECTION_ID + " = " + sectionId + " AND " +
                KEY_DUE_DATES_TERM_ID + " = " + termId;
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_DUE_DATES, values, where, null);
        db.close();
    }

    public List<Term> getTerms(boolean archived) {
        SQLiteDatabase db = getReadableDatabase();
        List<Term> terms = new ArrayList<>();

        int isArchived = FALSE;
        if (archived) {
            isArchived = TRUE;
        }

        String query = "SELECT * FROM " + TABLE_TERMS + " WHERE " + KEY_TERMS_ARCHIVED +
                " = " + isArchived;
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
        //db.close();
        return terms;
    }

    private List<Section> getSections(int termId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Section> sections = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SECTIONS + " WHERE " + KEY_SECTIONS_TERM_ID +
                " = " + termId;
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
                SparseArray<Double> scores    = new SparseArray<>();
                SparseArray<Double> maxScores = new SparseArray<>();
                int scoreIndex    = 0;
                int maxScoreIndex = 0;
                for (int i = 0; i < 12; i++) {
                    if (i % 2 == 0) {
                        scores.put(scoreIndex, cursor.getDouble(i+19));
                        scoreIndex++;
                    } else {
                        maxScores.put(maxScoreIndex, cursor.getDouble(i+19));
                        maxScoreIndex++;
                    }

                }
                double totalScore = cursor.getDouble(31);
                double maxScore = cursor.getDouble(32);
                String grade = cursor.getString(33);

                // get the assignments belonging to this section
                List<Assignment> assignments = getAssignments(termId, sectionId);

                // get the due dates belonging to this section
                List<DueDate> dueDates = getDueDates(termId, sectionId);

                // create and add the section
                Section section = new Section(sectionName, gradeThresholds, assignmentWeights,
                        scores, maxScores, totalScore, maxScore, grade, assignments, dueDates);
                sections.add(section);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return sections;
    }

    private List<Assignment> getAssignments(int termId, int sectionId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Assignment> assignments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE " + KEY_ASSIGNMENTS_TERM_ID +
                " = " + termId + " AND " + KEY_ASSIGNMENTS_SECTION_ID + " = " + sectionId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                String assignmentName = cursor.getString(3);
                String assignmentType = cursor.getString(4);
                double score = cursor.getDouble(5);
                double maxScore = cursor.getDouble(6);
                String grade = cursor.getString(7);

                // create and add the assignment
                Assignment assignment = new Assignment(assignmentName, assignmentType, score, maxScore, grade);
                assignments.add(assignment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return assignments;
    }

    private List<DueDate> getDueDates(int termId, int sectionId) {
        SQLiteDatabase db = getReadableDatabase();
        List<DueDate> dueDates = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DUE_DATES + " WHERE " + KEY_DUE_DATES_TERM_ID +
                " = " + termId + " AND " + KEY_DUE_DATES_SECTION_ID + " = " + sectionId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information form the database
                String dueDateName = cursor.getString(3);
                boolean complete = false;
                if (cursor.getInt(4) == TRUE) {
                    complete = true;
                }
                Calendar date = Calendar.getInstance();
                int year      = cursor.getInt(5);
                int month     = cursor.getInt(6);
                int day       = cursor.getInt(7);
                date.set(year, month, day);

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
