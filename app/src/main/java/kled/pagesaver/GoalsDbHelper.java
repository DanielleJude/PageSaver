package kled.pagesaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by kelle on 2/26/2017.
 */

public class GoalsDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "GoalsDB";

    public static final String TABLE_NAME_ENTRIES = "Goals";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE= "_title";
    public static final String KEY_DESCRIPTION = "_description";
    public static final String KEY_GOALS_PAGES = "_total_pages";
    public static final String KEY_PAGES_INCREMENT = "_daily_pages";
    public static final String KEY_END_TIME = "_end_time";

    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ENTRIES
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE
            + " TEXT, "
            + KEY_DESCRIPTION
            + " TEXT, "
            + KEY_GOALS_PAGES
            + " INTEGER NOT NULL, "
            + KEY_PAGES_INCREMENT
            + " INTEGER NOT NULL, "
            + KEY_END_TIME
            + " LONG "
            + "); ";

    public static final String[] columns = new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION,
    KEY_GOALS_PAGES, KEY_PAGES_INCREMENT, KEY_END_TIME };


    public GoalsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES);
        onCreate(db);
    }

    public long insertGoalEntry(GoalEntry entry){
        ContentValues value = new ContentValues();

        value.put(KEY_ID, entry.getId());
        value.put(KEY_TITLE, entry.getBookTitle());
        value.put(KEY_DESCRIPTION, entry.getDescription());
        value.put(KEY_GOALS_PAGES, entry.getPagesToComplete());
        value.put(KEY_PAGES_INCREMENT, entry.getDailyPages());

        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(TABLE_NAME_ENTRIES, null, value);
        database.close();
        return id;
    }

    public long updateGoalEntry(long id) {
        GoalEntry entry = fetchEntryByIndex(id);
        int entryToComplete = entry.getPagesToComplete();
        int newPagesToComplete = entryToComplete - entry.getDailyPages();

        if (newPagesToComplete > 0)
            entry.setPagesToComplete(newPagesToComplete);
        else
            entry.setPagesToComplete(0);

        ContentValues value = new ContentValues();

        value.put(KEY_ID, entry.getId());
        value.put(KEY_TITLE, entry.getBookTitle());
        value.put(KEY_DESCRIPTION, entry.getDescription());
        value.put(KEY_GOALS_PAGES, entry.getPagesToComplete());
        value.put(KEY_PAGES_INCREMENT, entry.getDailyPages());

        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME_ENTRIES, value, KEY_ID + "=" + id, null);
        database.close();

        return id;
    }

    public long updateGoalEntry(long id, int pagesRead) {
        GoalEntry entry = fetchEntryByIndex(id);
        int entryToComplete = entry.getPagesToComplete();
        int newPagesToComplete = entryToComplete - pagesRead;

        if (newPagesToComplete > 0)
            entry.setPagesToComplete(newPagesToComplete);
        else
            entry.setPagesToComplete(0);

        ContentValues value = new ContentValues();

        value.put(KEY_ID, entry.getId());
        value.put(KEY_TITLE, entry.getBookTitle());
        value.put(KEY_DESCRIPTION, entry.getDescription());
        value.put(KEY_GOALS_PAGES, entry.getPagesToComplete());
        value.put(KEY_PAGES_INCREMENT, entry.getDailyPages());

        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME_ENTRIES, value, KEY_ID + "=" + id, null);
        database.close();

        return id;
    }

    public void removeEntry(long index){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME_ENTRIES, KEY_ID + "=" + index, null);
        database.close();
    }

    public GoalEntry fetchEntryByIndex(long id) throws SQLException {
        SQLiteDatabase database = getReadableDatabase();
        GoalEntry entry = null;

        Cursor cursor = database.query(true, TABLE_NAME_ENTRIES, columns, KEY_ID + "="
                + id, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            entry = cursorToEntry(cursor);
        }

        cursor.close();
        database.close();
        return entry;
    }

    public ArrayList<GoalEntry> fetchEntries() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<GoalEntry> entryList = new ArrayList<GoalEntry>();

        Cursor cursor = database.query(TABLE_NAME_ENTRIES, columns,
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            GoalEntry entry = cursorToEntry(cursor);
            entryList.add(entry);
        }

        cursor.close();
        database.close();

        return entryList;
    }

    private GoalEntry cursorToEntry(Cursor cursor) {
        GoalEntry entry = new GoalEntry();
        entry.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        entry.setBookTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));

        return entry;

    }
}