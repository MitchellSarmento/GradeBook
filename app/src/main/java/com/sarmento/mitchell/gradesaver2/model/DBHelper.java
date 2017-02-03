package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Database Information
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GradeBookDB";

    // Tables
    private static final String TABLE_TERMS       = "Terms";
    private static final String TABLE_SECTIONS    = "Sections";
    private static final String TABLE_ASSIGNMENTS = "Assignments";
    private static final String TABLE_DUE_DATES   = "DueDates";
    private static final String TABLE_SCHEDULES   = "Schedules";

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
    public static final String[] KEY_SECTIONS_GRADE_THRESHOLDS = {
            KEY_SECTIONS_HIGH_A, KEY_SECTIONS_LOW_A, KEY_SECTIONS_HIGH_B, KEY_SECTIONS_LOW_B,
            KEY_SECTIONS_HIGH_C, KEY_SECTIONS_LOW_C, KEY_SECTIONS_HIGH_D, KEY_SECTIONS_LOW_D,
            KEY_SECTIONS_HIGH_F, KEY_SECTIONS_LOW_F
    };
    public static final String KEY_SECTIONS_WEIGHT_HOMEWORK    = "weightHomework";
    public static final String KEY_SECTIONS_WEIGHT_QUIZ        = "weightQuiz";
    public static final String KEY_SECTIONS_WEIGHT_MIDTERM     = "weightMidterm";
    public static final String KEY_SECTIONS_WEIGHT_FINAL       = "weightFinal";
    public static final String KEY_SECTIONS_WEIGHT_PROJECT     = "weightProject";
    public static final String KEY_SECTIONS_WEIGHT_OTHER       = "weightOther";
    public static final String[] KEY_SECTIONS_WEIGHTS          = {
            KEY_SECTIONS_WEIGHT_HOMEWORK, KEY_SECTIONS_WEIGHT_QUIZ, KEY_SECTIONS_WEIGHT_MIDTERM,
            KEY_SECTIONS_WEIGHT_FINAL, KEY_SECTIONS_WEIGHT_PROJECT, KEY_SECTIONS_WEIGHT_OTHER
    };
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
    public static final String[] KEY_SECTIONS_SCORES             = {
            KEY_SECTIONS_SCORE_HOMEWORK, KEY_SECTIONS_MAX_SCORE_HOMEWORK,
            KEY_SECTIONS_SCORE_QUIZ, KEY_SECTIONS_MAX_SCORE_QUIZ,
            KEY_SECTIONS_SCORE_MIDTERM, KEY_SECTIONS_MAX_SCORE_MIDTERM,
            KEY_SECTIONS_SCORE_FINAL, KEY_SECTIONS_MAX_SCORE_FINAL,
            KEY_SECTIONS_SCORE_PROJECT, KEY_SECTIONS_MAX_SCORE_PROJECT,
            KEY_SECTIONS_SCORE_OTHER, KEY_SECTIONS_MAX_SCORE_OTHER
    };
    public static final String KEY_SECTIONS_SCORE_TOTAL        = "scoreTotal";
    public static final String KEY_SECTIONS_MAX_SCORE_TOTAL    = "maxScoreTotal";
    public static final String KEY_SECTIONS_GRADE              = "grade";
    public static final String KEY_SECTIONS_FINAL_GRADE        = "finalGrade";
    public static final String KEY_SECTIONS_ARCHIVED           = "archived";

    // TABLE_ASSIGNMENTS columns
    public static final String KEY_ASSIGNMENTS_ID          = "id";
    public static final String KEY_ASSIGNMENTS_TERM_ID     = "termId";
    public static final String KEY_ASSIGNMENTS_SECTION_ID  = "sectionId";
    public static final String KEY_ASSIGNMENTS_NAME        = "name";
    public static final String KEY_ASSIGNMENTS_TYPE        = "type";
    public static final String KEY_ASSIGNMENTS_SCORE       = "score";
    public static final String KEY_ASSIGNMENTS_MAX_SCORE   = "maxScore";
    public static final String KEY_ASSIGNMENTS_GRADE       = "grade";
    public static final String KEY_ASSIGNMENTS_IMAGE_PATHS = "imagePath";
    public static final String KEY_ASSIGNMENTS_ARCHIVED    = "archived";

    // TABLE_DUE_DATES columns
    public static final String KEY_DUE_DATES_ID         = "id";
    public static final String KEY_DUE_DATES_TERM_ID    = "termId";
    public static final String KEY_DUE_DATES_SECTION_ID = "sectionId";
    public static final String KEY_DUE_DATES_NAME       = "name";
    public static final String KEY_DUE_DATES_COMPLETE   = "complete";
    public static final String KEY_DUE_DATES_YEAR       = "year";
    public static final String KEY_DUE_DATES_MONTH      = "month";
    public static final String KEY_DUE_DATES_DAY        = "day";
    public static final String KEY_DUE_DATES_ARCHIVED   = "archived";

    // TABLE_SCHEDULES columns
    public static final String KEY_SCHEDULES_TERM_ID            = "termId";
    public static final String KEY_SCHEDULES_SECTION_ID         = "sectionId";
    public static final String KEY_SCHEDULES_LOCATION           = "location";
    public static final String KEY_SCHEDULES_START_MONDAY       = "startMo";
    public static final String KEY_SCHEDULES_END_MONDAY         = "endMo";
    public static final String KEY_SCHEDULES_START_TUESDAY      = "startTu";
    public static final String KEY_SCHEDULES_END_TUESDAY        = "endTu";
    public static final String KEY_SCHEDULES_START_WEDNESDAY    = "startWe";
    public static final String KEY_SCHEDULES_END_WEDNESDAY      = "endWe";
    public static final String KEY_SCHEDULES_START_THURSDAY     = "startTh";
    public static final String KEY_SCHEDULES_END_THURSDAY       = "endTh";
    public static final String KEY_SCHEDULES_START_FRIDAY       = "startFr";
    public static final String KEY_SCHEDULES_END_FRIDAY         = "endFr";
    public static final String KEY_SCHEDULES_START_SATURDAY     = "startSa";
    public static final String KEY_SCHEDULES_END_SATURDAY       = "endSa";
    public static final String KEY_SCHEDULES_START_SUNDAY       = "startSu";
    public static final String KEY_SCHEDULES_END_SUNDAY         = "endSu";
    public static final String KEY_SCHEDULES_ARCHIVED           = "archived";

    private static final int TRUE  = 1;
    private static final int FALSE = 0;

    private static final int ALL_IDS = -1;

    private Academics academics = Academics.getInstance();
    private int inArchive;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        inArchive = (academics.inArchive()) ? TRUE : FALSE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_TERMS = "CREATE TABLE " + TABLE_TERMS + "(" +
                KEY_TERMS_ID + " INTEGER," +
                KEY_TERMS_NAME + " TEXT," +
                KEY_TERMS_ARCHIVED + " INTEGER)";

        final String CREATE_TABLE_SECTIONS = "CREATE TABLE " + TABLE_SECTIONS + "(" +
                KEY_SECTIONS_ID + " INTEGER," +
                KEY_SECTIONS_TERM_ID + " INTEGER," +
                KEY_SECTIONS_NAME + " TEXT," +
                KEY_SECTIONS_HIGH_A + " REAL," +
                KEY_SECTIONS_LOW_A + " REAL," +
                KEY_SECTIONS_HIGH_B + " REAL," +
                KEY_SECTIONS_LOW_B + " REAL," +
                KEY_SECTIONS_HIGH_C + " REAL," +
                KEY_SECTIONS_LOW_C + " REAL," +
                KEY_SECTIONS_HIGH_D + " REAL," +
                KEY_SECTIONS_LOW_D + " REAL," +
                KEY_SECTIONS_HIGH_F + " REAL," +
                KEY_SECTIONS_LOW_F + " REAL," +
                KEY_SECTIONS_WEIGHT_HOMEWORK + " REAL," +
                KEY_SECTIONS_WEIGHT_QUIZ + " REAL," +
                KEY_SECTIONS_WEIGHT_MIDTERM + " REAL," +
                KEY_SECTIONS_WEIGHT_FINAL + " REAL," +
                KEY_SECTIONS_WEIGHT_PROJECT + " REAL," +
                KEY_SECTIONS_WEIGHT_OTHER + " REAL," +
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
                KEY_SECTIONS_GRADE + " TEXT," +
                KEY_SECTIONS_FINAL_GRADE + " TEXT," +
                KEY_SECTIONS_ARCHIVED + " INTEGER)";

        final String CREATE_TABLE_ASSIGNMENTS = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                KEY_ASSIGNMENTS_ID + " INTEGER," +
                KEY_ASSIGNMENTS_TERM_ID + " INTEGER," +
                KEY_ASSIGNMENTS_SECTION_ID + " INTEGER," +
                KEY_ASSIGNMENTS_NAME + " TEXT," +
                KEY_ASSIGNMENTS_TYPE + " TEXT," +
                KEY_ASSIGNMENTS_SCORE + " REAL," +
                KEY_ASSIGNMENTS_MAX_SCORE + " REAL," +
                KEY_ASSIGNMENTS_GRADE + " TEXT," +
                KEY_ASSIGNMENTS_IMAGE_PATHS + " TEXT," +
                KEY_ASSIGNMENTS_ARCHIVED + " INTEGER)";

        final String CREATE_TABLE_DUE_DATES = "CREATE TABLE " + TABLE_DUE_DATES + "(" +
                KEY_DUE_DATES_ID + " INTEGER," +
                KEY_DUE_DATES_TERM_ID + " INTEGER," +
                KEY_DUE_DATES_SECTION_ID + " INTEGER," +
                KEY_DUE_DATES_NAME + " TEXT," +
                KEY_DUE_DATES_COMPLETE + " INTEGER," +
                KEY_DUE_DATES_YEAR + " INTEGER," +
                KEY_DUE_DATES_MONTH + " INTEGER," +
                KEY_DUE_DATES_DAY + " INTEGER," +
                KEY_DUE_DATES_ARCHIVED + " INTEGER)";

        final String CREATE_TABLE_SCHEDULES = "CREATE TABLE " + TABLE_SCHEDULES + "(" +
                KEY_SCHEDULES_TERM_ID + " INTEGER," +
                KEY_SCHEDULES_SECTION_ID + " INTEGER," +
                KEY_SCHEDULES_LOCATION + " TEXT," +
                KEY_SCHEDULES_START_MONDAY + " TEXT," +
                KEY_SCHEDULES_END_MONDAY + " TEXT," +
                KEY_SCHEDULES_START_TUESDAY + " TEXT," +
                KEY_SCHEDULES_END_TUESDAY + " TEXT," +
                KEY_SCHEDULES_START_WEDNESDAY + " TEXT," +
                KEY_SCHEDULES_END_WEDNESDAY + " TEXT," +
                KEY_SCHEDULES_START_THURSDAY + " TEXT," +
                KEY_SCHEDULES_END_THURSDAY + " TEXT," +
                KEY_SCHEDULES_START_FRIDAY + " TEXT," +
                KEY_SCHEDULES_END_FRIDAY + " TEXT," +
                KEY_SCHEDULES_START_SATURDAY + " TEXT," +
                KEY_SCHEDULES_END_SATURDAY + " TEXT," +
                KEY_SCHEDULES_START_SUNDAY + " TEXT," +
                KEY_SCHEDULES_END_SUNDAY + " TEXT," +
                KEY_SCHEDULES_ARCHIVED + " INTEGER)";

        db.execSQL(CREATE_TABLE_TERMS);
        db.execSQL(CREATE_TABLE_SECTIONS);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(CREATE_TABLE_DUE_DATES);
        db.execSQL(CREATE_TABLE_SCHEDULES);
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
        for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
            int thresholdValue = threshold.getValue();
            values.put(KEY_SECTIONS_GRADE_THRESHOLDS[thresholdValue],
                    gradeThresholds.get(thresholdValue));
        }

        SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
        SparseArray<Double> scores = section.getScores();
        SparseArray<Double> maxScores = section.getMaxScores();
        for (Section.AssignmentType type : Section.AssignmentType.values()) {
            int typeValue = type.getValue();
            values.put(KEY_SECTIONS_WEIGHTS[typeValue],
                    assignmentWeights.get(typeValue));
            values.put(KEY_SECTIONS_SCORES[typeValue*2], scores.get(typeValue));
            values.put(KEY_SECTIONS_SCORES[typeValue*2+1], maxScores.get(typeValue));
        }

        values.put(KEY_SECTIONS_SCORE_TOTAL, section.getTotalScore());
        values.put(KEY_SECTIONS_MAX_SCORE_TOTAL, section.getMaxScore());
        values.put(KEY_SECTIONS_GRADE, section.getGrade());
        values.put(KEY_SECTIONS_FINAL_GRADE, section.getFinalGrade());
        values.put(KEY_SECTIONS_ARCHIVED, FALSE);

        db.insert(TABLE_SECTIONS, null, values);
        db.close();

        addSchedule(section.getSchedule(), termId, sectionId);
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
        values.put(KEY_ASSIGNMENTS_ARCHIVED, FALSE);

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
        values.put(KEY_DUE_DATES_ARCHIVED, FALSE);

        db.insert(TABLE_DUE_DATES, null, values);
        db.close();
    }

    private void addSchedule(Schedule schedule, int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCHEDULES_TERM_ID, termId);
        values.put(KEY_SCHEDULES_SECTION_ID, sectionId);
        values.put(KEY_SCHEDULES_LOCATION, schedule.getLocation());

        String[] startKeys = {KEY_SCHEDULES_START_MONDAY,
                KEY_SCHEDULES_START_TUESDAY, KEY_SCHEDULES_START_WEDNESDAY,
                KEY_SCHEDULES_START_THURSDAY, KEY_SCHEDULES_START_FRIDAY,
                KEY_SCHEDULES_START_SATURDAY, KEY_SCHEDULES_START_SUNDAY};
        String[] endKeys = {KEY_SCHEDULES_END_MONDAY,
                KEY_SCHEDULES_END_TUESDAY, KEY_SCHEDULES_END_WEDNESDAY,
                KEY_SCHEDULES_END_THURSDAY, KEY_SCHEDULES_END_FRIDAY,
                KEY_SCHEDULES_END_SATURDAY, KEY_SCHEDULES_END_SUNDAY};

        SparseArray<String> startTimes = schedule.getStartTimes();
        SparseArray<String> endTimes   = schedule.getEndTimes();
        for (Schedule.Day day : Schedule.Day.values()) {
            int dayValue = day.getValue();
            values.put(startKeys[dayValue], startTimes.get(dayValue));
            values.put(endKeys[dayValue], endTimes.get(dayValue));
        }
        values.put(KEY_SCHEDULES_ARCHIVED, FALSE);

        db.insert(TABLE_SCHEDULES, null, values);
    }

    public void removeTerm(int termId) {
        SQLiteDatabase db = getWritableDatabase();

        // delete the Term
        String where = KEY_TERMS_ID + " = " + termId + " AND " +
                KEY_TERMS_ARCHIVED + " = " + inArchive;
        db.delete(TABLE_TERMS, where, null);

        // delete related Sections
        removeSection(termId, ALL_IDS);

        // delete related Assignments
        removeAssignment(termId, ALL_IDS, ALL_IDS);

        // delete related DueDates
        removeDueDate(termId, ALL_IDS, ALL_IDS);

        // delete related Schedules
        removeSchedule(termId, ALL_IDS);

        // decrement all larger Term ids
        db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TERMS + " SET " + KEY_TERMS_ID + " = " +
                KEY_TERMS_ID + " - 1 WHERE " + KEY_TERMS_ID + " > " + termId + " AND " +
                KEY_TERMS_ARCHIVED + " = " + inArchive);
        db.execSQL("UPDATE " + TABLE_SECTIONS + " SET " + KEY_SECTIONS_TERM_ID + " = " +
                KEY_SECTIONS_TERM_ID + " - 1 WHERE " + KEY_SECTIONS_TERM_ID + " > " + termId +
                " AND " + KEY_SECTIONS_ARCHIVED + " = " + inArchive);
        db.execSQL("UPDATE " + TABLE_ASSIGNMENTS + " SET " + KEY_ASSIGNMENTS_TERM_ID + " = " +
                KEY_ASSIGNMENTS_TERM_ID + " - 1 WHERE " + KEY_ASSIGNMENTS_TERM_ID + " > " +
                termId + " AND " + KEY_ASSIGNMENTS_ARCHIVED + " = " + inArchive);
        db.execSQL("UPDATE " + TABLE_DUE_DATES + " SET " + KEY_DUE_DATES_TERM_ID + " = " +
                KEY_DUE_DATES_TERM_ID + " - 1 WHERE " + KEY_DUE_DATES_TERM_ID + " > " +
                termId + " AND " + KEY_DUE_DATES_ARCHIVED + " = " + inArchive);
        db.execSQL("UPDATE " + TABLE_SCHEDULES + " SET " + KEY_SCHEDULES_TERM_ID + " = " +
                KEY_SCHEDULES_TERM_ID + " - 1 WHERE " + KEY_SCHEDULES_TERM_ID + " > " + termId +
                " AND " + KEY_SCHEDULES_ARCHIVED + " = " + inArchive);
        db.close();
    }

    public void removeSection(int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();

        // delete the Section
        String where = KEY_SECTIONS_TERM_ID + " = " + termId + " AND " + KEY_SECTIONS_ARCHIVED +
                " = " + inArchive;
        if (sectionId != ALL_IDS) {
            where += " AND " + KEY_SECTIONS_ID + " = " + sectionId;
        }
        db.delete(TABLE_SECTIONS, where, null);

        // delete related Assignments
        removeAssignment(termId, sectionId, ALL_IDS);

        // delete related DueDates
        removeDueDate(termId, sectionId, ALL_IDS);

        // delete related Schedule
        removeSchedule(termId, sectionId);

        // decrement all larger Section ids
        if (sectionId != ALL_IDS) {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_SECTIONS + " SET " + KEY_SECTIONS_ID + " = " +
                    KEY_SECTIONS_ID + " - 1 WHERE " + KEY_SECTIONS_ID + " > " + sectionId +
                    " AND " + KEY_SECTIONS_ARCHIVED + " = " + inArchive);
            db.execSQL("UPDATE " + TABLE_ASSIGNMENTS + " SET " + KEY_ASSIGNMENTS_SECTION_ID + " = " +
                    KEY_ASSIGNMENTS_SECTION_ID + " - 1 WHERE " + KEY_ASSIGNMENTS_SECTION_ID +
                    " > " + sectionId + " AND " + KEY_ASSIGNMENTS_ARCHIVED + " = " + inArchive);
            db.execSQL("UPDATE " + TABLE_DUE_DATES + " SET " + KEY_DUE_DATES_SECTION_ID + " = " +
                    KEY_DUE_DATES_SECTION_ID + " - 1 WHERE " + KEY_DUE_DATES_SECTION_ID +
                    " > " + sectionId + " AND " + KEY_DUE_DATES_ARCHIVED + " = " + inArchive);
            db.execSQL("UPDATE " + TABLE_SCHEDULES + " SET " + KEY_SCHEDULES_SECTION_ID + " = " +
                    KEY_SCHEDULES_SECTION_ID + " - 1 WHERE " + KEY_SCHEDULES_SECTION_ID +
                    " > " + sectionId + " AND " + KEY_SCHEDULES_ARCHIVED + " = " + inArchive);
        }
        db.close();
    }

    public void removeAssignment(int termId, int sectionId, int assignmentId) {
        SQLiteDatabase db = getWritableDatabase();

        // delete the Assignment
        String where = KEY_ASSIGNMENTS_TERM_ID + " = " + termId + " AND " +
                KEY_ASSIGNMENTS_ARCHIVED + " = " + inArchive;
        if (sectionId != ALL_IDS) {
            where += " AND " + KEY_ASSIGNMENTS_SECTION_ID + " = " + sectionId;
        }
        if (assignmentId != ALL_IDS) {
            where += " AND " + KEY_ASSIGNMENTS_ID + " = " + assignmentId;
        }
        db.delete(TABLE_ASSIGNMENTS, where, null);

        // decrement all larger Assignment ids
        if (assignmentId != ALL_IDS) {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_ASSIGNMENTS + " SET " + KEY_ASSIGNMENTS_ID + " = " +
                    KEY_ASSIGNMENTS_ID + " - 1 WHERE " + KEY_ASSIGNMENTS_ID + " > " +
                    assignmentId + " AND " + KEY_ASSIGNMENTS_ARCHIVED + " = " + inArchive);
        }
        db.close();
    }

    public void removeDueDate(int termId, int sectionId, int dueDateId) {
        SQLiteDatabase db = getWritableDatabase();

        // delete the DueDate
        String where = KEY_DUE_DATES_TERM_ID + " = " + termId + " AND " +
                KEY_DUE_DATES_ARCHIVED + " = " + inArchive;
        if (sectionId != ALL_IDS) {
            where += " AND " + KEY_DUE_DATES_SECTION_ID + " = " + sectionId;
        }
        if (dueDateId != ALL_IDS) {
            where += " AND " + KEY_DUE_DATES_ID + " = " + dueDateId;
        }
        db.delete(TABLE_DUE_DATES, where, null);

        // decrement all larger DueDate ids
        if (dueDateId != ALL_IDS) {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_DUE_DATES + " SET " + KEY_DUE_DATES_ID + " = " +
                    KEY_DUE_DATES_ID + " - 1 WHERE " + KEY_DUE_DATES_ID + " > " + dueDateId +
                    " AND " + KEY_DUE_DATES_ARCHIVED + " = " + inArchive);
        }
        db.close();
    }

    public void removeSchedule(int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();

        // delete the Schedule
        String where = KEY_SCHEDULES_TERM_ID + " = " + termId + " AND " +
                KEY_SCHEDULES_ARCHIVED + " = " + inArchive;
        if (sectionId != ALL_IDS) {
            where += " AND " + KEY_SCHEDULES_SECTION_ID + " = " + sectionId;
        }
        db.delete(TABLE_SCHEDULES, where, null);

        db.close();
    }

    public void updateTerm(ContentValues values, int termId) {
        SQLiteDatabase db = getWritableDatabase();

        String where = KEY_TERMS_ID + " = " + termId + " AND " +
                KEY_TERMS_ARCHIVED + " = " + inArchive;

        db.update(TABLE_TERMS, values, where, null);

        // if the Term is changing archived status then update its id and all relevant objects
        if (values.getAsBoolean(KEY_TERMS_ARCHIVED) != null) {
            if (inArchive == FALSE && values.getAsBoolean(KEY_TERMS_ARCHIVED) ||
                    inArchive == TRUE && !values.getAsBoolean(KEY_TERMS_ARCHIVED)) {

                int newTermId           = values.getAsInteger(KEY_TERMS_ID);
                boolean newArchiveState = values.getAsBoolean(KEY_TERMS_ARCHIVED);

                List<Term> terms;
                List<Term> termsToDecrement;
                if (inArchive == TRUE) {
                    // get the Terms in the destination container
                    terms = academics.getCurrentTerms();

                    // get the Terms in the source container
                    termsToDecrement = academics.getArchivedTerms();
                } else {
                    terms = academics.getArchivedTerms();

                    // get the Terms in the source container
                    termsToDecrement = academics.getCurrentTerms();
                }

                // update the Sections
                List<Section> sections = terms.get(newTermId).getSections();
                ContentValues sectionValues = new ContentValues();
                sectionValues.put(KEY_SECTIONS_TERM_ID, newTermId);
                sectionValues.put(KEY_SECTIONS_ARCHIVED, newArchiveState);
                for (int sectionId = 0; sectionId < sections.size(); sectionId++) {
                    updateSection(sectionValues, termId, sectionId);
                    Section section = sections.get(sectionId);

                    // update the Assignments
                    List<Assignment> assignments = section.getAssignments();
                    ContentValues assignmentValues = new ContentValues();
                    assignmentValues.put(KEY_ASSIGNMENTS_TERM_ID, newTermId);
                    assignmentValues.put(KEY_ASSIGNMENTS_ARCHIVED, newArchiveState);
                    for (int assignmentId = 0; assignmentId < assignments.size(); assignmentId++) {
                        updateAssignment(assignmentValues, termId, sectionId, assignmentId);
                    }

                    // update the Due Dates
                    List<DueDate> dueDates = section.getDueDates();
                    ContentValues dueDateValues = new ContentValues();
                    dueDateValues.put(KEY_DUE_DATES_TERM_ID, newTermId);
                    dueDateValues.put(KEY_DUE_DATES_ARCHIVED, newArchiveState);
                    for (int dueDateId = 0; dueDateId < dueDates.size(); dueDateId++) {
                        updateDueDate(dueDateValues, termId, sectionId, dueDateId);
                    }

                    // update the Schedule
                    ContentValues scheduleValues = new ContentValues();
                    scheduleValues.put(KEY_SCHEDULES_TERM_ID, newTermId);
                    scheduleValues.put(KEY_SCHEDULES_ARCHIVED, newArchiveState);
                    updateSchedule(scheduleValues, termId, sectionId);
                }

                // decrement the ids of Terms in the source container that had a larger id
                ContentValues termValues = new ContentValues();
                for (int decrementId = termId+1; decrementId < termsToDecrement.size()+1; decrementId++) {
                    termValues.put(KEY_TERMS_ID, decrementId-1);
                    updateTerm(termValues, decrementId);
                }
            }
        // if the Term is only changing its id then update all relevant objects
        } else if (values.getAsInteger(KEY_TERMS_ID) != null) {
            int newTermId = values.getAsInteger(KEY_TERMS_ID);

            Term term;
            if (inArchive == TRUE) {
                term = academics.getArchivedTerms().get(newTermId);
            } else {
                term = academics.getCurrentTerms().get(newTermId);
            }

            // update the Sections
            List<Section> sections = term.getSections();
            ContentValues sectionValues = new ContentValues();
            sectionValues.put(KEY_SECTIONS_TERM_ID, newTermId);
            for (int sectionId = 0; sectionId < sections.size(); sectionId++) {
                updateSection(sectionValues, termId, sectionId);
                Section section = sections.get(sectionId);

                // update the Assignments
                List<Assignment> assignments = section.getAssignments();
                ContentValues assignmentValues = new ContentValues();
                assignmentValues.put(KEY_ASSIGNMENTS_TERM_ID, newTermId);
                for (int assignmentId = 0; assignmentId < assignments.size(); assignmentId++) {
                    updateAssignment(assignmentValues, termId, sectionId, assignmentId);
                }

                // update the Due Dates
                List<DueDate> dueDates = section.getDueDates();
                ContentValues dueDateValues = new ContentValues();
                dueDateValues.put(KEY_DUE_DATES_TERM_ID, newTermId);
                for (int dueDateId = 0; dueDateId < dueDates.size(); dueDateId++) {
                    updateDueDate(dueDateValues, termId, sectionId, dueDateId);
                }

                // update the Schedule
                ContentValues scheduleValues = new ContentValues();
                scheduleValues.put(KEY_SCHEDULES_TERM_ID, newTermId);
                updateSchedule(scheduleValues, termId, sectionId);
            }
        }
        db.close();
    }

    public void updateSection(ContentValues values, int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_SECTIONS_TERM_ID + " = " + termId + " AND " +
                KEY_SECTIONS_ID + " = " + sectionId + " AND " + KEY_SECTIONS_ARCHIVED + " = " +
                inArchive;

        db.update(TABLE_SECTIONS, values, where, null);
        db.close();
    }

    public void updateAssignment(ContentValues values, int termId, int sectionId, int assignmentId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_ASSIGNMENTS_TERM_ID + " = " + termId + " AND " +
                KEY_ASSIGNMENTS_SECTION_ID + " = " + sectionId + " AND " +
                KEY_ASSIGNMENTS_ID + " = " + assignmentId + " AND " + KEY_ASSIGNMENTS_ARCHIVED +
                " = " + inArchive;

        db.update(TABLE_ASSIGNMENTS, values, where, null);
        db.close();
    }

    public void updateDueDate(ContentValues values, int termId, int sectionId, int dueDateId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_DUE_DATES_TERM_ID + " = " + termId + " AND " +
                KEY_DUE_DATES_SECTION_ID + " = " + sectionId + " AND " +
                KEY_DUE_DATES_ID + " = " + dueDateId + " AND " + KEY_DUE_DATES_ARCHIVED +
                " = " + inArchive;

        db.update(TABLE_DUE_DATES, values, where, null);
        db.close();
    }

    public void updateSchedule(ContentValues values, int termId, int sectionId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_SCHEDULES_TERM_ID + " = " + termId + " AND " +
                KEY_SCHEDULES_SECTION_ID + " = " + sectionId + " AND " +
                KEY_SCHEDULES_ARCHIVED + " = " + inArchive;

        db.update(TABLE_SCHEDULES, values, where, null);
        db.close();
    }

    public List<Term> getTerms(boolean archived) {

        SQLiteDatabase db = getReadableDatabase();
        List<Term> terms  = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TERMS + " WHERE " + KEY_TERMS_ARCHIVED +
                " = " + inArchive;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                int termId = cursor.getInt(cursor.getColumnIndex(KEY_TERMS_ID));
                String termName = cursor.getString(cursor.getColumnIndex(KEY_TERMS_NAME));

                // get the sections belonging to this term
                List<Section> sections = getSections(termId);

                // create and add the term with ordering determined by KEY_TERMS_ID
                Term term = new Term(termName, archived, sections);
                if (termId >= terms.size()) {
                    terms.add(term);
                } else {
                    terms.add(termId, term);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return terms;
    }

    private List<Section> getSections(int termId) {
        SQLiteDatabase db      = getReadableDatabase();
        List<Section> sections = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SECTIONS + " WHERE " + KEY_SECTIONS_TERM_ID +
                " = " + termId + " AND " + KEY_SECTIONS_ARCHIVED + " = " + inArchive;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                int sectionId = cursor.getInt(cursor.getColumnIndex(KEY_SECTIONS_ID));
                String sectionName = cursor.getString(cursor.getColumnIndex(KEY_SECTIONS_NAME));
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
                for (int i = 0; i < 12; i += 2) {
                    scores.put(scoreIndex, cursor.getDouble(i+19));
                    scoreIndex++;
                    maxScores.put(maxScoreIndex, cursor.getDouble(i+20));
                    maxScoreIndex++;
                }
                double totalScore = cursor.getDouble(cursor.getColumnIndex(KEY_SECTIONS_SCORE_TOTAL));
                double maxScore   = cursor.getDouble(cursor.getColumnIndex(KEY_SECTIONS_MAX_SCORE_TOTAL));
                String grade      = cursor.getString(cursor.getColumnIndex(KEY_SECTIONS_GRADE));
                String finalGrade = cursor.getString(cursor.getColumnIndex(KEY_SECTIONS_FINAL_GRADE));

                // get the assignments belonging to this section
                List<Assignment> assignments = getAssignments(termId, sectionId);

                // get the due dates belonging to this section
                List<DueDate> dueDates = getDueDates(termId, sectionId);

                // get the Schedule belonging to this Section
                Schedule schedule = getSchedule(termId, sectionId);

                // create and add the section
                Section section = new Section(sectionName, gradeThresholds, assignmentWeights,
                        scores, maxScores, totalScore, maxScore, grade, finalGrade,
                        assignments, dueDates, schedule);
                sections.add(section);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sections;
    }

    private List<Assignment> getAssignments(int termId, int sectionId) {
        SQLiteDatabase db            = getReadableDatabase();
        List<Assignment> assignments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE " + KEY_ASSIGNMENTS_TERM_ID +
                " = " + termId + " AND " + KEY_ASSIGNMENTS_SECTION_ID + " = " + sectionId +
                " AND " + KEY_ASSIGNMENTS_ARCHIVED + " = " + inArchive;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                String assignmentName = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNMENTS_NAME));
                String assignmentType = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNMENTS_TYPE));
                double score          = cursor.getDouble(cursor.getColumnIndex(KEY_ASSIGNMENTS_SCORE));
                double maxScore       = cursor.getDouble(cursor.getColumnIndex(KEY_ASSIGNMENTS_MAX_SCORE));
                String grade          = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNMENTS_GRADE));
                String imagePaths     = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNMENTS_IMAGE_PATHS));

                // create and add the assignment
                Assignment assignment = new Assignment(assignmentName, assignmentType, score,
                        maxScore, grade, imagePaths);
                assignments.add(assignment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return assignments;
    }

    private List<DueDate> getDueDates(int termId, int sectionId) {
        SQLiteDatabase db      = getReadableDatabase();
        List<DueDate> dueDates = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DUE_DATES + " WHERE " + KEY_DUE_DATES_TERM_ID +
                " = " + termId + " AND " + KEY_DUE_DATES_SECTION_ID + " = " + sectionId +
                " AND " + KEY_DUE_DATES_ARCHIVED + " = " + inArchive;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                String dueDateName = cursor.getString(cursor.getColumnIndex(KEY_DUE_DATES_NAME));
                boolean complete   = false;
                if (cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_COMPLETE)) == TRUE) {
                    complete = true;
                }
                Calendar date = Calendar.getInstance();
                int year      = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_YEAR));
                int month     = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_MONTH));
                int day       = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_DAY));
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

    private Schedule getSchedule(int termId, int sectionId) {
        SQLiteDatabase db = getReadableDatabase();
        Schedule schedule = null;

        String query = "SELECT * FROM " + TABLE_SCHEDULES + " WHERE " + KEY_SCHEDULES_TERM_ID +
                " = " + termId + " AND " + KEY_SCHEDULES_SECTION_ID + " = " + sectionId +
                " AND " + KEY_SCHEDULES_ARCHIVED + " = " + inArchive;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // gather information from the database
                String location = cursor.getString(cursor.getColumnIndex(KEY_SCHEDULES_LOCATION));

                SparseArray<String> startTimes = new SparseArray<>();
                SparseArray<String> endTimes   = new SparseArray<>();

                int day = 0;
                for (int i = 3; i < 16; i += 2) {
                    startTimes.put(day, cursor.getString(i));
                    endTimes.put(day, cursor.getString(i+1));
                    day++;
                }

                // create the Schedule
                schedule = new Schedule(location, startTimes, endTimes);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return schedule;
    }

    /*
     * Gets all upcoming DueDates to display in DueDatesWidget
     */
    public List<DueDate> getUpcomingDueDates() {
        SQLiteDatabase db      = getReadableDatabase();
        List<DueDate> dueDates = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DUE_DATES + " WHERE " + KEY_DUE_DATES_ARCHIVED +
                " = " + FALSE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // check if the due date is upcoming
                Calendar today = Calendar.getInstance();
                Calendar due   = Calendar.getInstance();
                int year       = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_YEAR));
                int month      = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_MONTH));
                int day        = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_DAY));
                due.set(year, month, day);

                if (!today.after(due)) {
                    // gather information from the database
                    int termId         = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_TERM_ID));
                    int sectionId      = cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_SECTION_ID));
                    String dueDateName = cursor.getString(cursor.getColumnIndex(KEY_DUE_DATES_NAME));
                    boolean complete   = false;
                    if (cursor.getInt(cursor.getColumnIndex(KEY_DUE_DATES_COMPLETE)) == TRUE) {
                        complete = true;
                    }

                    String innerQuery = "SELECT " + KEY_SECTIONS_NAME + " FROM " + TABLE_SECTIONS +
                            " WHERE " + KEY_SECTIONS_TERM_ID + " = " + termId + " AND " +
                            KEY_SECTIONS_ID + " = " + sectionId;
                    Cursor innerCursor = db.rawQuery(innerQuery, null);

                    String sectionName = "";
                    if (innerCursor.moveToFirst()) {
                        sectionName = innerCursor.getString(
                                innerCursor.getColumnIndex(KEY_SECTIONS_NAME));
                    }
                    innerCursor.close();

                    // create and add the due date
                    DueDate dueDate = new DueDate(dueDateName, complete, due, sectionName);
                    dueDates.add(dueDate);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dueDates;
    }
}
