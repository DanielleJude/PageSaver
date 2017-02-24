package kled.pagesaver;

import android.database.Cursor;
import android.os.AsyncTask;

/**
 * Created by Danielle on 2/24/17.
 */

public class ReadInEntriesAsyncTask extends AsyncTask<Void, Entry, Void> {
    ExerciseDataSource dataSource;
    @Override
    protected void onPreExecute() {
        dataSource = new ExerciseDataSource(getActivity());
    }

    @Override
    protected Void doInBackground (Void... params) {
        Cursor cursor = dataSource.getCursorToAllEntries();
        ExerciseEntry entry = dataSource.getNextEntryFromCursor(cursor);
        while(entry != null)
        {
            if(isCancelled())
            {
                cursor.close();
                dataSource.closeDB();
                return null;
            }
            publishProgress(entry);
            entry = dataSource.getNextEntryFromCursor(cursor);

        }

        return null;
    }

    @Override
    protected void onProgressUpdate (ExerciseEntry... param) {
        // Add to adapter
        ExerciseEntry entry = param[0];
        if(entry != null)
            adapter.addToAdapter(entry);
    }

    @Override
    protected void onPostExecute(Void result) {}
}
