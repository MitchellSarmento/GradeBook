package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Database Information
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GradeSaverDB";

    // Tables
    private static final String TABLE_TERMS       = "Terms";
    private static final String TABLE_SECTIONS    = "Sections";
    private static final String TABLE_ASSIGNMENTS = "Assignments";
    private static final String TABLE_DUEDATES    = "DueDates";

    // TABLE_TERMS columns
    private static final String KEY_TERMS_ID       = "id";
    private static final String KEY_TERMS_NAME     = "name";
    private static final String KEY_TERMS_ARCHIVED = "archived";

    // TABLE_SECTIONS columns
    private static final String KEY_SECTIONS_ID              = "id";
    private static final String KEY_SECTIONS_NAME            = "name";
    private static final String KEY_SECTIONS_TERM_ID         = "termId";
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
    private static final String KEY_SECTIONS_SCORE           = "score";
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
    private static final String KEY_ASSIGNMENTS_NAME       = "name";
    private static final String KEY_ASSIGNMENTS_SECTION_ID = "sectionId";
    private static final String KEY_ASSIGNMENTS_TYPE       = "type";
    private static final String KEY_ASSIGNMENTS_SCORE      = "score";
    private static final String KEY_ASSIGNMENTS_MAX_SCORE  = "maxScore";
    private static final String KEY_ASSIGNMENTS_GRADE      = "grade";

    // TABLE_DUEDATES columns
    private static final String KEY_DUEDATES_ID         = "id";
    private static final String KEY_DUEDATES_NAME       = "name";
    private static final String KEY_DUEDATES_SECTION_ID = "sectionId";
    private static final String KEY_DUEDATES_COMPLETE   = "complete";
    private static final String KEY_DUEDATES_DATE       = "date";

    private static final int TRUE  = 1;
    private static final int FALSE = 0;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_TERMS = "CREATE TABLE " + TABLE_TERMS + "(" +
                KEY_TERMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TERMS_NAME + " TEXT," +
                KEY_TERMS_ARCHIVED + " TEXT)";

        final String CREATE_TABLE_SECTIONS = "CREATE TABLE " + TABLE_SECTIONS + "(" +
                KEY_SECTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SECTIONS_NAME + " TEXT," +
                KEY_SECTIONS_TERM_ID + " INTEGER," + KEY_SECTIONS_HIGH_A + " REAL," + KEY_SECTIONS_LOW_A + " REAL,"
                + KEY_SECTIONS_HIGH_B + " REAL," + KEY_SECTIONS_LOW_B + " REAL," + KEY_SECTIONS_HIGH_C + " REAL,"
                + KEY_SECTIONS_LOW_C + " REAL," + KEY_SECTIONS_HIGH_D + " REAL," + KEY_SECTIONS_LOW_D + " REAL,"
                + KEY_SECTIONS_HIGH_F + " REAL," + KEY_SECTIONS_LOW_F + " REAL," + KEY_SECTIONS_WEIGHT_HOMEWORK +
                " REAL," + KEY_SECTIONS_WEIGHT_QUIZ + " REAL," + KEY_SECTIONS_WEIGHT_MIDTERM + " REAL," +
                KEY_SECTIONS_WEIGHT_FINAL + " REAL," + KEY_SECTIONS_WEIGHT_PROJECT + " REAL," +
                KEY_SECTIONS_WEIGHT_OTHER + " REAL," + KEY_SECTIONS_SCORE + " REAL," + KEY_SECTIONS_MAX_SCORE +
                " REAL," + KEY_SECTIONS_GRADE + " TEXT," + KEY_SECTIONS_LOCATION + " TEXT," +
                KEY_SECTIONS_ON_MONDAY + " INTEGER," + KEY_SECTIONS_ON_TUESDAY + " INTEGER," +
                KEY_SECTIONS_ON_WEDNESDAY + " INTEGER," + KEY_SECTIONS_ON_THURSDAY + " INTEGER," +
                KEY_SECTIONS_ON_FRIDAY + " INTEGER," + KEY_SECTIONS_ON_SATURDAY + " INTEGER," +
                KEY_SECTIONS_ON_SUNDAY + " INTEGER)";

        final String CREATE_TABLE_ASSIGNMENTS = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                KEY_ASSIGNMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ASSIGNMENTS_NAME + " TEXT," +
                KEY_ASSIGNMENTS_SECTION_ID + " INTEGER," + KEY_ASSIGNMENTS_TYPE + " TEXT," +
                KEY_ASSIGNMENTS_SCORE + " REAL," + KEY_ASSIGNMENTS_MAX_SCORE + " REAL," +
                KEY_ASSIGNMENTS_GRADE + " TEXT)";

        final String CREATE_TABLE_DUEDATES = "CREATE TABLE " + TABLE_DUEDATES + "(" +
                KEY_DUEDATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DUEDATES_NAME + " TEXT," +
                KEY_DUEDATES_SECTION_ID + " INTEGER," + KEY_DUEDATES_COMPLETE + " INTEGER," +
                KEY_DUEDATES_DATE + " TEXT)";

        db.execSQL(CREATE_TABLE_TERMS);
        db.execSQL(CREATE_TABLE_SECTIONS);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(CREATE_TABLE_DUEDATES);
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

    public List<Term> getTerms() {
        SQLiteDatabase db = getReadableDatabase();
        List<Term> terms = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TERMS + " WHERE " + KEY_TERMS_ARCHIVED + " = " + FALSE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String termName = cursor.getString(1);

                Term term = new Term(termName);
                terms.add(term);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return terms;
    }
}
