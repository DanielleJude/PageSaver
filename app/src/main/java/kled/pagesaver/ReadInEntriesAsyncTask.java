package kled.pagesaver;

import android.database.Cursor;
import android.os.AsyncTask;

/**
 * Created by Danielle on 2/24/17.
 */



public class ReadInEntriesAsyncTask extends AsyncTask<Void, Entry, Void> {
    public final static int PAST_MODE = 1;
    public final static int CURRENT_MODE = 2;
    public final static int ALL_MODE = 3;

    private int mode;
    private DBEntryAdapter adapter;

    public ReadInEntriesAsyncTask(int MODE, DBEntryAdapter adapter) {
        mode = MODE;
        this.adapter = adapter;

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground (Void... params) {


        return null;
    }

    @Override
    protected void onProgressUpdate (Entry... param) {

    }

    @Override
    protected void onPostExecute(Void result) {}
}
